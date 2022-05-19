package com.example.food.Listener;

import com.example.food.Domain.Response.CartResponse;
import com.example.food.dto.CartDTO;

public interface InsertCartResponseListener {
    void didFetch(CartResponse response, String message);
    void didError(String message);
}
