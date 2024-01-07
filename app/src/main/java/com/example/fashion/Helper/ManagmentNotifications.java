 package com.example.fashion.Helper;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.fashion.Domain.Notification;
import com.example.fashion.Fragment.NotificationFragment;
import com.example.fashion.R;

 public class ManagmentNotifications {
    private String channelId="Fashion_Id_channel";
    Context context;

    public void getNotifications(){

    }
    public void makeNotifications(Notification notification){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,this.channelId);
        builder.setSmallIcon(R.drawable.baseline_notifications_none_24)
        .setContentTitle(notification.getTitle())
        .setContentText(notification.getText())
        .setAutoCancel(true);
        Intent intent = new Intent(context, NotificationFragment.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_MUTABLE);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = notificationManager.getNotificationChannel(channelId);
            if (notificationChannel == null){
                notificationChannel = new NotificationChannel(channelId,"هناك بعض التنبيهات عليك تفحصها ",NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        notificationManager.notify(0,builder.build());

    }
}
