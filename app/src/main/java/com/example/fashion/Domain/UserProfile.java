
package com.example.fashion.Domain;

import com.example.fashion.Helper.ServerDetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfile {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("register_data")
    @Expose
    private String registerData;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("previous_ordersast")
    @Expose
    private Integer previousOrdersast;
    @SerializedName("being_prepared_ordersast")
    @Expose
    private Integer beingPreparedOrdersast;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("user_auth")
    @Expose
    private UserAuthentication userAuth;

    public UserAuthentication getUserAuth() {
        return userAuth;
    }

    public void setUserAuth(UserAuthentication userAuth) {
        this.userAuth = userAuth;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public UserProfile() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegisterData() {
        return registerData;
    }

    public void setRegisterData(String registerData) {
        this.registerData = registerData;
    }

    public String getImage() {
        if (image != null && !image.startsWith("http")) {
            return ServerDetail.endpoint + image;
        }
        if (image == null)
            return "";
        return image;
    }

//    public void setImage(Object image) {
//        this.image = image;
//    }

    public Integer getPreviousOrdersast() {
        return previousOrdersast;
    }

    public void setPreviousOrdersast(Integer previousOrdersast) {
        this.previousOrdersast = previousOrdersast;
    }

    public Integer getBeingPreparedOrdersast() {
        return beingPreparedOrdersast;
    }

    public void setBeingPreparedOrdersast(Integer beingPreparedOrdersast) {
        this.beingPreparedOrdersast = beingPreparedOrdersast;
    }

}
