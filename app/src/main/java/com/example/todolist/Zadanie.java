package com.example.todolist;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
    private static final int REQUEST_CODE_PICK_IMAGE = 1;
    private static final int REQUEST_CODE_SAVE_IMAGE = 2;
    private static final int REQUEST_CODE_PICK_FILE = 1;


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
    private MainActivity mainActivity;
    MyListAdapter adapter;



    public Zadanie() {
        // Required empty public constructor
    }
    public Zadanie(MyListAdapter adapter){
        this.adapter=adapter;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }

    public Zadanie(String taskTitle, String taskDescription, String taskCategory , Date taskDate, Time taskTime, boolean taskDone, boolean taskNotification, boolean taskAttachment) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskDate = taskDate;
        this.taskTime = taskTime;
        this.taskDone = taskDone;
        this.taskNotification = taskNotification;
        this.taskAttachment = taskAttachment;
        this.taskCategory = taskCategory;

    }
    private void pickFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*"); // Set MIME type to allow any file type
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE_PICK_FILE);
    }
    private void pickImageFromLibrary() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
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
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        Log.println(Log.INFO,"Zadanie","onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.println(Log.INFO,"Zadanie","onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.println(Log.INFO,"Zadanie","onCreateView");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_zadanie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.println(Log.INFO,"Zadanie","onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton fabCancel=view.findViewById(R.id.floatingActionButtonCancel);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mainActivity);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        fabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView3,new MainActivityFragment()).commit();
            }
        });
        FloatingActionButton fabUpload = view.findViewById(R.id.floatingActionButtonUpload);
        fabUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);

            }
        });
        FloatingActionButton fabSave = view.findViewById(R.id.floatingActionButtonSave);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addZadanie(new Zadanie(taskTitle,taskDescription,taskCategory,taskDate,taskTime,taskDone,taskNotification,taskAttachment));
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            FragmentContainerView attachmentFragmentContainerView = getView().findViewById(R.id.attachmentContainerView);
            attachmentFragmentContainerView.setVisibility(View.VISIBLE);
            PhotoFragment ph=new PhotoFragment(bitmap);

            getActivity().getSupportFragmentManager().beginTransaction().replace(attachmentFragmentContainerView.getId(), ph).commit();


        }
    }
}