package com.example.navigationdrawer.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawer.Adapter.HomeCategoryAdapter;
import com.example.navigationdrawer.Adapter.HomeProductAdapter;
import com.example.navigationdrawer.Domain.Category;
import com.example.navigationdrawer.Domain.CategoryDeserializer;
import com.example.navigationdrawer.Domain.ProductResult;
import com.example.navigationdrawer.Helper.RetrofitClient;
import com.example.navigationdrawer.Helper.ServerDetail;
import com.example.navigationdrawer.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment {
    private RecyclerView productrecyclerView,categoryRecyclerView;
    RecyclerView.Adapter productAdapter,categoryAdapter;

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
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Category.class, new CategoryDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerDetail.endpoint)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ServerDetail serverDetail =  retrofit.create(ServerDetail.class);
        Call<List<Category>> call = serverDetail.getCategory();
//                .getServerDetail()
//                .getCategory();

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
    }
}