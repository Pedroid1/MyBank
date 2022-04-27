package com.example.mybank.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class RetrofitMethods {

    @GET("characters/{id}")
    Call<Character[]> getCharacterById(@Path("id") Integer id);
}
