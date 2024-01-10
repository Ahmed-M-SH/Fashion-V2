package com.example.fashion.Domain;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetail {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("proof_of_payment_image")
    @Expose
    private String proofOfPaymentImage;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("customer_phone")
    @Expose
    private String customerPhone;
    @SerializedName("customer_phone2")
    @Expose
    private String customerPhone2;
    @SerializedName("total_paid")
    @Expose
    private String totalPaid;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("is_delivered")
    @Expose
    private Boolean isDelivered;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("is_proof")
    @Expose
    private Boolean isProof;
    @SerializedName("user")
    @Expose
    private Integer user;



    @SerializedName("item_count")
    @Expose
    private Integer item_count;



    public Integer getItem_count() {
        return item_count;
    }

    public void setItem_count(Integer item_count) {
        this.item_count = item_count;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getProofOfPaymentImage() {
        return proofOfPaymentImage;
    }

    public void setProofOfPaymentImage(String proofOfPaymentImage) {
        this.proofOfPaymentImage = proofOfPaymentImage;
    }

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

    public String getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(String totalPaid) {
        this.totalPaid = totalPaid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getIsDelivered() {
        return isDelivered;
    }

    public void setIsDelivered(Boolean isDelivered) {
        this.isDelivered = isDelivered;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getIsProof() {
        return isProof;
    }

    public void setIsProof(Boolean isProof) {
        this.isProof = isProof;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

}
