package com.example.fashion.Models;

import android.graphics.Bitmap;
public class ModelClass {
    private String name, price;
    private Bitmap image;
    public ModelClass(String name, String price, Bitmap image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public Bitmap getImage() {
        return image;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }
}