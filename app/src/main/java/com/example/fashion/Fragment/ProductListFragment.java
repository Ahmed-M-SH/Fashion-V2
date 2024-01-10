package com.example.fashion.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fashion.Adapter.HomeProductAdapter;
import com.example.fashion.Domain.ProductResult;
import com.example.fashion.Helper.RetrofitClient;
import com.example.fashion.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListFragment extends Fragment {
    private HomeProductAdapter productAdapter;
    private RecyclerView productrecyclerView;
    private int categoryId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        initView(view); // Initialize views here
        sendRequest();
        return view;
    }
    private void sendRequest() {
        Call<ProductResult> call = RetrofitClient.getInstance()
                .getServerDetail()
                .getProduct(categoryId);
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
        productrecyclerView = view.findViewById(R.id.productRecyclerView);
        categoryId = getActivity().getIntent().getIntExtra("category", 0);
    }
}