package com.example.navigationdrawer.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.navigationdrawer.Fragment.DetailsFragment;
import com.example.navigationdrawer.R;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        DetailsFragment fragment = new DetailsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
//            TextView oldPriceTextView = findViewById(R.id.old_price);
//            oldPriceTextView.setText("السعر السابق: $50");
//            oldPriceTextView.setPaintFlags(oldPriceTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

    }}