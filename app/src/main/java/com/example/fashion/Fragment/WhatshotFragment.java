package com.example.fashion.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashion.Adapter.FavoriteAdapter;
import com.example.fashion.Domain.Favorite;
import com.example.fashion.Helper.RetrofitClient;
import com.example.fashion.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WhatshotFragment extends Fragment {
    public RecyclerView recyclerView;
    public FavoriteAdapter favoriteAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_whatshot, container, false);
        initView(view); // Initialize views here
        sendRequest();
        return view;
    }

    private void sendRequest() {
        String auth ="token 4ff24a3114344bc978419193eacdbca8316a82c8";
        Call<List<Favorite>> call = RetrofitClient.getInstance().getServerDetail().getUserFavorite(auth);
        call.enqueue(new Callback<List<Favorite>>() {
            @Override
            public void onResponse(Call<List<Favorite>> call, Response<List<Favorite>> response) {
                List<Favorite> favorites = response.body();
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false));
                favoriteAdapter = new FavoriteAdapter(favorites);
                recyclerView.setAdapter(favoriteAdapter);
            }

            @Override
            public void onFailure(Call<List<Favorite>> call, Throwable t) {

            }
        });
    }

    private void initView(View view) {

        recyclerView = view.findViewById(R.id.listview);

    }
}