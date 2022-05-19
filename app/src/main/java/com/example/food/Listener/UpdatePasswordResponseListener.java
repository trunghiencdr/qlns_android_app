package com.example.food.Listener;

import com.example.food.Domain.Response.UpdatePasswordResponse;

public interface UpdatePasswordResponseListener {
    void didFetch(UpdatePasswordResponse response, String message);
    void didError(String message);
}
