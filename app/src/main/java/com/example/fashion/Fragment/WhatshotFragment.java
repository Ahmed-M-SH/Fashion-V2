package com.example.fashion.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashion.Adapter.FavoriteAdapter;
import com.example.fashion.Domain.CartProduct;
import com.example.fashion.Domain.Favorite;
import com.example.fashion.Domain.UserAuthentication;
import com.example.fashion.Helper.RetrofitClient;
import com.example.fashion.Helper.TinyDB;
import com.example.fashion.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WhatshotFragment extends Fragment {
    public RecyclerView recyclerView;
    public FavoriteAdapter favoriteAdapter;

    private TinyDB tinyDB;
    private UserAuthentication userAuth;
    private boolean isAuthent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_whatshot, container, false);
        initView(view); // Initialize views here
        sendRequest();
        return view;
    }

    public void sendRequest() {
        if (isAuthent) {
            userAuth = tinyDB.getObject("userAuth", UserAuthentication.class);
            Call<List<Favorite>> call = RetrofitClient.getInstance().getServerDetail().getUserFavorite(userAuth.getToken());
            call.enqueue(new Callback<List<Favorite>>() {
                @Override
                public void onResponse(Call<List<Favorite>> call, Response<List<Favorite>> response) {
                    List<Favorite> favorites = response.body();
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                    favoriteAdapter = new FavoriteAdapter(favorites);
//                favoriteAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(favoriteAdapter);
                }

                @Override
                public void onFailure(Call<List<Favorite>> call, Throwable t) {

                }
            });
        }
    }
public void updateData(){
                    favoriteAdapter.notifyDataSetChanged();
}

    public void onSelectAllChanged(boolean isChecked) {
        if (favoriteAdapter != null) {
            favoriteAdapter.selectAllItems(isChecked);

            // Get the selected items
            List<Favorite> selectedItems = favoriteAdapter.getSelectedItems();
            // Do something with the selected items (e.g., display them, perform an action)
            for (Favorite selectedItem : selectedItems) {
                // Do something with each selected item
                // You can access selectedItem.getId(), selectedItem.getName(), etc.
            }
        }
    }

    public List<Favorite> getSelectedItems() {

        return favoriteAdapter.getSelectedItems();
    }

    private void initView(View view) {

        recyclerView = view.findViewById(R.id.listview);
        tinyDB = new TinyDB(requireContext());
        isAuthent = tinyDB.getBoolean("isAuthent");

    }
}