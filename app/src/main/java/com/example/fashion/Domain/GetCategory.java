package com.example.fashion.Domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCategory {

    @SerializedName("parent")
    @Expose
    private Integer parent;

    public Integer getParent() {
        return parent;
    }
    public void setParent(Integer parent) {
        this.parent = parent;
    }
}
