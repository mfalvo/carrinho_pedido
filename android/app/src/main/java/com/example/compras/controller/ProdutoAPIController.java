package com.example.compras.controller;


import android.util.Log;

import com.example.compras.api.ClienteAPI;
import com.example.compras.api.ProdutoAPI;
import com.example.compras.api.RetrofitClient;
import com.example.compras.model.Produto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdutoAPIController {

    public static ProdutoAPIController.ResponseCallback ResponseCallback;
    private RetrofitClient retrofitClient;
    private ProdutoAPI produtoAPI;

    // Interface de ResponseCallback
    public interface ResponseCallback {
        void onSuccess(List<Produto> produtos);
        void onFailure(Throwable t);
    }

    // Construtor
    public ProdutoAPIController(RetrofitClient retrofitClient) {

        this.retrofitClient = retrofitClient;
        this.produtoAPI = RetrofitClient.getRetrofitInstance().create(ProdutoAPI.class);
    }
    public void fetchAllProdutos(final ResponseCallback callback) {
        Call<List<Produto>> call = produtoAPI.getListaProdutos();
        call.enqueue(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                if (response.isSuccessful()) {
                    List<Produto> produtos = response.body();
                    callback.onSuccess(produtos);
                } else {
                    Log.e("Erro", "Erro na resposta: " + response.errorBody());
                    callback.onFailure(new Exception("Erro na resposta da API"));
                }
            }
            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {
                Log.e("Erro", "Falha na chamada à API: " + t.getMessage());
                callback.onFailure(t);
            }
        });
    }


}