package br.ifsul.edu.lojafinal.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import br.ifsul.edu.lojafinal.R;
import br.ifsul.edu.lojafinal.model.Pedido;
import br.ifsul.edu.lojafinal.adapter.PedidosAdapter;
import br.ifsul.edu.lojafinal.setup.AppSetup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PedidosActivity extends AppCompatActivity {
    private static final String TAG = "pedidosActivity";
    private ListView lv_pedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        lv_pedidos = findViewById(R.id.lv_pedidos);
        lv_pedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("pedidos/");

        myRef.orderByChild("nome").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Pedido pedido = ds.getValue(Pedido.class);
                    pedido.setKey(ds.getKey());
                    AppSetup.pedidos.add(pedido);
                }

                //carrega os dados na View
                lv_pedidos.setAdapter(new PedidosAdapter(PedidosActivity.this, AppSetup.pedidos));

                //carrega os dados na View
                //vProdutos.setAdapter(new ProdutosAdapter(ProdutosActivity.this, AppSetup.produtos));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }

        return true;
    }
}
