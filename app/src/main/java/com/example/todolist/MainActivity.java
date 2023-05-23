package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.sql.Time;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new MyDatabase(this);
        setContentView(R.layout.activity_main);
        NotificationChannel channel = new NotificationChannel("channelId", "TimeNotification", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);






    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        MyDatabase db = MyDatabase.getInstance();
        db.checkIsDoneAndNotify();

    }
}