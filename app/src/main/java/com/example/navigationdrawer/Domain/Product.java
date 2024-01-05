package com.example.navigationdrawer.Domain;


import com.example.navigationdrawer.Helper.ServerDetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Product {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("rate")
    @Expose
    private Double rate;
    @SerializedName("new_price")
    @Expose
    private Double new_price;

    @SerializedName("promotion")
    @Expose
    private Double promotion;
    @SerializedName("review")
    @Expose
    private Integer review;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private List<String> image;
    @SerializedName("category")
    @Expose
    private Integer category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getReview() {
        return review;
    }

    public void setReview(Integer review) {
        this.review = review;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImage() {
        List<String> result = new ArrayList<>();
        for (int i = 0; i <image.size(); i++) {
            if (image.get(i) != null && !image.get(i).startsWith("http")) {
                result.add(ServerDetail.endpoint + image.get(i));
            }
            else result.add(image.get(i));
        }
        return result;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Double getNew_price() {
        return new_price;
    }

    public void setNew_price(Double new_price) {
        this.new_price = new_price;
    }

    public Double getPromotion() {
        return promotion;
    }

    public void setPromotion(Double promotion) {
        this.promotion = promotion;
    }

}
