package com.example.food.feature.category;

import com.example.food.Domain.CategoryDomain;
import com.example.food.dto.CategoryResponse;

import java.security.cert.PKIXRevocationChecker;
import java.util.List;
import java.util.Optional;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CategoryService {

    @GET("v1/Categories")
    Call<List<CategoryDomain>> getCategoryDomains();


    @GET("v1/Categories/{id}")
    Observable<Response<CategoryResponse>> getCategoryById(@Path("id") int id);

    @GET("v1/Categories/v2")
     Observable<Response<List<CategoryDomain>>> getCategories();

}
