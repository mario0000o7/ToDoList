package com.example.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class MyListAdapter extends RecyclerView.Adapter<ItemZadaniaViewHolder>{
    ArrayList<Zadanie> zadania=new ArrayList<>();
    MainActivity mainActivity;


    public MyListAdapter(MainActivity mainActivity){
        this.mainActivity=mainActivity;
    }
    @NonNull
    @Override
    public ItemZadaniaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_zdanie_item, parent, false);

        return new ItemZadaniaViewHolder(itemView,mainActivity);

    }
    public void addZadanie(Zadanie zadanie){
        zadania.add(zadanie);
        notifyItemInserted(zadania.size()-1);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemZadaniaViewHolder holder, int position) {

        holder.setTaskTitle(zadania.get(position).getTaskTitle());
        holder.setTaskDate(zadania.get(position).getTaskDate());
        holder.setTaskTime(zadania.get(position).getTaskTime());
        holder.setTaskCategory(zadania.get(position).getTaskCategory());
        holder.setTaskStatus(zadania.get(position).getTaskDone());
        holder.setAttachment(zadania.get(position).getTaskAttachment());
        holder.setNotification(zadania.get(position).getTaskNotification());
        holder.setMainActivity(mainActivity);


    }

    @Override
    public int getItemCount() {
        return zadania.size();
    }
}
