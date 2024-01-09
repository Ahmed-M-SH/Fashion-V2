package com.example.fashion.Domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateReview {
    @SerializedName("product")
    @Expose
    private Integer product;

    public CreateReview(Integer product, String review_text) {
        this.product = product;
        this.review_text = review_text;
    }

    public Integer getProduct() {
        return product;
    }

    public void setProduct(Integer product) {
        this.product = product;
    }

    public String getReview_text() {
        return review_text;
    }

    public void setReview_text(String review_text) {
        this.review_text = review_text;
    }

    @SerializedName("review_text")
    @Expose
    private String review_text;
}
