package com.example.compras.api;

import com.example.compras.model.Cliente;
import com.example.compras.model.Produto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface ProdutoAPI {

    @GET("/produtos")
    Call<List<Produto>> getListaProdutos();
}
