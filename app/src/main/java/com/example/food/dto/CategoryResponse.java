package com.example.food.dto;

import com.example.food.Domain.CategoryDomain;
import com.example.food.util.ResponseMessage;

import java.util.List;

public class CategoryResponse extends ResponseMessage {
    private CategoryDomain data;

    public CategoryResponse() {
    }

    public CategoryResponse(String status, String message, CategoryDomain data) {
        this.status = status;
        this.message = message;
        this.data = data;
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

    public CategoryDomain getData() {
        return data;
    }

    public void setData(CategoryDomain data) {
        this.data = data;
    }
}
