package com.example.food.network;

import com.example.food.Domain.CategoryDomain;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryAPIService {

    @GET("v1/Categories")
    Call<List<CategoryDomain>> getCategoryDomains();
}
