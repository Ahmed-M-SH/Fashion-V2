package com.example.navigationdrawer.Helper;

import com.example.navigationdrawer.Domain.CartProduct;
import com.example.navigationdrawer.Domain.Category;
import com.example.navigationdrawer.Domain.ProductDetail;
import com.example.navigationdrawer.Domain.ProductResult;
import com.example.navigationdrawer.Domain.UserAuthentication;
import com.example.navigationdrawer.Domain.UserProfile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public  interface ServerDetail {
    static String port = "8000";
    public static String endpoint = "http://192.168.1.2:" + port;

    @GET("/api/products/")
    Call <ProductResult> getProduct();
    @GET("/api/categorys/")
    Call <List<Category>> getCategory();

    @GET("/api/products/{productId}/")
    Call<ProductDetail> getProductDetail(@Path("productId") int productId);
    @GET("/api/user/profile/")
    Call<List< UserProfile>> getUserProfile(@Header("Authorization") String authorization);
    @GET("/api/user/cart/")
    Call<List<CartProduct>> getUserCart(@Header("Authorization") String authorization);
    @POST("/api/auth/login/")
    Call<UserAuthentication> getUserAuthentication(@Body UserAuthentication userAuthentication);

    @POST("/api/auth/sginup/")
    Call<UserProfile> getUserRegistration(@Body UserProfile userProfile);

}
