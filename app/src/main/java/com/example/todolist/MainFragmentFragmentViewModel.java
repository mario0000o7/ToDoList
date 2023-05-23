package com.example.todolist;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class MainFragmentFragmentViewModel extends ViewModel {
    private ArrayList<Zadanie> zadania = new ArrayList<>();
    private String currentCategory = "Wszystkie";
    private boolean sortDoneTasks = false;

    private String currentNotification="Wyłącz";
    public ArrayList<Zadanie> getZadania() {
        return zadania;
    }

    public String getCurrentCategory() {
        return currentCategory;
    }

    public void setCurrentCategory(String currentCategory) {
        this.currentCategory = currentCategory;
    }

    public boolean getSortDoneTasks() {
        return sortDoneTasks;
    }

    public void setCurrentNotification(String currentNotification) {
        this.currentNotification = currentNotification;
    }

    public String getCurrentNotification() {
        return currentNotification;
    }
    public static int notificationToTime(String notification){
        switch (notification){
            case "Wyłącz":
                return 0;
            case "5 min":
                return 5*60;
            case "10 min":
                return 10*60;
            case "15 min":
                return 15*60;
            case "30 min":
                return 30*60;
            case "1 godz":
                return 60*60;
            case "2 godz":
                return 120*60;
            case "3 godz":
                return 180*60;
            case "6 godz":
                return 360*60;
            case "12 godz":
                return 720*60;
            case "24 godz":
                return 1440*60;
            default:
                return 0;
        }
    }

    public void setSortDoneTasks(boolean sortDoneTasks) {
        this.sortDoneTasks = sortDoneTasks;
    }


}
