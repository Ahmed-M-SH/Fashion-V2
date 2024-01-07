package com.example.fashion.Services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import androidx.annotation.Nullable;

import com.example.fashion.Helper.ManagmentNotifications;


public class NotificationCheckService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Perform notification check here
        ManagmentNotifications notificationManager = new ManagmentNotifications(this);
        notificationManager.getUnreadNotifications();

        // Schedule the service to run periodically using an AlarmManager
        scheduleService();

        return START_STICKY;
    }

    private void scheduleService() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationCheckService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Set the alarm to run every X minutes (adjust as needed)
        long intervalMillis =  1 * 60 * 1000;
        long triggerTime = SystemClock.elapsedRealtime() + intervalMillis;

        // SetInexactRepeating to save battery
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, intervalMillis, pendingIntent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
