package br.ifsul.edu.lojafinal.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;

import br.ifsul.edu.lojafinal.R;
import br.ifsul.edu.lojafinal.model.ItemPedido;
import br.ifsul.edu.lojafinal.model.Produto;
import br.ifsul.edu.lojafinal.setup.AppSetup;


public class ProdutoDetalheActivity extends AppCompatActivity {

    private static final String TAG = "produtoDetalheActivity";
    private TextView tvNome, tvDescricao, tvValor, tvEstoque;
    private EditText etQuantidade;
    private ImageView imvFoto;
    private Button btVender;
    private Produto produto;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_detalhe);

        //mapeia os componentes da UI
        tvNome = findViewById(R.id.tvNomeProdutoAdapter);
        tvDescricao = findViewById(R.id.tvDerscricaoProduto);
        tvValor = findViewById(R.id.tvValorProduto);
        tvEstoque = findViewById(R.id.tvQuantidadeProduto);
        etQuantidade = findViewById(R.id.etQuantidade);
        imvFoto = findViewById(R.id.imvFoto);
        btVender = findViewById(R.id.btComprarProduto);

        //obtém a position anexada como metadado
        final Integer position = getIntent().getExtras().getInt("position");
        produto = AppSetup.produtos.get(position);
        Log.d(TAG, "Output State: "+ produto.equals(AppSetup.produtos.get(position)));

        Log.d(TAG, "Position: "+ position);

        //bindview
        tvNome.setText(AppSetup.produtos.get(position).getNome());
        tvDescricao.setText(AppSetup.produtos.get(position).getDescricao());
        tvValor.setText(NumberFormat.getCurrencyInstance().format(AppSetup.produtos.get(position).getValor()));

        if(produto.getUrl_foto().equals("")){
            imvFoto.setImageResource(R.drawable.img_carrinho_de_compras);
        }else{
            imvFoto.setImageBitmap(AppSetup.cacheProdutos.get(produto.getKey()));
        }

        // obtém a referência do database e do nó
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("produtos/" + produto.getKey() + "/quantidade");

        // Escuta o database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer quantidade = dataSnapshot.getValue(Integer.class);
                tvEstoque.setText(String.format("%s %s", getString(R.string.label_estoque), quantidade.toString()));
                produto.setQuantidade(quantidade);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btVender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppSetup.cliente == null){
                    Toast.makeText(ProdutoDetalheActivity.this, "Selecione um cliente", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ProdutoDetalheActivity.this, ClientesActivity.class));
                }else{
                    if(etQuantidade.getText().toString().isEmpty()){
                        Toast.makeText(ProdutoDetalheActivity.this, "Digite a quantidade.", Toast.LENGTH_SHORT).show();
                    }else{
                        Integer quantidade = Integer.valueOf(etQuantidade.getText().toString());
                        if(quantidade <= produto.getQuantidade()){

                            //concretiza a venda
                            ItemPedido itemPedido = new ItemPedido();
                            itemPedido.setProduto(produto);
                            itemPedido.setQuantidade(quantidade);
                            itemPedido.setTotalItem(quantidade * produto.getValor());
                            //itemPedido.setTotalItem(AppSetup.produtos.get(position).getValor() * itemPedido.getQuantidade());
                            itemPedido.setSituacao(true);

                            AppSetup.carrinho.add(itemPedido);

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef2 = database.getReference("produtos/" + produto.getKey() + "/quantidade");

                            myRef2.setValue(AppSetup.produtos.get(position).getQuantidade() - itemPedido.getQuantidade());


                            Toast.makeText(ProdutoDetalheActivity.this, "Item adicionado ao carrinho", Toast.LENGTH_SHORT).show();

                            //startActivity(new Intent(ProdutoDetalheActivity.this, CarrinhoActivity.class));

                            startActivity(new Intent(ProdutoDetalheActivity.this, CarrinhoActivity.class));
                            finish();
                        }else{
                            Toast.makeText(ProdutoDetalheActivity.this, "Quantidade acima do estoque.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}