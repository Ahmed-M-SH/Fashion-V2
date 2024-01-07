import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;
import com.example.fashion.Domain.NotificationDomain;
import com.example.fashion.Fragment.NotificationFragment;
import com.example.fashion.Helper.ManagmentNotifications;
import com.example.fashion.R;
import java.util.ArrayList;
import java.util.List;

public class NotificationForegroundService extends Service {

    private static final int NOTIFICATION_ID = 1;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Perform background notification task

        ManagmentNotifications mNotifications = new ManagmentNotifications(getApplicationContext());

        new NotificationTask(getApplicationContext()).execute(mNotifications.getUnreadNotifications()); // Replace with your logic

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private static class NotificationTask extends AsyncTask<List<NotificationDomain>, Void, Void> {

        private final Context context;

        public NotificationTask(Context context) {
            this.context = context.getApplicationContext();
        }

        @Override
        protected Void doInBackground(List<NotificationDomain>... params) {
            List<NotificationDomain> notifications = params[0];

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            // Check if there are new notifications
            boolean hasNewNotifications = hasNewNotifications(notifications);

            if (hasNewNotifications) {
                // Show a notification
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Fashion_Id_channel")
                        .setSmallIcon(R.drawable.baseline_notifications_none_24)
                        .setContentTitle("New Notification")
                        .setContentText("You have new notifications")
                        .setAutoCancel(true);

                Intent intent = new Intent(context, NotificationFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE);
                builder.setContentIntent(pendingIntent);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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
            return null;
        }

        private boolean hasNewNotifications(List<NotificationDomain> notifications) {
            // Replace this with your logic to determine if there are new notifications
            // For example, compare with the last set of notifications stored locally
            // or check against a server response
            return !notifications.isEmpty();
        }
    }

    private static int generateUniqueId() {
        // Combine current timestamp with a random number to create a unique ID
        long timestamp = System.currentTimeMillis();
        int random = (int) (Math.random() * 1000); // Adjust the range as needed
        return (int) (timestamp + random);
    }
}
