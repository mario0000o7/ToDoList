package com.example.todolist;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.sql.Time;

public class ItemZadaniaViewHolder extends RecyclerView.ViewHolder {


    private TextView taskTitle;
    private TextView taskTime;
    private TextView taskDate;
    private TextView taskCategory;
    private CheckBox taskStatus;
    private CheckBox attachment;
    private CheckBox notification;
    private MainActivity mainActivity;
    private MyListAdapter myListAdapter;
    private int position;
    public ItemZadaniaViewHolder(@NonNull View itemView, MainActivity mainActivity) {
        super(itemView);
        taskTitle = itemView.findViewById(R.id.taskTitle);
        taskTime = itemView.findViewById(R.id.taskTime);
        taskDate = itemView.findViewById(R.id.taskDateTitle);
        taskStatus = itemView.findViewById(R.id.taskStatusCheckBox);
        attachment = itemView.findViewById(R.id.taskAttachmentCheckBox);
        notification = itemView.findViewById(R.id.taskNotificationCheckBox);
        taskCategory = itemView.findViewById(R.id.taskCategory);








    }

    public void setMyListAdapter(MyListAdapter myListAdapter) {
        Log.d("SetMyListAdapter","SetMyListAdapter");
        this.myListAdapter = myListAdapter;
        FrameLayout mainFrame = itemView.findViewById(R.id.mainFrame);
        mainFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView3,myListAdapter.getZadanie(position)).commit();

            }
        });
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setMainActivity(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
    public void setTaskTitle(String taskTitle) {
        this.taskTitle.setText(taskTitle);
    }

    public void setAttachment(boolean attachment) {
        this.attachment.setChecked(attachment);
    }

    public void setNotification(boolean notification) {
        this.notification.setChecked(notification);
    }

    public void setTaskDate(Date taskDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        this.taskDate.setText(formatter.format(taskDate));
    }

    public void setTaskStatus(boolean taskStatus) {
        this.taskStatus.setChecked(taskStatus);
    }

    public void setTaskTime(Date taskTime, Date taskDate) {
        Log.d("SetTaskTime",taskTime.toString());

        this.taskTime.setText(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(taskTime));
    }
    public void setTaskCategory(String taskCategory){
        this.taskCategory.setText(taskCategory);
    }


}
