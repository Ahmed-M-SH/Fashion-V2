package com.example.fashion.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fashion.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
public class SearchActivity extends AppCompatActivity {
    private View view;

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

//    public void performSearch(View view) {
//        EditText searchEditText = findViewById(R.id.searchEditText);
//        String searchTerm = searchEditText.getText().toString().trim();
//
//        // قم بتنفيذ البحث باستخدام المصطلح المدخل (searchTerm)
//        // يمكنك تنفيذ البحث بالطريقة التي تناسب احتياجاتك مثل استدعاء واجهة برمجة التطبيقات (API) أو البحث في قاعدة البيانات الخاصة بك.
//    }
}}