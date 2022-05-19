package com.example.food.Listener;


import com.example.food.Domain.Response.OrderDetailResponse;

public interface InsertOrderDetailResponseListener {
    void didFetch(OrderDetailResponse response, String message);
    void didError(String message);
}
