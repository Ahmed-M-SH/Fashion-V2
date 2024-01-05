package com.example.navigationdrawer.Domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ErrorResponse {
    @SerializedName("email")
    private List<String> emailErrors;

    public List<String> getEmailErrors() {
        return emailErrors;
    }
}