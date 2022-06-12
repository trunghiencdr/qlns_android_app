package com.example.food.Domain;


import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;


public class Comment {
    private int productId;
    private int orderId;
    private Date createAt;
    private int rating;
    private String comment;

    public Comment() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Comment(int productId, int orderId, Date createAt, int rating, String comment) {
        this.productId = productId;
        this.orderId = orderId;
        this.createAt = createAt;
        this.rating = rating;
        this.comment = comment;
    }
}
