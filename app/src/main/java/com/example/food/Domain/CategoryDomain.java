package com.example.food.Domain;

import java.io.Serializable;
import java.util.List;

public class CategoryDomain implements Serializable {
    private int id;
    private String name;
    private String description;
    private List<Product> products;

    public CategoryDomain(int categoryId, String categoryName, String description, List<Product> products) {
        this.id = categoryId;
        this.name = categoryName;
        this.description = description;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "CategoryDomain{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

