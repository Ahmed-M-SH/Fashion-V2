package com.example.fashion.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashion.Adapter.HomeProductAdapter;
import com.example.fashion.Domain.ProductResult;
import com.example.fashion.Helper.RetrofitClient;
import com.example.fashion.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private View view;
    private ProgressBar progressBar12;
    private TextView resiltTxt;
    private HomeProductAdapter adapter;
    private EditText searchEditText;
    private RecyclerView search_recylerView;
    private Button searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_search);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    return true;

                case R.id.bottom_search:
                    return true;

                case R.id.bottom_whatshot:
                    startActivity(new Intent(getApplicationContext(), WhatshotActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    return true;
                case R.id.bottom_shopping_cart:
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    return true;
            }
            return false;
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_search:

                    return true;
                case R.id.bottom_whatshot:
                    startActivity(new Intent(getApplicationContext(), WhatshotActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_shopping_cart:
                    startActivity(new Intent(getApplicationContext(), CartActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
            }
            return false;
        });


    }

    public void sendRequest(){
        String searchTerm = searchEditText.getText().toString();

        if (searchTerm.isEmpty()){
            searchEditText.setError("الرجاء ادخال اسم او سعر المنتج");
            return;
        }
        progressBar12.setVisibility(View.VISIBLE);
        Call<ProductResult> call = RetrofitClient.getInstance().getServerDetail().searchProduct(searchTerm);
        call.enqueue(new Callback<ProductResult>() {
            @Override
            public void onResponse(Call<ProductResult> call, Response<ProductResult> response) {
                if (response.body().getResults().size() > 0) {
                    search_recylerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
                    adapter = new HomeProductAdapter(response.body());
                    search_recylerView.setAdapter(adapter);
                    resiltTxt.setText("النتائج المتاحة");
                    resiltTxt.setVisibility(View.VISIBLE);
                    progressBar12.setVisibility(View.GONE);
                }
                else {
                    resiltTxt.setText("لاتوجد نتائج متاحة");
                    resiltTxt.setVisibility(View.VISIBLE);
                    progressBar12.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<ProductResult> call, Throwable t) {

            }
        });
    }
    public void performSearch (View view){
         searchEditText = findViewById(R.id.searchEditText);
        search_recylerView = findViewById(R.id.search_recylerView);
        searchBtn = findViewById(R.id.searchBtn);
        resiltTxt = findViewById(R.id.resiltTxt);
        progressBar12 = findViewById(R.id.progressBar12);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest();
            }
        }
        );

        // قم بتنفيذ البحث باستخدام المصطلح المدخل (searchTerm)
        // يمكنك تنفيذ البحث بالطريقة التي تناسب احتياجاتك مثل استدعاء واجهة برمجة التطبيقات (API) أو البحث في قاعدة البيانات الخاصة بك.
    }
}