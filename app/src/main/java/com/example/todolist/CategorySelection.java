package com.example.todolist;

import android.app.Activity;

public class CategorySelection {
    static public int getPositionCategory(String category, Activity activity) {
        String[] categories = activity.getResources().getStringArray(R.array.category);
        for (int i = 0; i < categories.length; i++) {
            if (categories[i].equals(category))
                return i;
        }
        return 0;
    }

    static public int getPositionCategories(String category, Activity activity) {
        String[] categories = activity.getResources().getStringArray(R.array.categories);
        for (int i = 0; i < categories.length; i++) {
            if (categories[i].equals(category))
                return i;
        }
        return 0;
    }

    static public int getNotificationPosition(String notification, Activity activity) {
        String[] notifications = activity.getResources().getStringArray(R.array.notification);
        for (int i = 0; i < notifications.length; i++) {
            if (notifications[i].equals(notification))
                return i;
        }
        return 0;

    }
}

