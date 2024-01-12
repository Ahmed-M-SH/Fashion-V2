package com.example.fashion.Services;
// FashionApplication.java

import static java.security.AccessController.getContext;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import com.example.fashion.Helper.TinyDB;


public class FashionApplication extends Application {

    private static boolean isAuthent;
    private static final long DELAY_MILLIS = 60 * 100; // 1 minute

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable periodicServiceStarter = new Runnable() {
        @Override
        public void run() {
            startForegroundService();
            handler.postDelayed(this, DELAY_MILLIS);
        }
    };
    @Override
    public void onCreate() {
        super.onCreate();

        // Check if WorkManager is not initialized before initializing it
//        if (!isWorkManagerInitialized()) {
//            // Initialize WorkManager using a OneTimeWorkRequest
//            WorkManager.initialize(this, new Configuration.Builder().build());
//
//            PeriodicWorkRequest periodicWorkRequest =
//                    new PeriodicWorkRequest.Builder(NotificationWorker.class, 5, TimeUnit.SECONDS)
//                            .build();
//
//            WorkManager.getInstance(this).enqueue(periodicWorkRequest);
//
//            // Start the NotificationForegroundService on a background thread
//            new StartForegroundServiceTask().execute();
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            // For devices with API level 26 or higher
//            startForegroundService(new Intent(this, NotificationForegroundService.class));
//        } else {
//            // For devices with API level lower than 26
//            startService(new Intent(this, NotificationForegroundService.class));
//        }
//        startForegroundServiceWithDelay();
        TinyDB tinyDB = new TinyDB(this);
        FashionApplication.setIsAuthent(tinyDB.getBoolean("isAuthent"));

    }

    private void startForegroundServiceWithDelay() {
        handler.postDelayed(periodicServiceStarter, DELAY_MILLIS);
    }

    private void startForegroundService() {
        Intent serviceIntent = new Intent(this, NotificationForegroundService.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent);
        } else {
            startService(serviceIntent);
        }
    }


    public static boolean isIsAuthent() {
        return isAuthent;
    }

    public static void setIsAuthent(boolean isAuthent) {
        FashionApplication.isAuthent = isAuthent;
    }
}
