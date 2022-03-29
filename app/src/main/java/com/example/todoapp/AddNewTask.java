package com.example.todoapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.todoapp.Model.ToDoModel;
import com.example.todoapp.Utils.DatabaseHandler;
import com.example.todoapp.Utils.DbBitmapUtility;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;

public class AddNewTask extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";

    private EditText newTaskText;
    private Button newTaskSaveButton, newTaskImageButton, newTaskExitButton;
    private DatabaseHandler db;
    private Bitmap captureImage;
    private ImageView imageView;
    private TextView textViewDate;

    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    public static AddNewTask newInstance(){
        return new AddNewTask();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.new_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        newTaskText = getView().findViewById(R.id.newTaskText);
        newTaskSaveButton = getView().findViewById(R.id.newTaskButton);
        newTaskImageButton = getView().findViewById(R.id.cameraButton);
        newTaskExitButton = getView().findViewById(R.id.exitButton);
        imageView = getView().findViewById(R.id.imageView);
        textViewDate = getView().findViewById(R.id.textViewDate);

        captureImage = null;

        initDatePicker();
        dateButton = getView().findViewById(R.id.datePickerButton);

        db = new DatabaseHandler(getActivity());
        db.openDatabase();

        newTaskSaveButton.setTextColor(Color.BLACK);
        newTaskImageButton.setTextColor(Color.BLACK);
        newTaskExitButton.setTextColor(Color.BLACK);

        boolean isUpdate = false;
        final Bundle bundle = getArguments();
        if(bundle != null){
            isUpdate = true;
            String task = bundle.getString("task");
            newTaskText.setText(task);
            String date = bundle.getString("date");
            textViewDate.setText(date);
            byte[] imageReceive = bundle.getByteArray("image");
            Bitmap imageTaskReceive = DbBitmapUtility.getImage(imageReceive);
            if (imageTaskReceive != null){
                Log.i(TAG, "check imageTaskReceive" + imageTaskReceive);
                captureImage = imageTaskReceive;
                imageView.setImageBitmap(imageTaskReceive);
                newTaskImageButton.setText("Change Image");
            } else {
                newTaskImageButton.setText("Add Image");
            }

            newTaskSaveButton.setText("Update");

            newTaskSaveButton.setEnabled(true);
            newTaskSaveButton.setTextColor(Color.BLACK);
        } else {
            newTaskSaveButton.setEnabled(false);
            newTaskSaveButton.setTextColor(Color.GRAY);
        }


        newTaskText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals("")){
                    newTaskSaveButton.setEnabled(false);
                    newTaskSaveButton.setTextColor(Color.GRAY);
                } else {
                    newTaskSaveButton.setEnabled(true);
                    newTaskSaveButton.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        boolean finalIsUpdate = isUpdate;
        newTaskSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = newTaskText.getText().toString();
                String date = textViewDate.getText().toString();
                byte[] image = new byte[0];

                if (captureImage != null){
                    image = DbBitmapUtility.getBytes(captureImage);
                }

                if(finalIsUpdate){
                    db.updateTask(bundle.getInt("id"), text, image, date);
                }
                else {
                    ToDoModel task = new ToDoModel();
                    Log.d(TAG, "text: " + text);
                    task.setTask(text);
                    task.setStatus(0);
                    task.setImage(image);
                    task.setDate(date);
                    Log.d(TAG, "Task: " + task.getTask());
                    db.insertTask(task);
                }
                dismiss();
            }
        });

        newTaskImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open Camera
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });

        newTaskExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog){
        Activity activity = getActivity();
        if (activity instanceof DialogCloseListener){
            ((DialogCloseListener)activity).handleDialogClose(dialog);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            //Get Capture Image
            captureImage = (Bitmap) data.getExtras().get("data");
            //Set Capture Image to ImageView
            imageView.setImageBitmap(captureImage);
            newTaskImageButton.setText("Update Image");
        }
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                textViewDate.setText("Due Date: " + date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(getContext(), style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year)
    {
        return day + " " + getMonthFormat(month) + " " +  year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

}
