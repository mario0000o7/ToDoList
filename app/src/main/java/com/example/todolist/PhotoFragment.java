package com.example.todolist;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;


public class PhotoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    String photo="";
    public PhotoFragment() {
        // Required empty public constructor
    }



    // TODO: Rename and change types of parameters


    public PhotoFragment(String photo) {

        // Required empty public constructor
        this.photo=photo;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView = getView().findViewById(R.id.attachmentPhotoF);
        Log.d("photo",photo);
        File file = new File(photo);
        if(file.exists())
            imageView.setImageURI(Uri.fromFile(file));


        FloatingActionButton floatingActionButtonClose = getView().findViewById(R.id.floatingActionButtonClosePhoto);


        floatingActionButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(PhotoFragment.this).commit();
            }
        });
    }

}