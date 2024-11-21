package com.example.compras.controller;

import com.example.compras.api.ClienteAPI;
import com.example.compras.api.RetrofitClient;
import com.example.compras.model.Cliente;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClienteAPIController {

    public static ClienteAPIController.ResponseCallback ResponseCallback;
    private RetrofitClient retrofitClient;
    private List<Cliente> listCliente;
    private String status;
    private Cliente cliente;
    private ClienteAPI clienteAPI;

    public interface ResponseCallback {
        void onSuccess(Cliente cliente);
        void onFailure(Throwable t);
    }

    // Construtor de ClienteAPIController
    public ClienteAPIController(RetrofitClient retrofitClient) {
        this.retrofitClient = retrofitClient;
        this.clienteAPI = RetrofitClient.getRetrofitInstance().create(ClienteAPI.class);
        this.status = "";
    }
    public String getMessage() {
        return status;
    }

    // API de Login de Cliente
    public void getLoginCliente(String email, String password,
                                ClienteAPIController.ResponseCallback responseCallback) {

        Cliente cliente = new Cliente();
        cliente.setEmail(email);
        cliente.setSenha(password);

        Call<Cliente> call = this.clienteAPI.loginCliente(cliente);
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
    // API de Cadastramento de Cliente
    public void cadastraCliente(Cliente cliente,
                                   ClienteAPIController.ResponseCallback responseCallback){

        Call<Cliente> call = this.clienteAPI.cadastraCliente(cliente);
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
    };
    // API de Alteracao de perfil de Cliente
    public void alteraPerfilCliente (String email, Cliente cliente,
                                     ClienteAPIController.ResponseCallback responseCallback){

        Call<Cliente> call = this.clienteAPI.alteraPerfilCliente(email,cliente);
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
    // API de Busca de Cliente por email
    public void getClienteByEmail(String email,
                                ClienteAPIController.ResponseCallback responseCallback) {
        Call<Cliente> call = this.clienteAPI.getClienteByEmail(email);
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
    // API que adiciona um produto no carrinho do cliente
    public void adicionaProdutoNoCarrinho(String email, long idProduto, int quant,
                                          ClienteAPIController.ResponseCallback responseCallback){
        Call<Cliente> call = this.clienteAPI.adicionaProdutoNoCarrinho(email,idProduto,quant);
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
