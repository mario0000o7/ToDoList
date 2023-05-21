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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;

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
        Log.d("MainActivityFragment", "onAttach: " + mainActivity);



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
        MainFragmentFragmentViewModel viewModel= new ViewModelProvider(mainActivity).get(MainFragmentFragmentViewModel.class);
        menuCategory.setSelection(CategorySelection.getPositionCategories(viewModel.getCurrentCategory(),mainActivity));
        Log.d("TAG", "onItemSelected: "+menuCategory.getSelectedItem().toString());
        Switch switchDone = view.findViewById(R.id.switchDone);
        switchDone.setChecked(viewModel.getSortDoneTasks());
        switchDone.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.setSortDoneTasks(isChecked);
            listAdapter.setSortDoneTasks(isChecked);
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView3,new Zadanie(listAdapter)).commit();
            }
        });

        menuCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                listAdapter.setCurrentCategory(menuCategory.getSelectedItem().toString());
                Log.d("TAG", "onItemSelected: "+menuCategory.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}