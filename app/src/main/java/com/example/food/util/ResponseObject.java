package com.example.food.util;


import androidx.lifecycle.MutableLiveData;

import com.example.food.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class ResponseObject<T> {


    protected String status;


    protected String message;


    private T data;
    public ResponseObject(){

    }

    public T getData() {
        if(this.data.getClass().getName().equals(User.class.getName())){

        }
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResponseObject(String status, String message, T t) {
        this.status = status;
        this.message = message;
        this.data = t;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
