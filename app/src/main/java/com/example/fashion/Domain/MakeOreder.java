package com.example.fashion.Domain;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MakeOreder {

    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("customer_phone")
    @Expose
    private String customerPhone;
    @SerializedName("customer_phone2")
    @Expose
    private String customerPhone2;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("city")
    @Expose
    private Integer city;
    @SerializedName("currency")
    @Expose
    private Integer currency;
    @SerializedName("payment_type")
    @Expose
    private Integer paymentType;
    @SerializedName("order_items")
    @Expose
    private List<CartProduct> orderItems;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerPhone2() {
        return customerPhone2;
    }

    public void setCustomerPhone2(String customerPhone2) {
        this.customerPhone2 = customerPhone2;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public List<CartProduct> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<CartProduct> orderItems) {
        this.orderItems = orderItems;
    }
}
