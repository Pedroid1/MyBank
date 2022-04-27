package com.example.mybank.network;

public class Retrofit {

    private static final String BASE_URL =
            "https://ws.apicep.com/";

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .build();
        }
        return retrofit;
    }
}
