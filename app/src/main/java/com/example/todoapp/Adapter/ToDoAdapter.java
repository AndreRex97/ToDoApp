package com.example.todoapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.AddNewTask;
import com.example.todoapp.MainActivity;
import com.example.todoapp.Model.ToDoModel;
import com.example.todoapp.R;
import com.example.todoapp.Utils.DatabaseHandler;
import com.example.todoapp.Utils.DbBitmapUtility;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {
    private List<ToDoModel> todoList;
    private MainActivity activity;
    private DatabaseHandler db;

    public ToDoAdapter(DatabaseHandler db, MainActivity activity) {
        this.db = db;
        this.activity = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        db.openDatabase();
        ToDoModel item = todoList.get(position);
        byte[] image = item.getImage();
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    db.updateStatus(item.getId(), 1);
                }
                else{
                    db.updateStatus(item.getId(), 0 );
                }
            }
        });
        Bitmap imageTask = DbBitmapUtility.getImage(image);
        holder.imageView.setImageBitmap(imageTask);
        holder.textViewDate.setText(item.getDate());
        holder.textViewLocation.setText("Location: " + item.getLocation());
        if (item.getDate().isEmpty()){
            holder.textViewDate.setTextSize(0);
        } else {
            holder.textViewDate.setTextSize(20);
        }

        if (item.getLocation().isEmpty()){
            holder.textViewLocation.setTextSize(0);
        } else {
            holder.textViewLocation.setTextSize(16);
        }

        //Increase Image size
        if (imageTask != null){
            holder.imageView.getLayoutParams().width = 600;
            holder.imageView.getLayoutParams().height = 600;
        } else {
            holder.imageView.getLayoutParams().width = 0;
            holder.imageView.getLayoutParams().height = 0;
        }
    }

    private boolean toBoolean(int n) {
        return n != 0;
    }

    public int getItemCount() {
        return todoList.size();
    }

    public void setTasks(List<ToDoModel> todoList) {
        this.todoList = todoList;
        notifyDataSetChanged();
    }

    public Context getContext(){
        return activity;
    }

    public void deleteItem(int position){
        ToDoModel item = todoList.get(position);
        db.deleteTask(item.getId());
        todoList.remove(position);
        notifyItemRemoved(position);
    }
    
    public void editItem(int position) {
        ToDoModel item = todoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());
        bundle.putByteArray("image", item.getImage());
        bundle.putString("date", item.getDate());
        bundle.putString("location", item.getLocation());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddNewTask.TAG);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;
        ImageView imageView;
        TextView textViewDate, textViewLocation;

        ViewHolder(View view) {
            super(view);
            task = view.findViewById(R.id.todoCheckBox);
            imageView = view.findViewById(R.id.imageView);
            textViewDate = view.findViewById(R.id.textViewDate);
            textViewLocation = view.findViewById(R.id.textViewLocation);
        }
    }
}
