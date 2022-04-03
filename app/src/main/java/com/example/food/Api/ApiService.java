package com.example.food.Api;

import com.example.food.Domain.CartDomain;
import com.example.food.Domain.CategoryDomain;
import com.example.food.Domain.ProductDomain;
import com.example.food.dto.UserDTO;
import com.example.food.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiService=new Retrofit.Builder()
            .baseUrl("http://192.168.1.10:8080/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("api/v1/Categories")
    Call<ArrayList<CategoryDomain>> getListCategoryDomain();

    @GET("api/v1/Products")
    Call<ArrayList<ProductDomain>> getListProductDomain();

    @GET("api/v1/Cart/user/{id}")
    Call<ArrayList<CartDomain>> getListCartDomainFollowUserId(@Path(value = "id",encoded = true) int id);

    @POST("api/v1/Carts/insert")
    public Observable<CartDomain> insertCart(@Body CartDomain cartDomain);
}
