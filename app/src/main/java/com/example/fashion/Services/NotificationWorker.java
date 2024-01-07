package com.example.fashion.Services;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Configuration;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.fashion.Helper.ManagmentNotifications;

public class NotificationWorker extends Worker {

    public NotificationWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        WorkManager.initialize(getApplicationContext(), new Configuration.Builder().build());
        return Result.success();
    }
}
