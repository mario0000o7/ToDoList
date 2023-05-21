package com.example.todolist;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class MainFragmentFragmentViewModel extends ViewModel {
    private ArrayList<Zadanie> zadania = new ArrayList<>();
    private String currentCategory = "Wszystkie";
    private boolean sortDoneTasks = false;

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

    public void setSortDoneTasks(boolean sortDoneTasks) {
        this.sortDoneTasks = sortDoneTasks;
    }
}
