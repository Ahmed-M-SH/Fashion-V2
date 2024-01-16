package com.example.fashion.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.fashion.Adapter.OrderDetailAdapter;
import com.example.fashion.Domain.OrderDetail;
import com.example.fashion.Domain.UserAuthentication;
import com.example.fashion.Helper.RetrofitClient;
import com.example.fashion.Helper.TinyDB;
import com.example.fashion.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderListFragment extends Fragment {

    private RecyclerView orderRecyclerView;
    private OrderDetailAdapter orderDetailAdapter;
    private TinyDB tinyDB;
    private UserAuthentication userAuth;
    private boolean isAuthent;
    private ProgressBar progressBar10;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);
        initView(view);
        progressBar10.setVisibility(View.VISIBLE);
        sendRequest();
        progressBar10.setVisibility(View.GONE);
        return view;
    }

    private void sendRequest() {
        if (isAuthent) {
            userAuth = tinyDB.getObject("userAuth", UserAuthentication.class);
            Call<List<OrderDetail>> call = RetrofitClient.getInstance().getServerDetail().getOrdersList(userAuth.getToken());
            call.enqueue(new Callback<List<OrderDetail>>() {
                @Override
                public void onResponse(Call<List<OrderDetail>> call, Response<List<OrderDetail>> response) {
                    List<OrderDetail> orders = response.body();
                    orderDetailAdapter = new OrderDetailAdapter(orders);
                    orderRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, true));
                    orderRecyclerView.setAdapter(orderDetailAdapter);
                }

                @Override
                public void onFailure(Call<List<OrderDetail>> call, Throwable t) {

                }
            });
        }
    }

    private void initView(View view) {
        orderRecyclerView = view.findViewById(R.id.orderRecyclerView);
        tinyDB = new TinyDB(requireContext());
        progressBar10 = view.findViewById(R.id.progressBar10);
        isAuthent = tinyDB.getBoolean("isAuthent");
    }
}