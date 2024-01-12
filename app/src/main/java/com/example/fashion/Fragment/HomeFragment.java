package com.example.fashion.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.fashion.Activity.NotificationActivity;
import com.example.fashion.Adapter.HomeCategoryAdapter;
import com.example.fashion.Adapter.HomeProductAdapter;
import com.example.fashion.Adapter.PromotionAdapter;
import com.example.fashion.Domain.Category;
import com.example.fashion.Domain.NotificationDomain;
import com.example.fashion.Domain.ProductResult;
import com.example.fashion.Domain.Promotion;
import com.example.fashion.Helper.RetrofitClient;
import com.example.fashion.Helper.TinyDB;
import com.example.fashion.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    private RecyclerView productrecyclerView,categoryRecyclerView;
    RecyclerView.Adapter productAdapter,categoryAdapter;
    private ProgressBar progressBar;
    AppCompatButton notification_status;
    private ImageView notificationImg;
    private TinyDB tinyDB;
    private ViewPager2 viewPager;
    private PromotionAdapter promotionAdapter;

    private Handler handler;
    private Runnable runnable;
    private int delayTime = 5000; // تأخير بين كل انتقال صفحة (بالميلي ثانية)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view); // Initialize views here
        progressBar.setVisibility(View.VISIBLE);
        sendPromotionRequest();
        sendCategoryRequest();
        sendProductRequest();
        progressBar.setVisibility(View.GONE);
        return view;
    }

    private void sendPromotionRequest() {
        Call<List<Promotion>> call = RetrofitClient.getInstance().getServerDetail().getPromotion();
        call.enqueue(new Callback<List<Promotion>>() {
            @Override
            public void onResponse(Call<List<Promotion>> call, Response<List<Promotion>> response) {
                List<Promotion> promotion = response.body();
                if (promotion != null) {
                    promotionAdapter = new PromotionAdapter(promotion);
                    viewPager.setAdapter(promotionAdapter);
                        handler = new Handler();
                        runnable = new Runnable() {
                            @Override
                            public void run() {
                                if (viewPager.getCurrentItem() == viewPager.getAdapter().getItemCount() - 1) {
                                    viewPager.setCurrentItem(0);
                                } else {
                                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                                }
                                handler.postDelayed(this, delayTime);
                            }
                        };

                        handler.postDelayed(runnable, delayTime);

                } else {
                }
            }

            @Override
            public void onFailure(Call<List<Promotion>> call, Throwable t) {
                // Handle the failure case
            }
        });
    }
    private void sendCategoryRequest() {
//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(Category.class, new CategoryDeserializer())
//                .create();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(ServerDetail.endpoint)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();
//        ServerDetail serverDetail =  RetrofitClient.getInstance().create(ServerDetail.class);
        Call<List<Category>> call = RetrofitClient.getInstance()
                .getServerDetail()
                .getCategory();
//

        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
               List<Category> item = response.body();
                categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                categoryAdapter = new HomeCategoryAdapter(item);
                categoryRecyclerView.setAdapter(categoryAdapter);
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "An error has occured on Category", Toast.LENGTH_LONG)
                        .show();
                Log.i("onFailure", "Error: " + t.getMessage());
            }

        });
    }

    @Override
    public void onResume() {
        super.onResume();
        List<NotificationDomain> notificationCounts = tinyDB.getListObjectNotification("unReadNotifications");
        if (notificationCounts.size() == 0) {
            notification_status.setVisibility(View.INVISIBLE);
        }
        else {
            notification_status.setVisibility(View.VISIBLE);
            notification_status.setText("" + notificationCounts.size());
        }

    }

    private void sendProductRequest() {
        Call<ProductResult> call = RetrofitClient.getInstance()
                .getServerDetail()
                .getProduct();
        call.enqueue(new Callback<ProductResult>() {
            @Override
            public void onResponse(Call<ProductResult> call, Response<ProductResult> response) {
                ProductResult item = response.body();
                productrecyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2));
                Log.i("RESPONSE", "OnResponse: " + item);
                productAdapter = new HomeProductAdapter(item);
                productrecyclerView.setAdapter(productAdapter);
                Log.i("RESPONSE", "OnResponse: " + item);
            }


            @Override
            public void onFailure(Call<ProductResult> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "An error has occured on product", Toast.LENGTH_LONG)
                        .show();
                Log.i("onFailure", "Error: " + t.getMessage());

            }
        });
    }

//    private void initView(View view) {
//        productrecyclerView=view.findViewById(R.id.itme_recyclerView);
//        categoryRecyclerView =view.findViewById(R.id.icon_recyclerView);
//        notificationImg = view.findViewById(R.id.notificationImg);
//        notification_status = view.findViewById(R.id.noitficatin_stetes);
//        tinyDB = new TinyDB(getActivity().getApplicationContext());
//
//        notificationImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity().getApplicationContext(), NotificationActivity.class);
//                getActivity().startActivity(intent);
//            }
//        });
//
//    }
    private void initView(View view) {
        // تحضير البيانات (الصور) لعرضها في ViewPager2

        productrecyclerView = view.findViewById(R.id.itme_recyclerView);
        categoryRecyclerView = view.findViewById(R.id.icon_recyclerView);
        notificationImg = view.findViewById(R.id.notificationImg);
        notification_status = view.findViewById(R.id.noitficatin_stetes);
        tinyDB = new TinyDB(getActivity().getApplicationContext());
        viewPager = view.findViewById(R.id.view_pager);
        progressBar = view.findViewById(R.id.progressBar);
        notificationImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), NotificationActivity.class);
                getActivity().startActivity(intent);
            }
        });

        viewPager = view.findViewById(R.id.view_pager);


    }



}
