package com.example.compras.api;

import com.example.compras.model.Cliente;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ClienteAPI {
    interface  UserAPI{
        @POST("/user/login")
        Call<Cliente> loginUser(@Body Cliente cliente);

    }
}
