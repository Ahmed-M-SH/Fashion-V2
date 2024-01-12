package com.example.fashion.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.fashion.Domain.Favorite;
import com.example.fashion.Domain.UserAuthentication;
import com.example.fashion.Fragment.WhatshotFragment;
import com.example.fashion.Helper.RetrofitClient;
import com.example.fashion.Helper.TinyDB;
import com.example.fashion.R;
import com.example.fashion.Services.FashionApplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WhatshotActivity extends AppCompatActivity {
    private CheckBox selectAllWish;
    private Button deleteButton2;
    WhatshotFragment fragment;
    private TinyDB tinyDB;
    private UserAuthentication userAuth;
    private boolean isAuthent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatshot);
        if (!FashionApplication.isIsAuthent()){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
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

        tinyDB = new TinyDB(getApplicationContext());
        isAuthent = tinyDB.getBoolean("isAuthent");
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
        if (isAuthent) {
            userAuth = tinyDB.getObject("userAuth", UserAuthentication.class);
            Call<List<Favorite>> call = RetrofitClient.getInstance().getServerDetail().postFavoriteDeleteList(userAuth.getToken());
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
    }

    public void sendRequestDelete(List<Favorite> favorites) {

        if (isAuthent) {
            userAuth = tinyDB.getObject("userAuth", UserAuthentication.class);
            Call<List<Favorite>> call = RetrofitClient.getInstance().getServerDetail().postFavoriteDeleteList(userAuth.getToken(), favorites);
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
}