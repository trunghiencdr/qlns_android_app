package com.example.food.Domain;

import java.io.Serializable;

public class Image implements Serializable {
    private  String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
