package com.example.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AttachmentListAdapter extends RecyclerView.Adapter<ItemAttachmentViewHolder>{
    ArrayList<PhotoFragment> photos = new ArrayList<>();
    MainActivity mainActivity;
    AttachmentListAdapter(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public ItemAttachmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_photo, parent, false);

        return new ItemAttachmentViewHolder(itemView,mainActivity);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAttachmentViewHolder holder, int position) {
        holder.setImageView(photos.get(position).getPhoto());

    }
    public void addPhoto(PhotoFragment photo){
        photos.add(photo);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }
}
