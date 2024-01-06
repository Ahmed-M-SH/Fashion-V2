package com.example.navigationdrawer.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawer.Domain.CartProduct;
import com.example.navigationdrawer.Helper.RetrofitClient;
import com.example.navigationdrawer.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment {

    private RecyclerView listviewCart;
    private RecyclerView.Adapter AdapterlistviewCart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        initView(view); // Initialize views here
        sendRequest();
        return view;
    }

    private void sendRequest() {
        String auth ="token 4ff24a3114344bc978419193eacdbca8316a82c8";
        Call<List<CartProduct>> call = RetrofitClient.getInstance().getServerDetail().getUserCart(auth);
        call.enqueue(new Callback<List<CartProduct>>() {
            @Override
            public void onResponse(Call<List<CartProduct>> call, Response<List<CartProduct>> response) {
                List<CartProduct> products = response.body();

            }

            @Override
            public void onFailure(Call<List<CartProduct>> call, Throwable t) {

            }
        });
    }

    private void initView(View view) {


    }
}