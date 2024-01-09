package com.example.fashion.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.fashion.Adapter.ImagePagerAdapter;
import com.example.fashion.Adapter.ReviewAdapter;
import com.example.fashion.Domain.CartProduct;
import com.example.fashion.Domain.CreateReview;
import com.example.fashion.Domain.Favorite;
import com.example.fashion.Domain.ProductDetail;
import com.example.fashion.Helper.ManagementCart;
import com.example.fashion.Helper.RetrofitClient;
import com.example.fashion.R;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsFragment extends Fragment {

    private Button addToCartBtn;
    private RecyclerView.Adapter adapterReview;
    private int productId;
    private ViewPager2 viewPager;
    private ManagementCart managementCart;

    private CartProduct cartProduct;
    private RecyclerView recyclerReview;
    private RatingBar ratingBar;
    private EditText editTextReview;
    private Button editBttReview;
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
         productId = getActivity().getIntent().getIntExtra("product_id", 0);
        //        if (isAuthent) {
//            String userAuth = tinyDB.getString("userAuth");
        String userAuth = "token 4ff24a3114344bc978419193eacdbca8316a82c8";
        Call<ProductDetail> call = RetrofitClient.getInstance().getServerDetail().getProductDetail(userAuth,productId);
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
                float rate = (float) (0.0+item.getRate());
                ratingBar.setRating(rate);
                recyclerReview.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter(item.getImage());
                viewPager.setAdapter(imagePagerAdapter);
                adapterReview = new ReviewAdapter(item);
                recyclerReview.setAdapter(adapterReview);
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
        editBttReview = view.findViewById(R.id.editBttReview);
        editTextReview = view.findViewById(R.id.editTextReview);
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
        fovortieBtn =view.findViewById(R.id.fovortieBtn);
//        shareButton =view. findViewById(R.id.shareButton);
//        managmentCart = new ManagmentCart(this);
        backBtn =view. findViewById(R.id.backArrowBtn);
        ratingBar =view.findViewById(R.id.ratingBar);
        viewPager = view.findViewById(R.id.viewPaper_img);
        editBttReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendReviewRequest();
            }
        });
        cartProduct = new CartProduct();

        managementCart = new ManagementCart(getActivity().getApplicationContext());
        addToCartBtn =view.findViewById(R.id.addToCartBtn);

        fovortieBtn.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                fovortieBtn.setLiked(true);
                addToFavorite();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                removeFromFavorite();
            }
        });
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           managementCart.AddProduct(cartProduct);
            }
        });
    }

    public void addToFavorite(){
        String auth ="token 4ff24a3114344bc978419193eacdbca8316a82c8";

        Call<Favorite> call = RetrofitClient.getInstance().getServerDetail().postFavoriteAdd(auth,cartProduct);
        call.enqueue(new Callback<Favorite>() {
            @Override
            public void onResponse(Call<Favorite> call, Response<Favorite> response) {
            }

            @Override
            public void onFailure(Call<Favorite> call, Throwable t) {
            }
        });
    }

    public void removeFromFavorite(){
        String auth ="token 4ff24a3114344bc978419193eacdbca8316a82c8";
        List<Favorite> favoriteList = new ArrayList<>();
        Call<Favorite> call = RetrofitClient.getInstance().getServerDetail().postFavoriteDelete(auth,cartProduct.getProduct());
        call.enqueue(new Callback<Favorite>() {
            @Override
            public void onResponse(Call<Favorite> call, Response<Favorite> response) {

            }

            @Override
            public void onFailure(Call<Favorite> call, Throwable t) {

            }
        });

    }

    public void sendReviewRequest(){
        if (editTextReview.getText().toString().isEmpty()){
            editTextReview.setError("يرجاء كتابة التعليق");
            return;}
        String auth ="token 4ff24a3114344bc978419193eacdbca8316a82c8";
        CreateReview review = new CreateReview(productId,editTextReview.getText().toString());
        Call<Favorite> call = RetrofitClient.getInstance().getServerDetail().postReview(auth,review);
        call.enqueue(new Callback<Favorite>() {
            @Override
            public void onResponse(Call<Favorite> call, Response<Favorite> response) {
                Toast.makeText(requireContext(),"تم ارسال تعليقك بنجاح", Toast.LENGTH_LONG).show();
                editTextReview.setText("");
            }

            @Override
            public void onFailure(Call<Favorite> call, Throwable t) {
                Toast.makeText(requireContext(),"حدث خطاء اثناء ارسال التعليق", Toast.LENGTH_LONG).show();
            }
        });
    }
}