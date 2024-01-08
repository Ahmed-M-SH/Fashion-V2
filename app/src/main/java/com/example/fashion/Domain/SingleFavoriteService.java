package com.example.fashion.Domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleFavoriteService {

    @SerializedName("product")
    @Expose
    private Product product;

}
