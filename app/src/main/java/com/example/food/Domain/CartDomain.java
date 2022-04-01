package com.example.food.Domain;

import com.example.food.model.User;

import java.io.Serializable;

public class CartDomain implements Serializable {
    private User user;
    private ProductDomain productDomain;
    private int quantity;

    public CartDomain(User user, ProductDomain productDomain, int quantity) {
        this.user = user;
        this.productDomain = productDomain;
        this.quantity = quantity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ProductDomain getProductDomain() {
        return productDomain;
    }

    public void setProductDomain(ProductDomain productDomain) {
        this.productDomain = productDomain;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
