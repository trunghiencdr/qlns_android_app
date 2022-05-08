package com.example.food.Domain;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.food.model.User;

import java.util.Date;
import java.util.Objects;

public class Order {
    private int id;
    private User user;
    private Date createAt;
    private Discount discount;
    private String state;

    public Order() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return getId() == order.getId() && Objects.equals(getUser(), order.getUser()) && Objects.equals(getCreateAt(), order.getCreateAt()) && Objects.equals(getDiscount(), order.getDiscount()) && Objects.equals(getState(), order.getState());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getCreateAt(), getDiscount(), getState());
    }

    public static DiffUtil.ItemCallback<Order> itemCallback = new DiffUtil.ItemCallback<Order>() {
        @Override
        public boolean areItemsTheSame(@NonNull Order oldItem, @NonNull Order newItem) {
            return oldItem.getId()== newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Order oldItem, @NonNull Order newItem) {
            return oldItem.equals(newItem);
        }
    };
}
