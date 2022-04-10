package com.example.food.Domain;

import com.example.food.model.User;

import java.util.Date;

public class Order {
    private int id;
    private User user;
    private Date createAt;
    private Discount discount;
    private String state;

    public Order(int id, User user, Date createAt, Discount discount, String state) {
        this.id = id;
        this.user = user;
        this.createAt = createAt;
        this.discount = discount;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
