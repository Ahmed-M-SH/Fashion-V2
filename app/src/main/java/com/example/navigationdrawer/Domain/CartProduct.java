
package com.example.navigationdrawer.Domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartProduct {


    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("new_price")
    @Expose
    private Double newPrice;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("promotion")
    @Expose
    private Double promotion;
    @SerializedName("product_id")
    @Expose
    private Integer product_id;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("total")
    @Expose
    private Double total;

    public void setTotal(Double total) {
        this.total = total;
    }



    @SerializedName("id")
    @Expose
    private Integer id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(Double newPrice) {
        this.newPrice = newPrice;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getPromotion() {
        return promotion;
    }

    public void setPromotion(Double promotion) {
        this.promotion = promotion;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getTotal() {

        return newPrice * qty;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public void setTotal(Double total) {
//        this.total = total;
//    }

}
