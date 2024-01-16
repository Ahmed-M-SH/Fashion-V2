package com.example.fashion.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.example.fashion.Domain.UserProfile;
import com.example.fashion.Fragment.AboutFragment;
import com.example.fashion.Fragment.OrderListFragment;
import com.example.fashion.Helper.DBHelper;
import com.example.fashion.Fragment.HomeFragment;
import com.example.fashion.Helper.ServerDetail;
import com.example.fashion.Helper.TinyDB;
import com.example.fashion.R;
import com.example.fashion.Fragment.SettingsFragment;
import com.example.fashion.Services.FashionApplication;
import com.example.fashion.Services.NotificationCheckService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private boolean isAuthent;
    private TinyDB tinyDB;
//    private FloatingActionButton fab;

//    private static final long DELAY_MILLIS = 30 * 1000; // 1 minute
//
//    private final Handler handler = new Handler(Looper.getMainLooper());
//    private final Runnable periodicServiceStarter = new Runnable() {
//        @Override
//        public void run() {
//            startForegroundService();
//            handler.postDelayed(this, DELAY_MILLIS);
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tinyDB = new TinyDB(this);
        isAuthent = tinyDB.getBoolean("isAuthent");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 101);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }

        }


//        Intent serviceIntent = new Intent(this, NotificationCheckService.class);
//        startService(serviceIntent);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
//        fab = findViewById(R.id.fab);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        View header = navigationView.getHeaderView(0);
        ImageView navImage = header.findViewById(R.id.navImage);
        TextView navName = header.findViewById(R.id.navName);
        TextView navEmail = header.findViewById(R.id.navEmail);

        navImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UploadActivity.class);
//                startActivity(intent);
            }
        });

//        DBHelper dbHelper = new DBHelper(this);
//        Cursor cursor = dbHelper.getUser();
//
//        if (cursor.getCount() == 0) {
//            Toast.makeText(this, "No Profile Details", Toast.LENGTH_SHORT).show();
//        } else {
//            while (cursor.moveToNext()) {
//                navName.setText("" + cursor.getString(0));
//                navEmail.setText("" + cursor.getString(1));
//                byte[] imageByte = cursor.getBlob(2);
//                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
//                navImage.setImageBitmap(bitmap);
//            }
//        }
        if (isAuthent) {
            UserProfile profile = tinyDB.getObject("profile", UserProfile.class);

            navName.setText("" + profile.getName());
            navEmail.setText("" + profile.getEmail());
//                byte[] imageByte = cursor.getBlob(2);
//                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
//                navImage.setImageBitmap(bitmap);
            if (!profile.getImage().isEmpty())
                Glide.with(getApplicationContext()).load(profile.getImage()).into(navImage);

        }


//        fab.setOnClickListenner(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, UploadActivity.class);
//                startActivity(intent);
//            }
//        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new HomeFragment()).commit();
                    return true;
                case R.id.bottom_search:
                    startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                    finish();
                    return true;
                case R.id.bottom_whatshot:
                    startActivity(new Intent(getApplicationContext(), WhatshotActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                    finish();
                    return true;
                case R.id.bottom_shopping_cart:
                    startActivity(new Intent(getApplicationContext(), CartActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                    finish();
                    return true;
            }
            return false;
        });

        navName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UploadActivity.class);
//                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                break;
            case R.id.nav_settings:
                if (isAuthent){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SettingsFragment()).commit();
//                startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
                break;
                }else{
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    break;

                }
            case R.id.nav_share:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new ShareFragment()).commit();

                String APP_LINK = ServerDetail.endpoint;
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this app!");
                shareIntent.putExtra(Intent.EXTRA_TEXT, APP_LINK);
                startActivity(Intent.createChooser(shareIntent, "Share via"));

                break;
            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AboutFragment()).commit();
                break;
            case R.id.nav_order:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new OrderListFragment()).commit();
                break;
            case R.id.nav_logout:
                Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_login:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}