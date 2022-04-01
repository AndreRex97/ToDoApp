package com.example.todoapp.Model;

import android.graphics.Bitmap;

public class ToDoModel {
    private int id, status;
    private String task;
    private byte[] image;
    private String date;
    private String location;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public byte[] getImage() {return image;};

    public void setImage(byte[] image){this.image = image;};

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
