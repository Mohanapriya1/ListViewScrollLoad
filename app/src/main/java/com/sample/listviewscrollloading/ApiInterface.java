package com.sample.listviewscrollloading;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("/photos")
    public Call<List<ListModel>> getAllList();
}
