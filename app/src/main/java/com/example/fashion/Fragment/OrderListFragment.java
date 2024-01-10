package com.example.fashion.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fashion.Adapter.OrderDetailAdapter;
import com.example.fashion.Domain.OrderDetail;
import com.example.fashion.Helper.RetrofitClient;
import com.example.fashion.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderListFragment extends Fragment {

    private RecyclerView orderRecyclerView;
    private OrderDetailAdapter orderDetailAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);
        initView(view);
        sendRequest();
        return view;
    }

    private void sendRequest() {
        String auth ="token 4ff24a3114344bc978419193eacdbca8316a82c8";
        Call<List<OrderDetail>> call = RetrofitClient.getInstance().getServerDetail().getOrdersList(auth);
        call.enqueue(new Callback<List<OrderDetail>>() {
            @Override
            public void onResponse(Call<List<OrderDetail>> call, Response<List<OrderDetail>> response) {
                List<OrderDetail> orders = response.body();
                orderDetailAdapter = new OrderDetailAdapter(orders);
                orderRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,true));
                orderRecyclerView.setAdapter(orderDetailAdapter);
            }

            @Override
            public void onFailure(Call<List<OrderDetail>> call, Throwable t) {

            }
        });
    }

    private void initView(View view) {
        orderRecyclerView = view.findViewById(R.id.orderRecyclerView);
    }
}