package com.sample.listviewscrollloading;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {


    private static Retrofit retrofit;
    private static final String Base_url ="https://jsonplaceholder.typicode.com";

    public static Retrofit getRetrofitInstance(){

        if(retrofit == null){

            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(Base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
