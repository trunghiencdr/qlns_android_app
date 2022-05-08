package com.example.food.viewmodel;

import com.example.food.Domain.Order;
import com.example.food.Domain.Response.OrderResponse;
import com.example.food.Domain.Response.ResponseObject;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OrderRepository {
    @GET("v1/Orders/param/state?")
    Single<Response<ResponseObject<List<Order>>>> getOrdersByState(@Query("state") String state);

    @GET("v1/Orders/param/state/date?")
    Single<Response<ResponseObject<List<Order>>>> getOrdersByStateAndCreateAtBetween(@Query("state") String state,
                                                            @Query("startDate") String startDate,
                                                            @Query("endDate") String endDate);
}
