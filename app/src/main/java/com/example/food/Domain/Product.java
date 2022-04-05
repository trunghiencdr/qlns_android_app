package com.example.food.Domain;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

public class Product {
    private long productId;
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
    private Image image;
    private List<ImageDomain> images;
    public Product() {
    }

    public List<ImageDomain> getImages() {
        return images;
    }

    public void setImages(List<ImageDomain> images) {
        this.images = images;
    }

    public Product(long productId, String name, float price, String calculationUnit,
                   int total, String description, String slug, boolean display, float rate,
                   float discount, int id, String url, int year, Image image, List<ImageDomain> iamges) {
        this.productId = productId;
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
        this.image = image;
    }

    public Image getImage() {
//        System.out.println("image data:" + image.getData());


//        System.out.println(bitmap.toString());
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

//    public Bitmap getImageBitmap()  {
//        byte [] encodeByte = Base64.decode(image.getData(),Base64.DEFAULT);
//        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
//
////        Bitmap bmp = BitmapFactory.decodeByteArray(byteArrray,0, byteArrray.length);
//        return bitmap;
//    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
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
}
