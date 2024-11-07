package com.example.compras.api;

import com.example.compras.model.Cliente;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ClienteAPI {

        @POST("/cliente/login")
        Call<Cliente> login(@Body Cliente cliente);

    }
