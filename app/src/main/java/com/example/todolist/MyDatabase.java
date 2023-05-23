package com.example.todolist;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyDatabase {
    private static MyDatabase instance;
    private final FeedReaderDbHelper dbHelper;
    private static Activity mainActivity;
    private SQLiteDatabase db;


    MyDatabase(Activity mainActivity) {
        MyDatabase.mainActivity = mainActivity;
        dbHelper = new FeedReaderDbHelper(mainActivity);
        db = dbHelper.getWritableDatabase();

    }

    public static MyDatabase getInstance() {
        if (instance == null) {
            instance = new MyDatabase(mainActivity);
        }
        return instance;
    }

    public FeedReaderDbHelper getDbHelper() {
        return dbHelper;
    }

    void addZadanie(Zadanie zadanie) {
        try {

            db = dbHelper.getWritableDatabase();


            ContentValues values = new ContentValues();
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, zadanie.taskTitle);
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION, zadanie.taskDescription);
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DATE, zadanie.taskDate.getTime());
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TIME, zadanie.taskTime.getHours() * 3600000 + zadanie.taskTime.getMinutes() * 60000 + zadanie.taskTime.getSeconds() * 1000);
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ATTACHMENT, zadanie.taskAttachment);
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_STATUS, zadanie.taskDone);
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_NOTIFICATION, zadanie.taskNotification);
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_NOTIFICATION_ID, zadanie.notificationID);
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ATTACHMENT_PATH, zadanie.photoPath);
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CATEGORY, zadanie.taskCategory);


            db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        } catch (Exception | Error e) {
            db.close();
            return;
        }
        db.close();
    }

    //     public void deleteCity(String city){
//        db=dbHelper.getWritableDatabase();
//        db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, FeedReaderContract.FeedEntry.COLUMN_NAME_CITY + " = ?", new String[]{city});
//        db.close();
//    }
    public void updateTask(Zadanie zadanie) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, zadanie.taskTitle);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION, zadanie.taskDescription);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DATE, zadanie.taskDate.getTime());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TIME, zadanie.taskTime.getHours() * 3600000 + zadanie.taskTime.getMinutes() * 60000 + zadanie.taskTime.getSeconds() * 1000);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ATTACHMENT, zadanie.taskAttachment);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_STATUS, zadanie.taskDone);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_NOTIFICATION, zadanie.taskNotification);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_NOTIFICATION_ID, zadanie.notificationID);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ATTACHMENT_PATH, zadanie.photoPath);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CATEGORY, zadanie.taskCategory);

        db.update(FeedReaderContract.FeedEntry.TABLE_NAME, values, FeedReaderContract.FeedEntry.COLUMN_NAME_NOTIFICATION_ID + " = ?", new String[]{String.valueOf(zadanie.notificationID)});
        db.close();
    }

    //     public boolean isCityInDatabase(String city){
//        db=dbHelper.getReadableDatabase();
//        Cursor tmp=db.query(FeedReaderContract.FeedEntry.TABLE_NAME, null, FeedReaderContract.FeedEntry.COLUMN_NAME_CITY + " = ?", new String[]{city}, null, null, null);
//        boolean isCityInDatabase = tmp.getCount() > 0;
//        tmp.close();
//        db.close();
//        return isCityInDatabase;
//    }
    public void closeDatabase() {
        try {
            db.close();
            MyDatabase.instance = null;

        } catch (Exception ignored) {
        }
    }

    public ArrayList<Zadanie> getTasks() {
        db = dbHelper.getReadableDatabase();
        String[] projection = {
                FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION,
                FeedReaderContract.FeedEntry.COLUMN_NAME_DATE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TIME,
                FeedReaderContract.FeedEntry.COLUMN_NAME_ATTACHMENT,
                FeedReaderContract.FeedEntry.COLUMN_NAME_STATUS,
                FeedReaderContract.FeedEntry.COLUMN_NAME_NOTIFICATION,
                FeedReaderContract.FeedEntry.COLUMN_NAME_NOTIFICATION_ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_ATTACHMENT_PATH,
                FeedReaderContract.FeedEntry.COLUMN_NAME_CATEGORY


        };
        Cursor cursor = db.query(FeedReaderContract.FeedEntry.TABLE_NAME, projection, null, null, null, null, null);
        cursor.moveToFirst();
        ArrayList<Zadanie> tasks = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            Zadanie task = new Zadanie();

            task.taskTitle = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE));
            task.taskDescription = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION));
            task.taskDate = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_DATE)));
            Log.d("taskDate", String.valueOf(task.taskDate.getTime()));
            long time = cursor.getLong(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TIME));
            task.taskTime = new Time((int) (time / 3600000), (int) (time % 3600000 / 60000), (int) (time % 3600000 % 60000 / 1000));
//            task.taskTime = new Time(cursor.getLong(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TIME)));
            task.taskAttachment = cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_ATTACHMENT))==1;
            task.taskDone = cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_STATUS)) == 1;
            task.taskNotification = cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_NOTIFICATION)) == 1;
            task.notificationID = cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_NOTIFICATION_ID));
            task.photoPath = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_ATTACHMENT_PATH));
            task.taskCategory = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_CATEGORY));
            tasks.add(task);

            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return tasks;

    }

    public void checkIsDoneAndNotify() {
        db = dbHelper.getReadableDatabase();
        String[] projection = {
                FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION,
                FeedReaderContract.FeedEntry.COLUMN_NAME_DATE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TIME,
                FeedReaderContract.FeedEntry.COLUMN_NAME_ATTACHMENT,
                FeedReaderContract.FeedEntry.COLUMN_NAME_STATUS,
                FeedReaderContract.FeedEntry.COLUMN_NAME_NOTIFICATION,
                FeedReaderContract.FeedEntry.COLUMN_NAME_NOTIFICATION_ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_ATTACHMENT_PATH,
                FeedReaderContract.FeedEntry.COLUMN_NAME_CATEGORY
        };
        Cursor cursor = db.query(FeedReaderContract.FeedEntry.TABLE_NAME, projection, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int taskDate = (cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_DATE)));
            int taskTime = (cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TIME)));

            if (new Date().getTime() >= new Date(taskDate + taskTime).getTime()) {
                Zadanie tmp = new Zadanie();
                tmp.taskTitle = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE));
                tmp.taskDescription = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION));
                tmp.taskDate = new Date(taskDate);
                tmp.taskTime = new Time(taskTime);
                tmp.taskAttachment = cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_ATTACHMENT))==1;
                tmp.taskDone = cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_STATUS)) == 1;
                tmp.taskNotification = cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_NOTIFICATION)) == 1;

                tmp.notificationID = cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_NOTIFICATION_ID));
                tmp.photoPath = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_ATTACHMENT_PATH));
                tmp.taskCategory = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_CATEGORY));
                tmp.taskDone = true;
                updateTask(tmp);




            }


        }
//    public Map<String,String> getCity(String city){
//        db=dbHelper.getReadableDatabase();
//        String[] projection = {
//                FeedReaderContract.FeedEntry.COLUMN_NAME_CITY,
//                FeedReaderContract.FeedEntry.COLUMN_NAME_TEMP,
//                FeedReaderContract.FeedEntry.COLUMN_NAME_PRESSURE,
//                FeedReaderContract.FeedEntry.COLUMN_NAME_LON,
//                FeedReaderContract.FeedEntry.COLUMN_NAME_LAT,
//                FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION,
//                FeedReaderContract.FeedEntry.COLUMN_NAME_ACTUALTIME,
//                FeedReaderContract.FeedEntry.COLUMN_NAME_WINDSPEED,
//                FeedReaderContract.FeedEntry.COLUMN_NAME_WINDDEG,
//                FeedReaderContract.FeedEntry.COLUMN_NAME_HUMIDITY,
//                FeedReaderContract.FeedEntry.COLUMN_NAME_ICON,
//                FeedReaderContract.FeedEntry.COLUMN_NAME_JSONLIST
//        };
//        Cursor cursor = db.query(FeedReaderContract.FeedEntry.TABLE_NAME, projection, FeedReaderContract.FeedEntry.COLUMN_NAME_CITY + " = ?", new String[]{city}, null, null, null);
//        cursor.moveToFirst();
//        if (cursor.getCount() == 0) {
//            cursor.close();
//            db.close();
//            return null;
//        }
//        Map<String,String> cityData =new HashMap<>();
//        cityData.put("city",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_CITY)));
//        cityData.put("temp",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TEMP)));
//        cityData.put("pressure",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_PRESSURE)));
//        cityData.put("lon",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_LON)));
//        cityData.put("lat",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_LAT)));
//        cityData.put("description",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION)));
//        cityData.put("actualTime",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_ACTUALTIME)));
//        cityData.put("windSpeed",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_WINDSPEED)));
//        cityData.put("windDeg",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_WINDDEG)));
//        cityData.put("humidity",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_HUMIDITY)));
//        cityData.put("icon",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_ICON)));
//        cityData.put("jsonList",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_JSONLIST)));
//        cursor.close();
//        db.close();
//
//        return cityData;
//
//
//
//
//
//
//    }


    }
}
