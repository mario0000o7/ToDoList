package com.example.todolist;

import android.view.View;
import android.widget.CheckBox;
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

    public void setTaskTime(Time taskTime) {
        this.taskTime.setText(taskTime.toString());
    }
    public void setTaskCategory(String taskCategory){
        this.taskCategory.setText(taskCategory);
    }


}
