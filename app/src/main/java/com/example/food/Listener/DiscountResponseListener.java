package com.example.food.Listener;

import com.example.food.Domain.Response.DiscountResponse;

public interface DiscountResponseListener {
    void didFetch(DiscountResponse response, String message);
    void didError(String message);
}
