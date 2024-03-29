package br.ifsul.edu.lojafinal.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import br.ifsul.edu.lojafinal.R;
import br.ifsul.edu.lojafinal.model.Cliente;
import br.ifsul.edu.lojafinal.setup.AppSetup;

import static android.support.constraint.Constraints.TAG;

public class ClientesAdapter extends ArrayAdapter<Cliente> {
    private Context context;
    private Bitmap fotoEmBitmap;

    public ClientesAdapter(@NonNull Context context, @NonNull List<Cliente> clientes) {
        super(context, 0, clientes);
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;

        //infla a view
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cliente_adapter, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        //bindview
        final Cliente cliente = getItem(position);

        holder.tvNome.setText(cliente.getNome());
        holder.tvCPF.setText(cliente.getCpf());
        holder.pbFoto.setVisibility(ProgressBar.VISIBLE);
        holder.imvFoto.setImageResource(R.drawable.img_cliente_icon_524x524);
        if(cliente.getUrl_foto().equals("")){
            holder.pbFoto.setVisibility(ProgressBar.INVISIBLE);
        }else{
            //faz o download do servidor
            if(AppSetup.cacheClientes.get(cliente.getKey()) == null){
                StorageReference mStorageRef = FirebaseStorage.getInstance().getReference("images/clientes/" + cliente.getCodigoDeBarras() + ".jpeg");
                final long ONE_MEGABYTE = 1024 * 1024;
                mStorageRef.getBytes(ONE_MEGABYTE)
                        .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                fotoEmBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                holder.imvFoto.setImageBitmap(fotoEmBitmap);
                                AppSetup.cacheClientes.put(cliente.getKey(), fotoEmBitmap);
                                holder.pbFoto.setVisibility(ProgressBar.INVISIBLE);
                            }})
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Log.d(TAG, "Download da foto do cliente falhou: " + "images/clientes/" + cliente.getCodigoDeBarras() + ".jpeg");
                            }
                        });
            }else{
                holder.imvFoto.setImageBitmap(AppSetup.cacheClientes.get(cliente.getKey()));
                holder.pbFoto.setVisibility(ProgressBar.INVISIBLE);
            }
        }

        return convertView;
    }

    private class ViewHolder {
        final TextView tvNome;
        final TextView tvCPF;
//        final TextView tvSobrenome;
        final ImageView imvFoto;
        final ProgressBar pbFoto;

        public ViewHolder(View view) {
            //mapeia os componentes da UI para vincular os dados do objeto de modelo
            tvNome = view.findViewById(R.id.tvNomeClienteAdapter);
            tvCPF = view.findViewById(R.id.tvDetalhesDoClienteAdapater);
//            tvSobrenome = view.findViewById(R.id.tv)
            imvFoto = view.findViewById(R.id.imvFotoDoClienteAdapter);
            pbFoto = view.findViewById(R.id.pb_foto_clientes_adapter);
        }
    }
}
