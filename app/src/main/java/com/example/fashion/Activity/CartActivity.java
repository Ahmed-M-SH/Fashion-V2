package com.example.fashion.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.fashion.Domain.CartProduct;
import com.example.fashion.Fragment.CartFragment;
import com.example.fashion.R;
import com.example.fashion.Services.FashionApplication;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
  private ImageView backArrowBtn;
  private CheckBox selectAllCheckBox;
  private TextView totalFeeTxt,deliveryTxt,totalTxt;
    private CartFragment fragment;
    private Button orderBtnn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        if (!FashionApplication.isIsAuthent()){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
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

        orderBtnn = findViewById(R.id.orderBtnn);
        orderBtnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<CartProduct> productsItem = fragment.adapterlistviewCart.getSelectedItems();
                if (productsItem.size() > 0) {
                    Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                    intent.putExtra("productItem",new ArrayList<>(productsItem));
                    startActivity(intent);
                }
                else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CartActivity.this);
                    alertDialogBuilder.setTitle("خطاء في الطلب");  // Set the title of the dialog
                    alertDialogBuilder.setMessage("يرجاء تحديد منتجاتك التي تريد طلبها");  // Set the message of the dialog

                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Positive button clicked
                            dialog.dismiss();  // Dismiss the dialog if needed
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
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

