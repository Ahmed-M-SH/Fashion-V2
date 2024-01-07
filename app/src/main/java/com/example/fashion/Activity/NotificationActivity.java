package com.example.fashion.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashion.Fragment.HomeFragment;
import com.example.fashion.Fragment.NotificationFragment;
import com.example.fashion.Helper.ManagmentNotifications;
import com.example.fashion.R;

public class NotificationActivity extends AppCompatActivity {
    private ManagmentNotifications managmentNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        managmentNotifications = new ManagmentNotifications(this);
        managmentNotifications.readAllNotifications();
        NotificationFragment fragment = new NotificationFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

    }
}