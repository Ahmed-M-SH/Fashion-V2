package com.example.fashion.Domain;

import java.io.Serializable;

public class ReviewDomain implements Serializable {

    private String reviewUser;
    private String reviewDate;

    public ReviewDomain(String reviewUser, String reviewDate, String reviewText) {
        this.reviewUser = reviewUser;
        this.reviewDate = reviewDate;
        this.reviewText = reviewText;
    }

    public String getReviewUser() {
        return reviewUser;
    }

    public void setReviewUser(String reviewUser) {
        this.reviewUser = reviewUser;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    private String reviewText;
}
