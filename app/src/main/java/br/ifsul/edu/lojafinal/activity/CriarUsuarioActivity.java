package br.ifsul.edu.lojafinal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.ifsul.edu.lojafinal.R;
import br.ifsul.edu.lojafinal.model.Usuario;
import br.ifsul.edu.lojafinal.setup.AppSetup;

public class CriarUsuarioActivity extends AppCompatActivity {
    private static final String TAG = "UserActivity";
    private EditText etEmail, etSenha, etFuncao, etNome, etSobrenome, etCodigo;
    private FirebaseAuth mAuth;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_usuario);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //mapeia os componentes da UI
        etEmail = findViewById(R.id.etEmail_signup);
        etSenha = findViewById(R.id.etSenha_signup);
//        etFuncao = findViewById(R.id.etFuncao_signup);
        etNome = findViewById(R.id.etNome_signup);
        etSobrenome = findViewById(R.id.etSobrenome_signup);



        //trata evento onclick do button
        findViewById(R.id.btcadastrar_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String senha = etSenha.getText().toString();
//                String funcao = etFuncao.getText().toString();
                String nome = etNome.getText().toString();
                String sobrenome = etSobrenome.getText().toString();


                if(!email.isEmpty() && !senha.isEmpty()){
                    signUp(email, senha, nome, sobrenome);
                }else{
                    if(email.isEmpty()){
                        etEmail.setError(getString(R.string.msg_invalido));
                    }
                    if(senha.isEmpty()){
                        etSenha.setError(getString(R.string.msg_invalido));
                    }
                    Snackbar.make(findViewById(R.id.container_userActivity), getString(R.string.toast_preencher_todos_campos), Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }

    private void signUp(String email, String senha, final String nome, final String sobrenome) {
        mAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //Salvando o regsitro do usuário no Database do Firebase
                    Log.d(TAG, "createUserWithEmail:success");
                    cadastrarUser(nome,sobrenome);
                    sendEmailVerification();
                    limparCampos();

                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(CriarUsuarioActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void limparCampos() {
        etEmail.setText("");
        etSenha.setText("");
//        etFuncao.setText("");
        etSobrenome.setText("");
        etNome.setText("");

    }

    private void cadastrarUser(final String nome, final String sobrenome) {
        Usuario user = new Usuario();
        user.setFirebaseUser(mAuth.getCurrentUser());
        user.setFuncao("vendedor");
        user.setNome(nome);
        user.setSobrenome(sobrenome);
        user.setEmail(mAuth.getCurrentUser().getEmail());
        FirebaseDatabase.getInstance().getReference().child("usuarios").child(user.getFirebaseUser().getUid()).setValue(user);
        AppSetup.user = user;
        Toast.makeText(CriarUsuarioActivity.this, "Usuário Criado com sucesso com E-mail: ." + user.getEmail(), Toast.LENGTH_SHORT).show();

    }

    private void sendEmailVerification() {
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CriarUsuarioActivity.this,
                            "Email de verificação enviado para " + user.getEmail(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "sendEmailVerification", task.getException());
                    Toast.makeText(CriarUsuarioActivity.this,
                            "Envio de email para verifiacão falhou.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
