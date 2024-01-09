package com.example.fashion.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.fashion.Fragment.DetailsFragment;
import com.example.fashion.Fragment.PaymentFragment;
import com.example.fashion.R;

public class PaymentActivity extends AppCompatActivity {
    private ImageView backBttnn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        PaymentFragment fragment = new PaymentFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
//        backBttnn = findViewById(R.id.backBttnn);
//        backBttnn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // إنشاء Intent للانتقال إلى الصفحة التالية
//                Intent intent = new Intent(PaymentActivity.this, CartActivity.class);
//                startActivity(intent);
//            }
//        });

    }
}