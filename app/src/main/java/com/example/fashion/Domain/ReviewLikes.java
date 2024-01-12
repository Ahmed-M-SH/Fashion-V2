package com.example.fashion.Domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ReviewLikes {

    @SerializedName("review")
    @Expose
    private Integer review;

    public Integer getReview() {
        return review;
    }
    public void setReview(Integer review) {
        this.review = review;
    }

    public ReviewLikes(Integer review) {
        this.review = review;
    }
}
