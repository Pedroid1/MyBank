package com.example.mybank.network;

import com.example.mybank.model.Endereco;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitMethods {

    @GET("{cep}")
    Call<Endereco> getCep(@Path("cep") String cep);
}
