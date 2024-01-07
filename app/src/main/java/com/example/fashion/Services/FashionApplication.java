package com.example.fashion.Services;
// FashionApplication.java

import static java.security.AccessController.getContext;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import androidx.work.Configuration;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import java.util.concurrent.TimeUnit;
import com.example.fashion.Services.NotificationForegroundService;

public class FashionApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Check if WorkManager is not initialized before initializing it
        if (!isWorkManagerInitialized()) {
            // Initialize WorkManager using a OneTimeWorkRequest
            WorkManager.initialize(this, new Configuration.Builder().build());

            PeriodicWorkRequest periodicWorkRequest =
                    new PeriodicWorkRequest.Builder(NotificationWorker.class, 5, TimeUnit.SECONDS)
                            .build();

            WorkManager.getInstance(this).enqueue(periodicWorkRequest);

            // Start the NotificationForegroundService on a background thread
            new StartForegroundServiceTask().execute();
        }
    }

    // Check if WorkManager is initialized
    private boolean isWorkManagerInitialized() {
        try {
            WorkManager.getInstance(this);
            return true;
        } catch (IllegalStateException e) {
            return false;
        }
    }

    private static class StartForegroundServiceTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // For devices with API level 26 or higher
                startForegroundService(new Intent(getContext(), NotificationForegroundService.class));
            } else {
                // For devices with API level lower than 26
                startService(new Intent(getContext(), NotificationForegroundService.class));
            }
            return null;
        }
    }
}
