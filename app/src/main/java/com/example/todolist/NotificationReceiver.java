package com.example.todolist;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class NotificationReceiver extends BroadcastReceiver {
    private static final int NOTIFICATION_ID = 1;
    static MyListAdapter myListAdapter;
    static MainActivity mainActivity;

    static void setMyListAdapter(MyListAdapter myListAdapter) {
        NotificationReceiver.myListAdapter = myListAdapter;
    }

    public static void setMainActivity(MainActivity mainActivity) {
        NotificationReceiver.mainActivity = mainActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences = mainActivity.getSharedPreferences("com.example.todolist", Context.MODE_PRIVATE);
        String notification = sharedPreferences.getString("currentNotification", "Wyłącz");
        Log.d("NotificationReceiver", "onReceive: " + notification);

        myListAdapter.checkIsDoneZadania();
        myListAdapter.sortDoneTasks();
        if (notification.equals("Wyłącz")) {
            return;
        }

        // Create an intent to launch the activity when the notification is clicked
        Log.d("NotificationReceiver", "onReceive: " + intent.getStringExtra("Title"));
        String title = intent.getStringExtra("Title");
        String message = intent.getStringExtra("Message");
        Boolean status = intent.getBooleanExtra("Status", false);
        if (!status)
            return;
        int id = intent.getIntExtra("ID", 0);
        Intent notificationClickIntent = new Intent(context, MainActivity.class);
        notificationClickIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                notificationClickIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        // Build the notification

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channelId")
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Show the notification

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, builder.build());
    }
}
