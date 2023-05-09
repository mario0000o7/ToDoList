package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Time;
import java.util.Date;


public class MainActivityFragment extends Fragment {

    private MainActivity mainActivity;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public MainActivityFragment() {
        // Required empty public constructor
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_activity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MyListAdapter listAdapter = new MyListAdapter(mainActivity);
        listAdapter.addZadanie(new Zadanie("z","cos tam","Nauka", new Date(),new Time(12,12,120),true,false,false));
        listAdapter.addZadanie(new Zadanie("z","cos tam","Nauka", new Date(),new Time(12,12,12),false,true,false));
        listAdapter.addZadanie(new Zadanie("sadasdsadsadasdsa sadsadasdsadads","cos tam","Nauka", new Date(),new Time(12,12,12),false,false,true));
        RecyclerView taskList = view.findViewById(R.id.taskList);
        taskList.setAdapter(listAdapter);
        taskList.setLayoutManager(new LinearLayoutManager(mainActivity));
        taskList.setItemAnimator(null);
        taskList.setHasFixedSize(true);
        Spinner menuCategory = view.findViewById(R.id.menuCategoriesSpinner);
        Spinner menuNotification = view.findViewById(R.id.menuNotificationSpinner);
        ArrayAdapter<CharSequence> adapterCategories = ArrayAdapter.createFromResource(mainActivity, R.array.categories, android.R.layout.simple_spinner_item);
        adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapterNotification = ArrayAdapter.createFromResource(mainActivity, R.array.notification, android.R.layout.simple_spinner_item);
        menuCategory.setAdapter(adapterCategories);
        menuNotification.setAdapter(adapterNotification);
        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView3,new Zadanie()).commit();
            }
        });

    }
}