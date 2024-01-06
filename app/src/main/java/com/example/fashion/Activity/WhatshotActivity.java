package com.example.fashion.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.fashion.Fragment.WhatshotFragment;
import com.example.fashion.R;

public class WhatshotActivity extends AppCompatActivity {
    private CheckBox selectAllWish;
    private Button deleteButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatshot);

        // إنشاء كائن الفريجمنت وعرضه في واجهة المستخدم
        WhatshotFragment fragment = new WhatshotFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void selectAllItems(boolean isSelected) {
        // قم بتحديد جميع العناصر هنا بناءً على حالة الـ CheckBox
        // قم بتعديل الدالة وفقًا لاحتياجاتك
    }
}