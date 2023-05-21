package com.example.todolist;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemAttachmentViewHolder extends RecyclerView.ViewHolder{
    MainActivity mainActivity;
    ImageView imageView;

    public ItemAttachmentViewHolder(@NonNull View itemView, MainActivity mainActivity) {
        super(itemView);
        imageView = itemView.findViewById(R.id.attachmentPhotoF);
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setImageView(Bitmap imageView) {
        this.imageView.setImageBitmap(imageView);
    }

}
