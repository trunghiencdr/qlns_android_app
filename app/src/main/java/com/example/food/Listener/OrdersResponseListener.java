package com.example.food.Listener;

import com.example.food.Domain.Order;

import java.util.ArrayList;

public interface OrdersResponseListener {
    void didFetch(ArrayList<Order> response, String message);
    void didError(String message);
}
