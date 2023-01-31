package com.example.online_attendance_indtechsoft.Notification;

public class NotificationModel {

    public NotificationModel(String title, String msg) {
        this.title = title;
        this.msg = msg;
    }

    String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    String msg;

    public NotificationModel(){}

}
