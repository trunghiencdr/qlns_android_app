package com.example.food.Domain;
public class CategoryDomain {
    private String id;
    private String name;
    private String description;

    public CategoryDomain(String categoryId, String categoryName, String description) {
        this.id = categoryId;
        this.name = categoryName;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public void setDescription(String description) {
        this.description = description;
    }
}

