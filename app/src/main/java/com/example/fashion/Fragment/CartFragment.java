package com.example.fashion.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashion.Adapter.CartAdapter;
import com.example.fashion.Domain.CartProduct;
import com.example.fashion.Domain.UserAuthentication;
import com.example.fashion.Helper.RetrofitClient;
import com.example.fashion.Helper.TinyDB;
import com.example.fashion.R;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment {

    public RecyclerView listviewCart;
    public CartAdapter adapterlistviewCart;
    private TinyDB tinyDB;
    private UserAuthentication userAuth;
    private boolean isAuthent;
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
        if (isAuthent) {
            userAuth = tinyDB.getObject("userAuth", UserAuthentication.class);
            Call<List<CartProduct>> call = RetrofitClient.getInstance().getServerDetail().getUserCart(userAuth.getToken());
            call.enqueue(new Callback<List<CartProduct>>() {
                @Override
                public void onResponse(Call<List<CartProduct>> call, Response<List<CartProduct>> response) {
                    List<CartProduct> products = response.body();
                    if (response.isSuccessful()) {
                        listviewCart.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                        adapterlistviewCart = new CartAdapter(products);
                        listviewCart.setAdapter(adapterlistviewCart);

                    } else {
                        ResponseBody errorResponse = response.errorBody();

                        Toast.makeText(getActivity().getApplicationContext(), "Error on Cart Request" + errorResponse.toString(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<List<CartProduct>> call, Throwable t) {

                }
            });
        }
    }

    public void onSelectAllChanged(boolean isChecked) {
        if (adapterlistviewCart != null) {
            adapterlistviewCart.selectAllItems(isChecked);

            // Get the selected items
            List<CartProduct> selectedItems = adapterlistviewCart.getSelectedItems();
            // Do something with the selected items (e.g., display them, perform an action)
            for (CartProduct selectedItem : selectedItems) {
                // Do something with each selected item
                // You can access selectedItem.getId(), selectedItem.getName(), etc.
            }
        }
    }
    public Double getTotal(){
//        List<CartProduct> selectedItems = adapterlistviewCart.getSelectedItems();
//        // Do something with the selected items (e.g., display them, perform an action)
//        Double total = 0.0;
//        if (selectedItems.size() > 0) {
//            for (CartProduct selectedItem : selectedItems) {
//                total += selectedItem.getTotal();
//            }
//        }
//
//        return total;
       return adapterlistviewCart.getTotal();
    }
    private void initView(View view) {
        listviewCart = view.findViewById(R.id.listviewCart);
        tinyDB = new TinyDB(requireContext());
        isAuthent = tinyDB.getBoolean("isAuthent");

    }

    @Override
    public void onStop() {
        super.onStop();
        adapterlistviewCart.managmentCart.commit();
    }
}