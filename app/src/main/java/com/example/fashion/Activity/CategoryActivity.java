package com.example.fashion.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.fashion.Fragment.CategorieFragment;
import com.example.fashion.Fragment.DetailsFragment;
import com.example.fashion.R;

public class CategoryActivity extends AppCompatActivity {

    private boolean have_children;
    private int categoryId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        have_children = getIntent().getBooleanExtra("have_children", false);
        categoryId = getIntent().getIntExtra("category", 0);
        if (!have_children){
            Intent intent = new Intent(this, ProductListActivity.class);
            intent.putExtra("category", categoryId);
            startActivity(intent);
            finish();
        }
        else {
//            CategorieFragment fragment = CategorieFragment.newInstance(categoryId);
            CategorieFragment fragment =new CategorieFragment();
//            fragment.category = categoryId;
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
    }
    }

}