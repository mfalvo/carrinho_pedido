package com.example.compras.view;

import android.content.Context;
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
import com.example.compras.adapter.ProdutoAdapter;
import com.example.compras.adapter.ProdutoAdapter.MyViewHolder;
import com.example.compras.api.RetrofitClient;
import com.example.compras.controller.ClienteAPIController;
import com.example.compras.controller.ProdutoAPIController;
import com.example.compras.model.Cliente;
import com.example.compras.utils.LProdutos;
import com.example.compras.model.Produto;
import com.example.compras.utils.SharedPrefManager;

import java.util.List;

public class ListaProdutos extends AppCompatActivity {

    private RecyclerView recyclerViewProdutos;
    private List<Produto> listaProdutos;

    private String emailCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_produtos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.recyclerViewProdutos = findViewById(R.id.recyclerViewProdutos);
        configurarRecyclerView();

        RetrofitClient retrofitClient;
        retrofitClient = new RetrofitClient();

        SharedPrefManager sharedPrefManager = new SharedPrefManager(ListaProdutos.this);
        Cliente cliente = sharedPrefManager.getCliente();
        this.emailCliente = cliente.getEmail();

        ProdutoAPIController produtoAPIController = new ProdutoAPIController(retrofitClient);
        produtoAPIController.getAllProdutos( new ProdutoAPIController.ResponseCallback(){
            @Override
            public void onSuccess(List<Produto> produtos) {
                    if (produtos != null && !produtos.isEmpty()) {

                        LProdutos lProdutos = new LProdutos();
                        lProdutos.setLProdutos(produtos);
                        sharedPrefManager.saveLProdutos(lProdutos);
                        listaProdutos = produtos;
                        atualizarRecyclerView();
                    } else {
                        avisoLocal("Nenhum produto encontrado!");
                    }
            }
            @Override
            public void onFailure(Throwable t) {
                   avisoLocal("Erro ao buscar produtos: " + t.getMessage());
            }
        });
    }

    private void atualizarRecyclerView() {
        ProdutoAdapter adapter = new ProdutoAdapter(ListaProdutos.this,this.emailCliente,listaProdutos);
        recyclerViewProdutos.setAdapter(adapter);
    }


    private void configurarRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewProdutos.setLayoutManager(layoutManager);
        recyclerViewProdutos.setHasFixedSize(true);
        recyclerViewProdutos.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    private void avisoLocal(String aviso) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(ListaProdutos.this);
        alerta.setCancelable(false);
        alerta.setTitle("Aviso");
        alerta.setMessage(aviso);
        alerta.setNegativeButton("Ok", null);
        alerta.create().show();
    }


    public void abrirPerfilCliente(View view){
        Intent intent = new Intent(ListaProdutos.this, PerfilCliente.class);
        startActivity(intent);
    }

    public void abriListaCarrinho(View view){
        Intent intent = new Intent(ListaProdutos.this, ListaCarrinho.class);
        startActivity(intent);
    }

    public void sairProduto(View view){
        SharedPrefManager sharedPrefManager = new SharedPrefManager(ListaProdutos.this);
        sharedPrefManager.resetKeys();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }


}