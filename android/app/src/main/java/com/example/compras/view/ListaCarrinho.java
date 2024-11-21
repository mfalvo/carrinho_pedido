package com.example.compras.view;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.compras.R;
import com.example.compras.adapter.ItemCarrinhoAdapter;
import com.example.compras.adapter.ProdutoAdapter;
import com.example.compras.api.RetrofitClient;
import com.example.compras.controller.ClienteAPIController;
import com.example.compras.controller.ProdutoAPIController;
import com.example.compras.model.CarrinhoItem;
import com.example.compras.model.Cliente;
import com.example.compras.model.Produto;
import com.example.compras.utils.LProdutos;
import com.example.compras.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class ListaCarrinho extends AppCompatActivity {

    private RecyclerView recyclerViewCarrinho;
    private List<CarrinhoItem> listaCarrinho;
    private List<Produto> listaProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_carrinho);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.recyclerViewCarrinho = findViewById(R.id.recyclerViewCarrinho);
        configurarRecyclerView();

        SharedPrefManager sharedPrefManager = new SharedPrefManager(ListaCarrinho.this);
        Cliente cliente = sharedPrefManager.getCliente();
        LProdutos lprodutos = sharedPrefManager.getLProdutos();
        this.listaProdutos = lprodutos.getLProdutos();

        RetrofitClient retrofitClient;
        retrofitClient = new RetrofitClient();

        ClienteAPIController clienteAPIController = new ClienteAPIController(retrofitClient);
        clienteAPIController.getClienteByEmail( cliente.getEmail(), new ClienteAPIController.ResponseCallback(){
            @Override
            public void onSuccess(Cliente cliente) {
                if (cliente != null){
                    sharedPrefManager.saveCliente(cliente);
                    listaCarrinho = cliente.getCarrinho().getCarrinhoitems();
                    atualizarRecyclerView();
                } else {
                    mostrarMensagemErro("Nenhum item encontrado!");
                }
            }
            @Override
            public void onFailure(Throwable t) {
                mostrarMensagemErro("Erro ao buscar produtos: " + t.getMessage());
                Log.e("ListaProdutos", "Erro ao buscar produtos", t);
            }
        });


    }

    private void atualizarRecyclerView() {
        ItemCarrinhoAdapter adapter = new ItemCarrinhoAdapter(listaCarrinho, listaProdutos);
        recyclerViewCarrinho.setAdapter(adapter);
    }

    private void mostrarMensagemErro(String mensagem) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setCancelable(false);
        alerta.setTitle("Erro");
        alerta.setMessage(mensagem);
        alerta.setNegativeButton("Ok", null);
        alerta.create().show();
    }

    private void configurarRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewCarrinho.setLayoutManager(layoutManager);
        recyclerViewCarrinho.setHasFixedSize(true);
        recyclerViewCarrinho.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    public void abrirPerfilCliente(View view){
        Intent intent = new Intent(ListaCarrinho.this, PerfilCliente.class);
        startActivity(intent);
    }

    public void fecharCarrinhoItem(View view){
        finish();
    }

}