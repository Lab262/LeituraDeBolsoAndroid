package lab262.leituradebolso.Extensions;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import java.util.Calendar;

import lab262.leituradebolso.LaunchScreenActivity;
import lab262.leituradebolso.R;

/**
 * Created by luisresende on 11/10/16.
 */

public class NotificationsManager {

    public static void setReadingDaysNotifications(Context context, long hourNotification){
        //Set Notification
        setNewReadingDayNotification(context,hourNotification);
    }

    private static void setNewReadingDayNotification(Context context, long dateNotification) {

        //TODO: Mudar os textos das notificacoes
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle(context.getString(R.string.title_notification));
        builder.setContentText(context.getString(R.string.content_notification));
        builder.setSmallIcon(R.drawable.ic_reading_day);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);
        builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
        builder.setLights(0xFFFFA500, 800, 1000);
        builder.setAutoCancel(true);

        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, builder.build());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, 0);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, dateNotification, AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public static void cancelAllNotifications(Context context){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, notificationIntent, 0);
        alarmManager.cancel(sender);
    }
}
