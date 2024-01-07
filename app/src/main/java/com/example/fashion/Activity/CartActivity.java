package com.example.fashion.Activity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.fashion.Fragment.CartFragment;
import com.example.fashion.R;

public class CartActivity extends AppCompatActivity {
  private ImageView backArrowBtn;
  private CheckBox selectAllCheckBox;
  private TextView totalFeeTxt,deliveryTxt,totalTxt;
    private CartFragment fragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initView();
        fragment = new CartFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void initView() {

        selectAllCheckBox = findViewById(R.id.checkBox2);
        totalTxt = findViewById(R.id.totalTxt);
        totalFeeTxt = findViewById(R.id.totalFeeTxt);
        deliveryTxt = findViewById(R.id.deliveryTxt);
        backArrowBtn = findViewById(R.id.backArrowBtn);

//        totalTxt.setText("$"+fragment.getTotal());
//        totalFeeTxt.setText("$"+fragment.getTotal());

        selectAllCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Notify the fragment about the checkbox state change
                if (fragment != null) {
                    fragment.onSelectAllChanged(isChecked);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (fragment != null && fragment.adapterlistviewCart != null) {
            totalTxt.setText("$" + fragment.adapterlistviewCart.getTotal());
            totalFeeTxt.setText("$" + fragment.adapterlistviewCart.getTotal());
        }
    }
}

