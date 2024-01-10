package com.example.fashion.Domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderItem {

    @SerializedName("product")
    @Expose
    private Integer product;

    @SerializedName("qty")
    @Expose
    private Integer qty;
    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
// Add constructor, getters, and setters
    }

