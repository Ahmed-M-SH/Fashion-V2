package com.example.fashion.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.fashion.R;

public class PaymentActivity extends AppCompatActivity {
    private ImageView backBttnn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        backBttnn = findViewById(R.id.backBttnn);
        backBttnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // إنشاء Intent للانتقال إلى الصفحة التالية
                Intent intent = new Intent(PaymentActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

    }
}