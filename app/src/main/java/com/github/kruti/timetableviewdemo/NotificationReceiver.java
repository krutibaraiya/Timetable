package com.github.kruti.timetableviewdemo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

import static android.content.Context.MODE_PRIVATE;

public class NotificationReceiver extends AppCompatActivity {

    SharedPreferences preferences = getSharedPreferences("startTime", MODE_PRIVATE);
    long StartTime = preferences.getLong("startTime", 0);

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Intent repeating_intent = new Intent(context, EditActivity.class);
            repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);


            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(android.R.drawable.arrow_up_float)
                    .setWhen(StartTime)
                    .setContentTitle("Notification Title")
                    .setContentText("You have a class after ten minutes")
                    .setAutoCancel(true);

            notificationManager.notify(100, builder.build());
        }
    };
}





