package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.sql.Time;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyListAdapter listAdapter = new MyListAdapter(this);
        listAdapter.addZadanie(new Zadanie("z","cos tam","Nauka", new Date(),new Time(12,12,120),true,false,false));
        listAdapter.addZadanie(new Zadanie("z","cos tam","Nauka", new Date(),new Time(12,12,12),false,true,false));
        listAdapter.addZadanie(new Zadanie("sadasdsadsadasdsa sadsadasdsadads","cos tam","Nauka", new Date(),new Time(12,12,12),false,false,true));
        RecyclerView taskList = findViewById(R.id.taskList);
        taskList.setAdapter(listAdapter);
        taskList.setLayoutManager(new LinearLayoutManager(this));
        taskList.setItemAnimator(null);
        taskList.setHasFixedSize(true);




    }
}