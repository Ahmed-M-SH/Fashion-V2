package com.example.fashion.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fashion.R;

public class SplashActivity extends AppCompatActivity {
    public static int SPLASH_TIMER = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ProgressBar progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);

        // التحقق من وجود اتصال بالإنترنت
        if (isNetworkAvailable()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_TIMER);
        } else {
            // عرض رسالة للمستخدم بعدم وجود اتصال بالإنترنت
            // يمكنك استبدال "No internet connection" بالرسالة التي ترغب في عرضها للمستخدم
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    // دالة للتحقق من وجود اتصال بالإنترنت
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}