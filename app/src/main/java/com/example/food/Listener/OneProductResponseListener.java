package com.example.food.Listener;

import com.example.food.Domain.Product;
import com.example.food.Domain.Response.ProductResponse;

public interface OneProductResponseListener {
    void didFetch(ProductResponse response, String message);
    void didError(String message);
}
