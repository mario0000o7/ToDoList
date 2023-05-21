package com.example.todolist;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class MyListAdapter extends RecyclerView.Adapter<ItemZadaniaViewHolder>{
    ArrayList<Zadanie> zadania;
    ArrayList<Zadanie> zadaniaSort;
    MainActivity mainActivity;
    private boolean sortDoneTasks = false;
    private MainFragmentFragmentViewModel viewModel;
    private String currentCategory ="Wszystkie";


    public MyListAdapter(MainActivity mainActivity){
        this.mainActivity=mainActivity;
        viewModel= new ViewModelProvider(mainActivity).get(MainFragmentFragmentViewModel.class);
        zadania=viewModel.getZadania();
        currentCategory=viewModel.getCurrentCategory();
        sortListByCategory(currentCategory);
        sortDoneTasks=viewModel.getSortDoneTasks();
    }
    @NonNull
    @Override
    public ItemZadaniaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_zdanie_item, parent, false);

        return new ItemZadaniaViewHolder(itemView,mainActivity);

    }
    public void sortListByCategory(String category){
        currentCategory=category;
        zadaniaSort = new ArrayList<>();
        for(Zadanie zadanie:zadania){
            if(zadanie.getTaskCategory().equals(category)||category.equals("Wszystkie")){
                zadaniaSort.add(zadanie);
            }
        }
        notifyDataSetChanged();
    }

    public void sortDoneTasks(boolean sortDoneTasks){
        this.sortDoneTasks=sortDoneTasks;

        zadaniaSort = new ArrayList<>();
        for(Zadanie zadanie:zadania){
            if(zadanie.getTaskDone()==sortDoneTasks&&(zadanie.getTaskCategory().equals(currentCategory)||currentCategory.equals("Wszystkie"))){
                zadaniaSort.add(zadanie);
            }
        }
        notifyDataSetChanged();
    }
    boolean isTaskExist(Zadanie zadanie)
    {
        return zadania.contains(zadanie);
    }
    public void addZadanie(Zadanie zadanie){
        zadania.add(zadanie);
        Log.println(Log.INFO,"Zadania Size: ",zadania.size()+"");
//        notifyItemInserted(zadania.size()-1);
        sortListByCategory(currentCategory);
    }
    public Zadanie getZadanie(int position){
        return zadaniaSort.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemZadaniaViewHolder holder, int position) {


        holder.setTaskTitle(zadaniaSort.get(position).getTaskTitle());
        holder.setTaskDate(zadaniaSort.get(position).getTaskDate());
        holder.setTaskTime(zadaniaSort.get(position).getTaskTime());
        holder.setTaskCategory(zadaniaSort.get(position).getTaskCategory());
        holder.setTaskStatus(zadaniaSort.get(position).getTaskDone());
        holder.setAttachment(zadaniaSort.get(position).getTaskAttachment());
        holder.setNotification(zadaniaSort.get(position).getTaskNotification());
        holder.setMainActivity(mainActivity);
        holder.setPosition(position);
        holder.setMyListAdapter(this);




    }

    public void setSortDoneTasks(boolean sortDoneTasks) {
        this.sortDoneTasks = sortDoneTasks;
        viewModel.setSortDoneTasks(sortDoneTasks);
        sortDoneTasks(sortDoneTasks);
    }

    public void setCurrentCategory(String currentCategory) {
        this.currentCategory = currentCategory;
        viewModel.setCurrentCategory(currentCategory);

        sortListByCategory(currentCategory);

    }

    @Override
    public int getItemCount() {
        return zadaniaSort.size();
    }
}
