package com.example.food.Listener;


import com.example.food.Domain.Response.OTPResponse;

public interface OTPResponseListener {
    void didFetch(OTPResponse response, String message);
    void didError(String message);
}
