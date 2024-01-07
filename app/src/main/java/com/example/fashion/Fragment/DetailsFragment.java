package com.example.fashion.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.fashion.Adapter.ImagePagerAdapter;
import com.example.fashion.Domain.CartProduct;
import com.example.fashion.Domain.ProductDetail;
import com.example.fashion.Helper.ManagementCart;
import com.example.fashion.Helper.RetrofitClient;
import com.example.fashion.R;
import com.like.LikeButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class DetailsFragment extends Fragment {

    private Button addToCartBtn;
    private RecyclerView.Adapter adapterReview;
    private ViewPager2 viewPager;
    private ManagementCart managementCart;

    private CartProduct cartProduct;
    private RecyclerView recyclerReview;
    private TextView titleTxt, feeTxt, descriptionTxt, reviewTxt, scoreTxt, readMoreTxt,old_price;
    private ImageView picFood, backBtn,shareButton;
//    private Review reviewObject;
    private LikeButton fovortieBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sendRequest();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        initView(view); // Initialize views here
//        sendRequest();
        return view;
    }

    private void sendRequest() {
        int productId = getActivity().getIntent().getIntExtra("product_id", 0);
        Call<ProductDetail> call = RetrofitClient.getInstance().getServerDetail().getProductDetail(productId);
        call.enqueue(new Callback<ProductDetail>() {
            @Override
            public void onResponse(Call<ProductDetail> call, retrofit2.Response<ProductDetail> response) {
                ProductDetail item = response.body();
//                // Populate UI elements with data
//                Glide.with()
//                        .load(item.getImage())
//                        .into(picFood);
                Log.i("OnSuccesses", "Respnse: " + response.body().toString());

                titleTxt.setText("" + item.getName());
                descriptionTxt.setText(item.getDescription());
                reviewTxt.setText("" + item.getReviewCount() + "");
                scoreTxt.setText(item.getRate() + "");
                feeTxt.setText("$" + item.getPrice());
                old_price.setText("$"+item.getNew_price());
                if (item.getInFavorite())
                    fovortieBtn.setLiked(true);
//                recyclerReview.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter(item.getImage());
                viewPager.setAdapter(imagePagerAdapter);
//                adapterReview = new ReviewAdapter(item);
//                recyclerReview.setAdapter(adapterReview);

                cartProduct.setProduct(item.getId());
            }

            @Override
            public void onFailure(Call<ProductDetail> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                Log.i("onFailure", "Error: " + t.getMessage());
            }
        });
    }


    private void initView(View view) {
        recyclerReview =view. findViewById(R.id.recylerReview);
        addToCartBtn = view.findViewById(R.id.addToCartBtn);
        titleTxt =view.findViewById(R.id.titleTxt);
        feeTxt =view.findViewById(R.id.priceTxt);
        descriptionTxt =view. findViewById(R.id.descriptionTxt);
        reviewTxt =view. findViewById(R.id.reviewTxt);
        scoreTxt =view. findViewById(R.id.rateTxt);
        old_price = view.findViewById(R.id.old_price);
//        picFood =view. findViewById(R.id.MakUp);
        readMoreTxt =view. findViewById(R.id.readMoreTxt);
//        fovortieBtn =view.findViewById(R.id.fovortieBtn);
//        shareButton =view. findViewById(R.id.shareButton);
//        managmentCart = new ManagmentCart(this);
        backBtn =view. findViewById(R.id.backArrowBtn);
        viewPager = view.findViewById(R.id.viewPaper_img);
        cartProduct = new CartProduct();

        managementCart = new ManagementCart(getActivity().getApplicationContext());
        addToCartBtn =view.findViewById(R.id.addToCartBtn);

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           managementCart.AddProduct(cartProduct);
            }
        });

    }
}