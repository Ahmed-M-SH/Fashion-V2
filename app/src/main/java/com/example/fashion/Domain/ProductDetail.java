
package com.example.fashion.Domain;

import java.util.ArrayList;
import java.util.List;

import com.example.fashion.Helper.ServerDetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductDetail {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("rate")
    @Expose
    private Double rate;
    @SerializedName("review_count")
    @Expose
    private Integer reviewCount;
    @SerializedName("review")
    @Expose
    private List<Review> review;
    @SerializedName("in_favorite")
    @Expose
    private Boolean inFavorite;
    @SerializedName("in_cart")
    @Expose
    private Boolean inCart;
    @SerializedName("image")
    @Expose
    private List<String> image;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("category")
    @Expose
    private Integer category;

    @SerializedName("new_price")
    @Expose
    private Double new_price;

    @SerializedName("promotion")
    @Expose
    private Double promotion;

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

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public List<Review> getReview() {
        return review;
    }

    public void setReview(List<Review> review) {
        this.review = review;
    }

    public Boolean getInFavorite() {
        return inFavorite;
    }

    public void setInFavorite(Boolean inFavorite) {
        this.inFavorite = inFavorite;
    }

    public Boolean getInCart() {
        return inCart;
    }

    public void setInCart(Boolean inCart) {
        this.inCart = inCart;
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

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

}
