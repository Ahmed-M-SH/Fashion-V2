package com.example.fashion.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.fashion.Domain.Favorite;
import com.example.fashion.Fragment.WhatshotFragment;
import com.example.fashion.Helper.RetrofitClient;
import com.example.fashion.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WhatshotActivity extends AppCompatActivity {
    private CheckBox selectAllWish;
    private Button deleteButton2;
    WhatshotFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatshot);
        initViews();
        fragment = new WhatshotFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void initViews() {
        deleteButton2 = findViewById(R.id.deleteButton2);
        selectAllWish = findViewById(R.id.selectAllWish);

        selectAllWish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Notify the fragment about the checkbox state change
                if (fragment != null) {
                    fragment.onSelectAllChanged(isChecked);
                }
            }
        });
        deleteButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Favorite> items = fragment.getSelectedItems();
                sendRequestDelete(items);
                finish();
            }
        });
    }

    public void sendRequestDelete() {
        String auth ="token 4ff24a3114344bc978419193eacdbca8316a82c8";

        Call<List<Favorite>> call = RetrofitClient.getInstance().getServerDetail().postFavoriteDeleteList(auth);
        call.enqueue(new Callback<List<Favorite>>() {
            @Override
            public void onResponse(Call<List<Favorite>> call, Response<List<Favorite>> response) {
                fragment.sendRequest();
            }

            @Override
            public void onFailure(Call<List<Favorite>> call, Throwable t) {

            }
        });
    }

    public void sendRequestDelete(List<Favorite> favorites) {

        String auth ="token 4ff24a3114344bc978419193eacdbca8316a82c8";

        Call<List<Favorite>> call = RetrofitClient.getInstance().getServerDetail().postFavoriteDeleteList(auth,favorites);
        call.enqueue(new Callback<List<Favorite>>() {
            @Override
            public void onResponse(Call<List<Favorite>> call, Response<List<Favorite>> response) {
//                fragment.sendRequest();
//                fragment.favoriteAdapter.deleteSelectedItems();
            }

            @Override
            public void onFailure(Call<List<Favorite>> call, Throwable t) {

            }
        });
    }
}