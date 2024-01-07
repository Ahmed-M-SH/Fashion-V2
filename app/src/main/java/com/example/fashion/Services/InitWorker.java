package com.example.fashion.Services;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Configuration;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class InitWorker extends Worker {

    public InitWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Initialize WorkManager
        WorkManager.initialize(getApplicationContext(), new Configuration.Builder().build());

        return Result.success();
    }
}
