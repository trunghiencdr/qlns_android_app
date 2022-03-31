package com.example.food.Domain;

import java.io.Serializable;

public class GridProduct implements Serializable {
    String image;
    String name;
    float price;
    String calculationUnit;

    public GridProduct(String image, String name, float price, String calculationUnit) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.calculationUnit = calculationUnit;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCalculationUnit() {
        return calculationUnit;
    }

    public void setCalculationUnit(String calculationUnit) {
        this.calculationUnit = calculationUnit;
    }
}
