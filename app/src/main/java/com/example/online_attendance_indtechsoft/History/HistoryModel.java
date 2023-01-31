package com.example.online_attendance_indtechsoft.History;

public class HistoryModel {
    public HistoryModel(String location, String time) {
        this.location = location;
        this.time = time;
    }

    String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    String time;

    public HistoryModel(){}
}
