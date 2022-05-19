package com.example.food.dto;

import android.service.autofill.UserData;

import com.example.food.model.User;
import com.example.food.util.ResponseMessage;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserDTO{

    private String message;
    private String status;

    @SerializedName("data")
    @Expose
    private User data;

    public UserDTO() {
    }

    public UserDTO(String message, String status, User data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public User getUser(){
        return data;
    }

}
