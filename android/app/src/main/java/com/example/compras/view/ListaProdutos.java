package com.example.compras.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import androidx.activity.EdgeToEdge;
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
import com.example.compras.api.RetrofitClient;
import com.example.compras.controller.ClienteAPIController;
import com.example.compras.controller.ProdutoAPIController;
import com.example.compras.model.Cliente;
import com.example.compras.model.Produto;
import com.example.compras.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class ListaProdutos extends AppCompatActivity {

    private RecyclerView recyclerViewProdutos;
    private List<Produto> listaProdutos;

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

        ProdutoAPIController produtoAPIController = new ProdutoAPIController(retrofitClient);
        produtoAPIController.getAllProdutos( new ProdutoAPIController.ResponseCallback(){
            @Override
            public void onSuccess(List<Produto> produtos) {
                    if (produtos != null && !produtos.isEmpty()) {
                        listaProdutos = produtos;
                        atualizarRecyclerView();
                    } else {
                        mostrarMensagemErro("Nenhum produto encontrado!");
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
        ProdutoAdapter adapter = new ProdutoAdapter(listaProdutos);
        recyclerViewProdutos.setAdapter(adapter);
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
        recyclerViewProdutos.setLayoutManager(layoutManager);
        recyclerViewProdutos.setHasFixedSize(true);
        recyclerViewProdutos.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    public void abrirPerfilCliente(View view){
        Intent intent = new Intent(ListaProdutos.this, PerfilCliente.class);
        startActivity(intent);
    }

    public void abriListaCarrinho(View view){
        Intent intent = new Intent(ListaProdutos.this, ListaCarrinho.class);
        startActivity(intent);
    }

}