package com.example.todolist;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Zadanie#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Zadanie extends Fragment {
    private static final int REQUEST_CODE_PICK_IMAGE = 1;
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
    String photoPath="";
    Bitmap photoBitmap=null;
    int notificationID=UUID.randomUUID().hashCode();


    public Zadanie() {
        // Required empty public constructor
    }
    public Zadanie(MyListAdapter adapter){
        this.adapter=adapter;
    }


    public void checkIsDone(){
        if(taskDate.getTime()+taskTime.getHours()*3600000+taskTime.getMinutes()*60000+taskTime.getSeconds()*1000<new Date().getTime()) {
//            Log.d("Zadanie", "checkIsDone: "+taskTitle);
//            Log.d("Zadanie", "checkIsDone: "+taskTime.getHours()+" "+taskTime.getMinutes()+" "+taskTime.getSeconds());
//            Log.d("Zadanie", "checkIsDone: "+taskDate.getTime()+" "+taskTime.getTime()+" "+new Date().getTime());
            taskDone = true;
            MyDatabase db = MyDatabase.getInstance();
            db.updateTask(this);

        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    public void sendNotification(int timeBeforeDone){
        if(taskDone||mainActivity==null)
            return;
        Log.d("Zadanie", "sendNotification: "+taskTitle+mainActivity.toString());
        Intent notificationIntent = new Intent(mainActivity, NotificationReceiver.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        notificationIntent.putExtra("Title", taskTitle);
        if(taskNotification)
            notificationIntent.putExtra("Status", true);
        else
            notificationIntent.putExtra("Status", false);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(taskDate);
        Log.d("Calendar", "sendNotification: "+calendar.getTime().toString());

// Get the time zone of the device
        TimeZone deviceTimeZone = TimeZone.getDefault();

// Set the time zone of the calendar to the device's time zone
        calendar.setTimeZone(deviceTimeZone);

// Get the date in the local time zone
        Date localDate = calendar.getTime();
        Log.d("Zadanie", "sendNotification: "+localDate.toString());
        notificationIntent.putExtra("Message", "Zadanie z kategorii: "+taskCategory+" o terminie: "+localDate+" "+taskTime);
        notificationIntent.putExtra("ID", notificationID);
        PendingIntent pendingIntentNotification = PendingIntent.getBroadcast(
                mainActivity,
                notificationID,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        AlarmManager alarmManager = (AlarmManager) mainActivity.getSystemService(Context.ALARM_SERVICE);
        long time = taskDate.getTime() + taskTime.getHours()*3600000+taskTime.getMinutes()*60000+taskTime.getSeconds()*1000-timeBeforeDone*1000;
        Log.d("taskTime", "sendNotification: "+taskTime.getHours()+" "+taskTime.getMinutes()+" "+taskTime.getSeconds());
        if(taskTime.getHours()*3600000+taskTime.getMinutes()*60000+taskTime.getSeconds()*1000==0) {
            time = taskDate.getTime() +1000;
            Log.d("Zadanie", "sendNotification==0: "+time);
        }
        if(time<new Date().getTime())
            time=taskDate.getTime() + taskTime.getHours()*3600000+taskTime.getMinutes()*60000+taskTime.getSeconds()*1000;
        if(taskTime.getHours()*3600000+taskTime.getMinutes()*60000+taskTime.getSeconds()*1000-timeBeforeDone*1000==0) {
            time = taskDate.getTime() +1000;
            Log.d("Zadanie", "sendNotification==0: "+time);
        }

        Log.d("Zadanie","taskDate: "+new Date(taskDate.getTime()+taskTime.getHours()*3600000+taskTime.getMinutes()*60000+taskTime.getSeconds()*1000));
        Log.d("Zadanie","time: "+new Date(time).toString());
        Log.d("Zadanie", "sendNotification: "+time);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntentNotification);

    }

    public void cancelNotification(){
        if(taskDone||!taskNotification||mainActivity==null)
            return;
        PendingIntent pendingIntentNotification = PendingIntent.getBroadcast(
                mainActivity,
                notificationID,
                new Intent(mainActivity, NotificationReceiver.class),
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        AlarmManager alarmManager = (AlarmManager) mainActivity.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntentNotification);
    }
    public void changeNotification(int timeBeforeDone){
        cancelNotification();
        sendNotification(timeBeforeDone);
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
    public Zadanie(String taskTitle, String taskDescription, String taskCategory , Date taskDate, Time taskTime, boolean taskDone, boolean taskNotification, boolean taskAttachment,MyListAdapter adapter) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskDate = taskDate;
        this.taskTime = taskTime;
        this.taskDone = taskDone;
        this.taskNotification = taskNotification;
        this.taskAttachment = taskAttachment;
        this.taskCategory = taskCategory;
        this.adapter=adapter;

    }
    public Zadanie(Zadanie zadanie)
    {
        this.taskTitle = zadanie.taskTitle;
        this.taskDescription = zadanie.taskDescription;
        this.taskDate = zadanie.taskDate;
        this.taskTime = zadanie.taskTime;
        this.taskDone = zadanie.taskDone;
        this.taskNotification = zadanie.taskNotification;
        this.taskAttachment = zadanie.taskAttachment;
        this.taskCategory = zadanie.taskCategory;
        this.taskFiles=zadanie.taskFiles;
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
        EditText taskTitleText= view.findViewById(R.id.taskTitleText);
        EditText taskDescriptionText= view.findViewById(R.id.taskDescriptionText);
        EditText editTextTime=view.findViewById(R.id.editTextTime);
        TextView taskDateView=view.findViewById(R.id.taskDate);
        Switch notificationSwitch=view.findViewById(R.id.notificationSwitch);
        TextView doneStatus=view.findViewById(R.id.doneStatus);
        Spinner categorySpinner=view.findViewById(R.id.categorySpinner);
        taskTitleText.setText(taskTitle);
        taskDescriptionText.setText(taskDescription);
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        taskDateView.setText(formatter.format(taskDate));
        notificationSwitch.setChecked(taskNotification);
        doneStatus.setText(taskDone?"Zakończony":"Niezakończony");
        if(taskDone) {
            doneStatus.setTextColor(Color.GREEN);
            editTextTime.setEnabled(false);
            notificationSwitch.setEnabled(false);
        }
        else {
            doneStatus.setTextColor(Color.RED);

        }

        editTextTime.setText(taskTime.toString());

        categorySpinner.setSelection(CategorySelection.getPositionCategory(taskCategory,mainActivity));
        Log.println(Log.INFO,"Zadanie",photoPath);
        if(!Objects.equals(photoPath, ""))
        {
            File imageFile=new File(photoPath);
            if(imageFile.exists()) {
                ImageView attachmentPhoto = getView().findViewById(R.id.attachmentPhotoF);
                attachmentPhoto.setImageURI(Uri.fromFile(imageFile));
                attachmentPhoto.setVisibility(View.VISIBLE);
            }
            else{
                photoPath="";
                ImageView attachmentPhoto = getView().findViewById(R.id.attachmentPhotoF);
                attachmentPhoto.setVisibility(View.INVISIBLE);
                taskAttachment=false;
            }

        }



        Zadanie thisZadanie=this;

        fabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.sortDoneTasks();

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView3,new MainActivityFragment()).commit();
//                if(!adapter.isTaskExist(thisZadanie)) {
//                    File imageFile=new File(photoPath);
//                    if(imageFile.exists())
//                        imageFile.delete();
//                }


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
                if(taskTitleText.getText().toString().equals(""))
                {
                    Toast.makeText(mainActivity,"Wprowadź tytuł zadania",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(taskDescriptionText.getText().toString().equals(""))
                {
                    Toast.makeText(mainActivity,"Wprowadź opis zadania",Toast.LENGTH_SHORT).show();
                    return;
                }
                taskTitle=taskTitleText.getText().toString();
                taskDescription=taskDescriptionText.getText().toString();
                if(editTextTime.getText().toString().equals(""))
                    taskTime=new Time(0,0,1);
                else {
                    try {
                        taskTime = new Time(Integer.parseInt(editTextTime.getText().toString().split(":")[0]), Integer.parseInt(editTextTime.getText().toString().split(":")[1]), Integer.parseInt(editTextTime.getText().toString().split(":")[2]));

                    }
                    catch (Exception e)
                    {
                        Toast.makeText(mainActivity,"Wprowadź poprawny format czasu",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if(editTextTime.getText().toString().equals("00:00:00"))
                    taskTime=new Time(0,0,1);
                taskDone=doneStatus.getText().toString().equals("Zakończony");
                taskNotification=notificationSwitch.isChecked();
                taskCategory=categorySpinner.getSelectedItem().toString();
                File internalDir = getActivity().getFilesDir();
                File outputFile=new File(internalDir, UUID.randomUUID().toString()+".jpg");
                FileOutputStream out = null;
                if(photoBitmap!=null)
                    try {
                        out = new FileOutputStream(outputFile);
                        photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                        out.flush();
                        out.close();
                        photoPath=outputFile.getAbsolutePath();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                Log.d("Zadanie adapter",adapter.toString());

                if(!adapter.isTaskExist(thisZadanie)) {
                    taskDate=new Date();
                    Log.d("Zadanie",mainActivity.toString());
                    adapter.addZadanie(thisZadanie);
                }
                else
                {
                    MyDatabase db=MyDatabase.getInstance();
                    db.updateTask(thisZadanie);
                    adapter.sortDoneTasks();
                }
                sendNotification(MainFragmentFragmentViewModel.notificationToTime(adapter.getCurrentNotification()));

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView3,new MainActivityFragment()).commit();
            }
        });
        ImageView attachmentPhoto=view.findViewById(R.id.attachmentPhotoF);
        attachmentPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!photoPath.equals(""))
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainerView3,new PhotoFragment(photoPath)).commit();
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

    public void display(){
        Log.println(Log.INFO,"Zadanie",taskTitle);
        Log.println(Log.INFO,"Zadanie",taskDescription);
        Log.println(Log.INFO,"Zadanie",taskTime.toString());
        Log.println(Log.INFO,"Zadanie",taskDate.toString());
        Log.println(Log.INFO,"Zadanie",taskNotification?"true":"false");
        Log.println(Log.INFO,"Zadanie",taskDone?"true":"false");
        Log.println(Log.INFO,"Zadanie",taskCategory);
        Log.println(Log.INFO,"Zadanie",photoPath);
        Log.println(Log.INFO,"Zadanie",taskAttachment?"true":"false");
        Log.println(Log.INFO,"Zadanie",taskFiles.toString());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);




        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            File photoFile = new File(photoPath);
            if(photoFile.exists())
                photoFile.delete();
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            photoBitmap = BitmapFactory.decodeFile(picturePath);


            try {

                ImageView attachmentPhoto=getView().findViewById(R.id.attachmentPhotoF);
                attachmentPhoto.setImageBitmap(photoBitmap);
                attachmentPhoto.setVisibility(View.VISIBLE);
                taskAttachment=true;
            } catch (Exception e) {
                e.printStackTrace();
            }






        }
    }
}