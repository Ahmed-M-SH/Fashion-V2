package com.example.navigationdrawer.Domain;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartService {
    public List<CartProduct> getCartItem() {
        return cartItem;
    }

    public void setCartItem(List<CartProduct> cartItem) {
        this.cartItem = cartItem;
    }

    @SerializedName("products")
    @Expose
    private List<CartProduct> cartItem;
}
