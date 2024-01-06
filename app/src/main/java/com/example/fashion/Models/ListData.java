package com.example.fashion.Models;

import android.graphics.Bitmap;

public class ListData {
       public String name, price;
    public String ingredients;
    public Bitmap image;


    public ListData(String name, String price, Bitmap image) {
            this.name = name;
            this.price = price;
            this.ingredients = ingredients;

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