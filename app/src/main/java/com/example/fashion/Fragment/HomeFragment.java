package com.example.fashion.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashion.Activity.NotificationActivity;
import com.example.fashion.Adapter.HomeCategoryAdapter;
import com.example.fashion.Adapter.HomeProductAdapter;
import com.example.fashion.Domain.Category;
import com.example.fashion.Domain.NotificationDomain;
import com.example.fashion.Domain.ProductResult;
import com.example.fashion.Helper.RetrofitClient;
import com.example.fashion.Helper.TinyDB;
import com.example.fashion.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    private RecyclerView productrecyclerView,categoryRecyclerView;
    RecyclerView.Adapter productAdapter,categoryAdapter;
    AppCompatButton notification_status;
    private ImageView notificationImg;
    private TinyDB tinyDB;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initView(view);
        sendProductRequest();
        sendCategoryRequest();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view); // Initialize views here
        return view;
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

    private void initView(View view) {
        productrecyclerView=view.findViewById(R.id.itme_recyclerView);
        categoryRecyclerView =view.findViewById(R.id.icon_recyclerView);
        notificationImg = view.findViewById(R.id.notificationImg);
        notification_status = view.findViewById(R.id.noitficatin_stetes);
        tinyDB = new TinyDB(getActivity().getApplicationContext());
        List<NotificationDomain> notificationCounts = tinyDB.getListObjectNotification("unReadNotifications");
        if (notificationCounts.size() == 0) {
            notification_status.setVisibility(View.INVISIBLE);
        }
        notification_status.setText(""+notificationCounts.size());
        notificationImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), NotificationActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }
}