package com.example.fashion.Helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;

import com.example.fashion.Adapter.HomeProductAdapter;
import com.example.fashion.Adapter.ImagePagerAdapter;
import com.example.fashion.Domain.ProductDetail;
import com.example.fashion.Domain.ProductResult;
import com.example.fashion.Domain.UserAuthentication;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagmentProduct {

    private TinyDB tinyDB;
    private Context context;
    private UserAuthentication userAuth;
    private ProductResult productResult;
    private ProductDetail productDetail;
    private boolean isAuthent;

    public ManagmentProduct(Context context) {
        this.context = context;
        tinyDB = new TinyDB(context);
        isAuthent= tinyDB.getBoolean("isAuthent");

    }
    public ProductResult getHomeProduct() {
        if (isAuthent) {
            userAuth = tinyDB.getObject("userAuth", UserAuthentication.class);
        Call<ProductResult> call = RetrofitClient.getInstance()
                .getServerDetail()
                .getProduct(userAuth.getToken());
        call.enqueue(new Callback<ProductResult>() {
            @Override
            public void onResponse(Call<ProductResult> call, Response<ProductResult> response) {
                ProductResult item = response.body();
                productResult = item;
            }

            @Override
            public void onFailure(Call<ProductResult> call, Throwable t) {
                Toast.makeText(context, "An error has occured on product", Toast.LENGTH_LONG)
                        .show();
                productResult = null;
            }
        });
    }
             else {
            Call<ProductResult> call = RetrofitClient.getInstance()
                    .getServerDetail()
                    .getProduct();

            call.enqueue(new Callback<ProductResult>() {
                @Override
                public void onResponse(Call<ProductResult> call, Response<ProductResult> response) {
                    ProductResult item = response.body();
                    productResult = item;
                }
                @Override
                public void onFailure(Call<ProductResult> call, Throwable t) {
                    Toast.makeText(context, "An error has occured on product", Toast.LENGTH_LONG)
                            .show();
                    productResult = null;
                }
            });
        }
        return productResult;
    }
    public ProductDetail getProductDetail(int productId) {
        if (isAuthent) {
            userAuth = tinyDB.getObject("userAuth", UserAuthentication.class);
        Call<ProductDetail> call = RetrofitClient.getInstance().getServerDetail().getProductDetail(productId);
        call.enqueue(new Callback<ProductDetail>() {
            @Override
            public void onResponse(Call<ProductDetail> call, retrofit2.Response<ProductDetail> response) {
                ProductDetail item =response.body();
//                if (response.isSuccessful())

                    productDetail =item;
//                else
//                    productDetail =null;
            }
            @Override
            public void onFailure(Call<ProductDetail> call, Throwable t) {
                Toast.makeText(context, "An error has occured", Toast.LENGTH_LONG).show();
//                productDetail = null;
            }
        });
        }
        else{

            Call<ProductDetail> call = RetrofitClient.getInstance().getServerDetail().getProductDetail(productId);
            call.enqueue(new Callback<ProductDetail>() {
                @Override
                public void onResponse(Call<ProductDetail> call, retrofit2.Response<ProductDetail> response) {
                    ProductDetail item = response.body();
                    if (response.isSuccessful())
                        productDetail =item;
                    else
                        productDetail =null;
                }
                @Override
                public void onFailure(Call<ProductDetail> call, Throwable t) {
                    Toast.makeText(context, "An error has occured", Toast.LENGTH_LONG).show();
                    productDetail = null;
                }
            });
        }
        return productDetail;
    }

}
