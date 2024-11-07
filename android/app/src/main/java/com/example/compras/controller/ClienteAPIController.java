package com.example.compras.controller;

import com.example.compras.api.ClienteAPI;
import com.example.compras.api.RetrofitClient;
import com.example.compras.model.Cliente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClienteAPIController {

    public static ClienteAPIController.ResponseCallback ResponseCallback;
    private RetrofitClient retrofitClient;
    private Cliente cliente;
    private ClienteAPI clienteAPI;

    public interface ResponseCallback {
        void onSuccess(Cliente cliente);
        void onFailure(Throwable t);
    }

    public ClienteAPIController(RetrofitClient retrofitClient) {
        this.retrofitClient = retrofitClient;
        this.clienteAPI = RetrofitClient.getRetrofitInstance().create(ClienteAPI.class);
    }

    public void getLoginUser(String email, String password,
                             ClienteAPIController.ResponseCallback responseCallback) {

        Cliente cliente = new Cliente();
        cliente.setEmail(email);
        cliente.setSenha(password);

        Call<Cliente> call = this.clienteAPI.login(cliente);
        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                responseCallback.onSuccess(response.body());
            }
            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                responseCallback.onFailure(new Exception("Request failed"));
            }
        });
    }
}
