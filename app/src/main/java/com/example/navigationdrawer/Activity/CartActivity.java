package com.example.navigationdrawer.Activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.navigationdrawer.R;

public class CartActivity extends AppCompatActivity {
  private ImageView backArrowBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

    }
}

