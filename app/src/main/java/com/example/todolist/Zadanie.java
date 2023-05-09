package com.example.todolist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Zadanie#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Zadanie extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    String taskTitle="Brak tytułu";
    String taskDescription="Brak opisu";
    Date taskDate=new Date();
    Time taskTime=new Time(0,0,0);
    int taskPriority=0;
    boolean taskDone=false;
    boolean taskNotification=false;
    boolean taskAttachment=false;
    String taskCategory="Brak kategorii";
    ArrayList<File> taskFiles=new ArrayList<>();



    public Zadanie() {
        // Required empty public constructor
    }

    public Zadanie(String taskTitle, String taskDescription,String taskCategory ,Date taskDate, Time taskTime, boolean taskDone, boolean taskNotification,boolean taskAttachment) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskDate = taskDate;
        this.taskTime = taskTime;
        this.taskDone = taskDone;
        this.taskNotification = taskNotification;
        this.taskAttachment = taskAttachment;
        this.taskCategory = taskCategory;

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Zadanie.
     */
    // TODO: Rename and change types and number of parameters
    public static Zadanie newInstance(String param1, String param2) {
        Zadanie fragment = new Zadanie();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_zadanie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton fabCancel=view.findViewById(R.id.floatingActionButton2);
        fabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView3,new MainActivityFragment()).commit();
            }
        });

    }

    public Date getTaskDate() {
        return taskDate;
    }

    public int getTaskPriority() {
        return taskPriority;
    }

    public String getTaskCategory() {
        return taskCategory;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public Time getTaskTime() {
        return taskTime;
    }
    public boolean getTaskDone() {
        return taskDone;
    }
    public boolean getTaskNotification() {
        return taskNotification;
    }
    public boolean getTaskAttachment() {
        return taskAttachment;
    }
    public ArrayList<File> getTaskFiles() {
        return taskFiles;
    }
}