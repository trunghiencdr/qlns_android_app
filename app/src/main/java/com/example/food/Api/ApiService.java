package com.example.food.Api;

import com.example.food.Domain.CategoryDomain;
import com.example.food.Domain.ProductDomain;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ApiService {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiService=new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("api/v1/Categories")
    Call<ArrayList<CategoryDomain>> getListCategoryDomain();

    @GET("api/v1/Products")
    Call<ArrayList<ProductDomain>> getListProductDomain();
}
