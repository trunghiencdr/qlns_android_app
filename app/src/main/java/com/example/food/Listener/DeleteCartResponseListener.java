package com.example.food.Listener;

import com.example.food.Domain.Response.CartResponse;

public interface DeleteCartResponseListener {
    void didFetch(String response, String message);
    void didError(String message);
}
