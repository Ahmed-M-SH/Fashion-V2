package com.example.navigationdrawer.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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

import com.example.navigationdrawer.Fragment.AboutFragment;
import com.example.navigationdrawer.Helper.DBHelper;
import com.example.navigationdrawer.Fragment.HomeFragment;
import com.example.navigationdrawer.R;
import com.example.navigationdrawer.Fragment.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
//    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.INTERNET},101);
        }



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

        DBHelper dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.getUser();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Profile Details", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                navName.setText("" + cursor.getString(0));
                navEmail.setText("" + cursor.getString(1));
                byte[] imageByte = cursor.getBlob(2);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                navImage.setImageBitmap(bitmap);
            }
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
//                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
//                    finish();
                    return true;
                case R.id.bottom_search:
                    startActivity(new Intent(getApplicationContext(), SearchActivity.class));
//                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_whatshot:
                    startActivity(new Intent(getApplicationContext(), WhatshotActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_shopping_cart:
                    startActivity(new Intent(getApplicationContext(), CartActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SettingsFragment()).commit();
                startActivity(new Intent(getApplicationContext(),UploadActivity.class));
                break;
            case R.id.nav_share:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new ShareFragment()).commit();

                  String APP_LINK = "https://your-app-link.com";
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