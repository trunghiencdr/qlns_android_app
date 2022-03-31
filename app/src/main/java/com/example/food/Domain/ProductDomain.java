package com.example.food.Domain;

import java.io.Serializable;
import java.util.List;

public class ProductDomain implements Serializable {
    private Long productId;
    private Long categoryId;
    private String name;
    private float price;
    private String calculationUnit;
    private int total;
    private String description;
    private String slug;
    private boolean display;
    private float rate;
    private float discount;
    private int id;
    private String url;
    private int year;
    private List<ImageDomain> images;

    public ProductDomain(Long productId, Long categoryId, String name, float price, String calculationUnit, int total, String description, String slug, boolean display, float rate, float discount, int id, String url, int year, List<ImageDomain> images) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.calculationUnit = calculationUnit;
        this.total = total;
        this.description = description;
        this.slug = slug;
        this.display = display;
        this.rate = rate;
        this.discount = discount;
        this.id = id;
        this.url = url;
        this.year = year;
        this.images = images;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<ImageDomain> getImages() {
        return images;
    }

    public void setImages(List<ImageDomain> images) {
        this.images = images;
    }
}
