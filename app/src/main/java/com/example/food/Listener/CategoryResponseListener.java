package com.example.food.Listener;

import com.example.food.Domain.Category;

import java.util.ArrayList;

public interface CategoryResponseListener {
    void didFetch(ArrayList<Category> response, String message);
    void didError(String message);
}
