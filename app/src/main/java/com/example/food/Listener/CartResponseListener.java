package com.example.food.Listener;

import com.example.food.Domain.Cart;

import java.util.ArrayList;

public interface CartResponseListener {
    void didFetch(ArrayList<Cart> response, String message);
    void didError(String message);
}
