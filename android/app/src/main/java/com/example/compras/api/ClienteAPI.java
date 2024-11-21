package com.example.compras.api;

import com.example.compras.model.Cliente;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ClienteAPI {

        // End Point que verifica login se usuario e senha estiverem corretos no
        // objeto Cliente, o end point retornará o objeto Cliente completo
        @POST("/clientes/login")
        Call<Cliente> loginCliente(@Body Cliente cliente);

        // End point que permite o cadastro de cliente.
        @POST("/clientes")
        Call<Cliente> cadastraCliente(@Body Cliente cliente);

        // End point que permite a obtenção do objeto cliente por email
        @GET("/clientes/{email}")
        Call<Cliente> getClienteByEmail(@Path("email") String email);

        // En point que permite a alteracao de dados do cliente
        @PUT("/clientes/{email}")
        Call<Cliente> alteraPerfilCliente(@Path("email") String email, @Body Cliente cliente);

        @PUT("/clientes/{email}/addItemCarrinho/{idproduto}/{quant}")
        Call<Cliente> adicionaProdutoNoCarrinho(@Path("email") String email,
                                                @Path("idproduto") long idproduto,
                                                @Path("quant") int quant);



}
