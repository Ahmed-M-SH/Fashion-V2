 package com.example.fashion.Helper;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.fashion.Domain.NotificationDomain;
import com.example.fashion.Domain.UserAuthentication;
import com.example.fashion.Fragment.NotificationFragment;
import com.example.fashion.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

 public class ManagmentNotifications {
    private String channelId="Fashion_Id_channel";
    Context context;
     private TinyDB tinyDB;
     private UserAuthentication userAuth;
     private boolean isAuthent;

     public ManagmentNotifications(Context context) {
         this.context = context;
         this.tinyDB = new TinyDB(context);
         this.isAuthent= tinyDB.getBoolean("isAuthent");
     }
     public List<NotificationDomain> getUnreadNotifications(){
        if (isAuthent) {
             userAuth = tinyDB.getObject("userAuth", UserAuthentication.class);
         final List<NotificationDomain>[] notificationsResult = new List[]{new ArrayList()};
         Call<List<NotificationDomain>> call = RetrofitClient.getInstance().getServerDetail().getUnreadNotifications(userAuth.getToken());
         call.enqueue(new Callback<List<NotificationDomain>>() {
             @Override
             public void onResponse(Call<List<NotificationDomain>> call, Response<List<NotificationDomain>> response) {
                 List<NotificationDomain> notifications = response.body();

                 notificationsResult[0] = notifications;
                 tinyDB.putListObjectNotification("unReadNotifications", notifications);
                 if (notifications.size() == 0) {
                     Log.i("Notification", "Notification Service is empty");
                 }
                 else {
                     makeNotifications(notifications);
                     tinyDB.putObject("notifications", notifications);
                 }
             }

             @Override
             public void onFailure(Call<List<NotificationDomain>> call, Throwable t) {

             }
         });
         return notificationsResult[0];
        }
         else {
            List<NotificationDomain> emptyDomain = new ArrayList<NotificationDomain>();
             return emptyDomain;
        }
    }
     public List<NotificationDomain> getAllNotifications(){
         if (isAuthent) {
             userAuth = tinyDB.getObject("userAuth", UserAuthentication.class);
             final List<NotificationDomain>[] notificationsResult = new List[]{new ArrayList()};
             Call<List<NotificationDomain>> call = RetrofitClient.getInstance().getServerDetail().getAllNotifications(userAuth.getToken());
             call.enqueue(new Callback<List<NotificationDomain>>() {
                 @Override
                 public void onResponse(Call<List<NotificationDomain>> call, Response<List<NotificationDomain>> response) {
                     List<NotificationDomain> notifications = response.body();

                     notificationsResult[0] = notifications;
                     tinyDB.putListObjectNotification("Notifications", notifications);
                     if (notifications.size() == 0) {
                         Log.i("Notification", "Notification Service is empty");
                     } else {
//                     makeNotifications(notifications);
                         tinyDB.putObject("notifications", notifications);
                     }
                 }

                 @Override
                 public void onFailure(Call<List<NotificationDomain>> call, Throwable t) {

                 }
             });
             return notificationsResult[0];
         }else {
             List<NotificationDomain> emptyDomain = new ArrayList<NotificationDomain>();
             return emptyDomain;
         }
     }
     public void makeNotifications(List<NotificationDomain> notifications) {
         NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

         for (NotificationDomain notification : notifications) {
             NotificationCompat.Builder builder = new NotificationCompat.Builder(context, this.channelId);
             builder.setSmallIcon(R.drawable.baseline_notifications_none_24)
                     .setContentTitle(notification.getTitle())
                     .setContentText(notification.getText())
                     .setAutoCancel(true);

             Intent intent = new Intent(context, NotificationFragment.class);
             intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

             PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE);
             builder.setContentIntent(pendingIntent);

             if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                 NotificationChannel notificationChannel = notificationManager.getNotificationChannel(channelId);
                 if (notificationChannel == null) {
                     notificationChannel = new NotificationChannel(channelId, "هناك بعض التنبيهات عليك تفحصها ", NotificationManager.IMPORTANCE_HIGH);
                     notificationChannel.setLightColor(Color.GREEN);
                     notificationChannel.enableVibration(true);
                     notificationManager.createNotificationChannel(notificationChannel);
                 }
             }
             int notificationId = generateUniqueId();
             notificationManager.notify(notificationId, builder.build());
         }
     }
     private int generateUniqueId() {
         // Combine current timestamp with a random number to create a unique ID
         long timestamp = System.currentTimeMillis();
         int random = new Random().nextInt(1000); // Adjust the range as needed
         return (int) (timestamp + random);
     }

     public void readAllNotifications() {
         if (isAuthent) {
             userAuth = tinyDB.getObject("userAuth", UserAuthentication.class);
             Call<List<NotificationDomain>> call = RetrofitClient.getInstance().getServerDetail().ReadAllNotifications(userAuth.getToken());
             call.enqueue(new Callback<List<NotificationDomain>>() {
                 @Override
                 public void onResponse(Call<List<NotificationDomain>> call, Response<List<NotificationDomain>> response) {
                     List<NotificationDomain> notifications = response.body();
                     tinyDB.remove("unReadNotifications");
                 }

                 @Override
                 public void onFailure(Call<List<NotificationDomain>> call, Throwable t) {

                 }
             });
         }
     }
}
