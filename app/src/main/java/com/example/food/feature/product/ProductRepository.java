package com.example.food.feature.product;

import com.example.food.Domain.Product;
import com.example.food.Domain.ProductDomain;

import java.util.List;


import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;

public interface ProductRepository {

    @GET("v1/Products/top10")
    Observable<Response<List<Product>>> getTop10Products();
}
