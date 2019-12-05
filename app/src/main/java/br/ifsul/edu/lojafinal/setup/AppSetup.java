package br.ifsul.edu.lojafinal.setup;

import android.graphics.Bitmap;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ifsul.edu.lojafinal.model.Cliente;
import br.ifsul.edu.lojafinal.model.ItemPedido;
import br.ifsul.edu.lojafinal.model.Pedido;
import br.ifsul.edu.lojafinal.model.Produto;
import br.ifsul.edu.lojafinal.model.Usuario;



public class AppSetup {
    public static List<Produto> produtos = new ArrayList<>();
    public static List<Cliente> clientes = new ArrayList<>();
    public static List<ItemPedido> carrinho = new ArrayList<>();
    public static List<Pedido> pedidos = new ArrayList<>();
    public static List<ItemPedido> itemPedidos = new ArrayList<>();
    public static Cliente cliente = null;
    public static Pedido pedido = null;
    public static Usuario user = null;
    public static FirebaseAuth mAuth = null;

    public static Map<String, Bitmap> cacheProdutos = new HashMap<>();
    public static Map<String, Bitmap> cacheClientes = new HashMap<>();
}
