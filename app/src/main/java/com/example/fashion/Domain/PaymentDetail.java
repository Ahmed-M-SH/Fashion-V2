package com.example.fashion.Domain;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentDetail {

    @SerializedName("currency")
    @Expose
    private List<Currency> currency;
    @SerializedName("city")
    @Expose
    private List<City> city;
    @SerializedName("payment_type")
    @Expose
    private List<PaymentType> paymentType;

    public List<Currency> getCurrency() {
        return currency;
    }

    public void setCurrency(List<Currency> currency) {
        this.currency = currency;
    }

    public List<City> getCity() {
        return city;
    }

    public void setCity(List<City> city) {
        this.city = city;
    }

    public List<PaymentType> getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(List<PaymentType> paymentType) {
        this.paymentType = paymentType;
    }

}
