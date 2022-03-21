package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.todoapp.Adapter.ToDoAdapter;
import com.example.todoapp.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView tasksRecyclerView;
    private ToDoAdapter tasksAdapter;

    private List<ToDoModel> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskList = new ArrayList<>();

        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        tasksAdapter = new ToDoAdapter(this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        ToDoModel task = new ToDoModel();
        task.setTask("Test task");
        task.setStatus(0);
        task.setId(1);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);

        tasksAdapter.setTasks(taskList);
    }
}