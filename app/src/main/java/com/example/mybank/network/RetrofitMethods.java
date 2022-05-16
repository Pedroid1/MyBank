package com.example.mybank.network;

import com.example.mybank.model.Cliente;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitMethods {

    @POST("/clientes")
    Call<Cliente> cadastrarCliente(@Body Cliente cliente);

    @GET("/clientes/{id}")
    Call<Cliente> buscarPorId(@Path("id") Integer id);

    @PUT("/clientes/{id}")
    Call<Cliente> atualizarPorId(@Path("id") Integer id);

    @DELETE("/clientes/{id}")
    Call<Cliente> deletarPorId(@Path("id") Integer id);
}
