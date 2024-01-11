
package com.example.fashion.Domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductRate {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("rating_date")
    @Expose
    private String ratingDate;
    @SerializedName("rating_no")
    @Expose
    private Double ratingNo;
    @SerializedName("product")
    @Expose
    private Integer product;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRatingDate() {
        return ratingDate;
    }

    public void setRatingDate(String ratingDate) {
        this.ratingDate = ratingDate;
    }

    public Double getRatingNo() {
        return ratingNo;
    }

    public void setRatingNo(Double ratingNo) {
        this.ratingNo = ratingNo;
    }

    public Integer getProduct() {
        return product;
    }

    public void setProduct(Integer product) {
        this.product = product;
    }

}
