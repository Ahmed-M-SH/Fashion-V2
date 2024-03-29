package com.example.fashion.Helper;

import com.example.fashion.Domain.CartProduct;
import com.example.fashion.Domain.CartService;
import com.example.fashion.Domain.Category;
import com.example.fashion.Domain.CreateReview;
import com.example.fashion.Domain.Favorite;
import com.example.fashion.Domain.MakeOreder;
import com.example.fashion.Domain.NotificationDomain;
import com.example.fashion.Domain.OrderDetail;
import com.example.fashion.Domain.PaymentDetail;
import com.example.fashion.Domain.ProductDetail;
import com.example.fashion.Domain.ProductRate;
import com.example.fashion.Domain.ProductResult;
import com.example.fashion.Domain.Promotion;
import com.example.fashion.Domain.ReviewLikes;
import com.example.fashion.Domain.UpdateUserProfile;
import com.example.fashion.Domain.UserAuthentication;
import com.example.fashion.Domain.UserProfile;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public  interface ServerDetail {
    static String port = "8000";
//    public static String endpoint = "http://192.168.1.2:" + port;
        public static String endpoint = "https://ahmedcoding.pythonanywhere.com";


    //    -------------- GET Methods  --------------
    @GET("/api/products/")
    Call <ProductResult> getProduct();
    @GET("/api/products/promotion/")
    Call <List<Promotion>> getPromotion();

    @GET("/api/products/")
    Call <ProductResult> getProduct(@Query("category") int category);

    @GET("/api/products/")
    Call <ProductResult> searchProduct(@Query("search") String search);
    @GET("/api/products/")
    Call <ProductResult> getProduct(@Header("Authorization") String authorization);
    @GET("/api/categorys/")
    Call <List<Category>> getCategory();
    @GET("/api/categorys/")
    Call <List<Category>> getCategory(@Query("parent") Integer parent);

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
    @GET("/api/orders/payment-detail/")
    Call<PaymentDetail> getPaymentDetail(@Header("Authorization") String authorization);

    @GET("/api/orders/")
    Call<List<OrderDetail>> getOrdersList(@Header("Authorization") String authorization);


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
    @POST("/api/products/review/create/")
    Call <Favorite> postReview(@Header("Authorization") String authorization, @Body CreateReview review);
    @POST("/api/products/rating/")
    Call <ProductRate> postRating(@Header("Authorization") String authorization, @Body ProductRate review);
    @POST("/api/favorite/create/")
    Call<Favorite> postFavoriteAdd(@Header("Authorization") String authorization,@Body CartProduct product);


    @Multipart
    @POST("/api/orders/create/")
    Call<MakeOreder> postMakeOrderWithImage(
            @Header("Authorization") String authorization,
            @Part("city") RequestBody city,
            @Part("currency") RequestBody currency,
            @Part("payment_type") RequestBody paymentType,
            @Part("customer_phone") RequestBody customerPhone,
            @Part("customer_phone2") RequestBody customerPhone2,
            @Part("address") RequestBody address,
            @Part MultipartBody.Part image,
            @Part("order_items") RequestBody orderItems
    );


    @POST("/api/orders/create/")
    Call<MakeOreder> postMakeOrder(@Header("Authorization") String authorization, @Body MakeOreder order);


    @Multipart
    @PUT("/api/orders/{order_id}/update/")
    Call <MakeOreder> updateOrderImage(@Header("Authorization") String authorization,@Path("order_id") int productId,
    @Part MultipartBody.Part image
    );

    @POST("/api/auth/login/")
    Call<UserAuthentication> getUserAuthentication(@Body UserAuthentication userAuthentication);

    @POST("/api/auth/sginup/")
    Call<UserProfile> getUserRegistration(@Body UserProfile userProfile);

    @POST("/api/products/reviewlike/create/")
    Call<ReviewLikes> postReviewLike(@Header("Authorization") String authorization, @Body ReviewLikes reviewLikes);

    @DELETE("/api/products/reviewlike/{review_id}/delete/")
    Call<ReviewLikes> deleteReviewLike(@Header("Authorization") String authorization,@Path("review_id") int reviewId);


    @Multipart
    @PATCH("/api/user/profile/update/")
    Call<UserProfile> postProfileImage(
            @Header("Authorization") String authorization,
            @Part MultipartBody.Part image
    );
    @PATCH("/api/user/profile/update/")
    Call<UserProfile> updateProfileUser(
            @Header("Authorization") String authorization,
            @Body UpdateUserProfile profile
    );

}
