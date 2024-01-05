package com.example.navigationdrawer.Helper;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private ServerDetail myApi;

    public RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerDetail.endpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myApi = retrofit.create(ServerDetail.class);
    }
    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();

        }
        return instance;
    }

    public ServerDetail getServerDetail() {
        return myApi;
    }
}
