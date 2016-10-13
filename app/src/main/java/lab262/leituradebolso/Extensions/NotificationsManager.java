package lab262.leituradebolso.Extensions;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import java.util.Calendar;

import lab262.leituradebolso.LaunchScreenActivity;
import lab262.leituradebolso.R;

/**
 * Created by luisresende on 11/10/16.
 */

public class NotificationsManager {

    private static int NUMBER_NOTIFICATIONS = 3;

    public static void setReadingDaysNotifications(Context context, long hourNotification){
        for (int i=0; i<NUMBER_NOTIFICATIONS; i++){
            //Add a day in date
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(hourNotification);
            calendar.add(Calendar.DATE, 1);
            hourNotification = calendar.getTime().getTime();

            //Set Notification
            setNewReadingDayNotification(context,hourNotification);
        }
    }

    private static void setNewReadingDayNotification(Context context, long dateNotification) {
        NotificationCompat.Builder builderNotification =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_reading_day)
                        .setContentTitle(context.getString(R.string.title_notification))
                        .setContentText(context.getString(R.string.content_notification));
        builderNotification.setWhen(dateNotification);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, LaunchScreenActivity.class);

        // The stack builder object will contain an artificial back stack for the started Activity.
        // This ensures that navigating backward from the Activity leads out of your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(LaunchScreenActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        builderNotification.setContentIntent(resultPendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builderNotification.build());
    }

    public static void cancelAllNotifications(Context context){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}
