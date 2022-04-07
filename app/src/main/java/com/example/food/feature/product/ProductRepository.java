package com.example.food.feature.product;

import com.example.food.Domain.Product;
import com.example.food.Domain.Product;

import java.util.List;


import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductRepository {

    @GET("v1/Products/top10")
    Observable<Response<List<Product>>> getTop10Products();

    @GET("v1/Products/category/{category_id}")
    Observable<Response<List<Product>>> getProductsByCategoryId(@Path("category_id") int id);
}
