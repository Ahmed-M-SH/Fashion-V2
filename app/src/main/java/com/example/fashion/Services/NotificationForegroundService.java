package com.example.fashion.Services;// NotificationForegroundService.java

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;

import com.example.fashion.Activity.NotificationActivity;
import com.example.fashion.Domain.NotificationDomain;
import com.example.fashion.Fragment.NotificationFragment;
import com.example.fashion.Helper.ManagmentNotifications;
import com.example.fashion.R;

import java.util.List;

public class NotificationForegroundService extends Service {

    private static final int NOTIFICATION_ID = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new NotificationTask(getApplicationContext()).execute();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private static class NotificationTask extends AsyncTask<Void, Void, Void> {

        private final Context context;

        public NotificationTask(Context context) {
            this.context = context.getApplicationContext();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ManagmentNotifications mNotifications = new ManagmentNotifications(context);
            List<NotificationDomain> notifications = mNotifications.getUnreadNotifications();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            boolean hasNewNotifications = hasNewNotifications(notifications);

            if (hasNewNotifications) {
                for (NotificationDomain notification : notifications) {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Fashion_Id_channel")
                            .setSmallIcon(R.drawable.baseline_notifications_none_24)
                            .setContentTitle(notification.getTitle())
                            .setContentText(notification.getText())
                            .setAutoCancel(true);

                    Intent intent = new Intent(context, NotificationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
                    builder.setContentIntent(pendingIntent);

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        NotificationChannel notificationChannel = notificationManager.getNotificationChannel("Fashion_Id_channel");
                        if (notificationChannel == null) {
                            notificationChannel = new NotificationChannel("Fashion_Id_channel", "Notification Channel", NotificationManager.IMPORTANCE_HIGH);
                            notificationChannel.setLightColor(Color.GREEN);
                            notificationChannel.enableVibration(true);
                            notificationManager.createNotificationChannel(notificationChannel);
                        }
                    }

                    int notificationId = generateUniqueId();
                    notificationManager.notify(notificationId, builder.build());
                }
            }
            return null;
        }

        private boolean hasNewNotifications(List<NotificationDomain> notifications) {
            return !notifications.isEmpty();
        }
    }

    private static int generateUniqueId() {
        long timestamp = System.currentTimeMillis();
        int random = (int) (Math.random() * 1000);
        return (int) (timestamp + random);
    }
}
