package com.example.fashion.Helper;

import com.example.fashion.Domain.CartProduct;
import com.example.fashion.Domain.CartService;
import com.example.fashion.Domain.Category;
import com.example.fashion.Domain.Favorite;
import com.example.fashion.Domain.NotificationDomain;
import com.example.fashion.Domain.ProductDetail;
import com.example.fashion.Domain.ProductResult;
import com.example.fashion.Domain.UserAuthentication;
import com.example.fashion.Domain.UserProfile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public  interface ServerDetail {
    static String port = "8000";
    public static String endpoint = "http://192.168.1.2:" + port;

//    -------------- GET Methods  --------------
    @GET("/api/products/")
    Call <ProductResult> getProduct();
    @GET("/api/products/")
    Call <ProductResult> getProduct(@Header("Authorization") String authorization);
    @GET("/api/categorys/")
    Call <List<Category>> getCategory();

    @GET("/api/products/{productId}/")
    Call<ProductDetail> getProductDetail(@Path("productId") int productId);
    @GET("/api/products/{productId}/")
    Call<ProductDetail> getProductDetail(@Header("Authorization") String authorization,@Path("productId") int productId);
    @GET("/api/user/profile/")
    Call<List< UserProfile>> getUserProfile(@Header("Authorization") String authorization);

    @GET("/api/user/unread-notification/")
    Call<List<NotificationDomain>> getUnreadNotifications(@Header("Authorization") String authorization);
    @GET("/api/user/all-notification/")
    Call<List<NotificationDomain>> getAllNotifications(@Header("Authorization") String authorization);
    @GET("/api/user/read-all-notification/")
    Call<List<NotificationDomain>> ReadAllNotifications(@Header("Authorization") String authorization);
    @GET("/api/user/cart/")
    Call<List<CartProduct>> getUserCart(@Header("Authorization") String authorization);
    @GET("/api/favorite/")
    Call<List<Favorite>> getUserFavorite(@Header("Authorization") String authorization);


//   ---------------- Post Methods ----------------
    @POST("/api/user/cart/add-list/")
    Call<CartService> postCartAddList(@Header("Authorization") String authorization, @Body CartService cartItem);
    @POST("/api/user/cart/delete-list/")
    Call<CartService> postCartDeleteList(@Header("Authorization") String authorization,@Body CartService cartItem);

    @POST("/api/favorite/delete-list/")
    Call<List <Favorite>> postFavoriteDeleteList(@Header("Authorization") String authorization,@Body  List<Favorite> favoriteItem);
    @POST("/api/favorite/delete-list/")
    Call<List <Favorite>> postFavoriteDeleteList(@Header("Authorization") String authorization);

    @DELETE("/api/favorite/{product_id}/delete/")
    Call <Favorite> postFavoriteDelete(@Header("Authorization") String authorization,@Path("product_id") int productId);
    @POST("/api/favorite/create/")
    Call<Favorite> postFavoriteAdd(@Header("Authorization") String authorization,@Body CartProduct product);

    @POST("/api/auth/login/")
    Call<UserAuthentication> getUserAuthentication(@Body UserAuthentication userAuthentication);

    @POST("/api/auth/sginup/")
    Call<UserProfile> getUserRegistration(@Body UserProfile userProfile);



}
