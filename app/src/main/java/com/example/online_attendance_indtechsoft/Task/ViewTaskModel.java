package com.example.online_attendance_indtechsoft.Task;

public class ViewTaskModel {

    String task_id;

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getTask_assigned_by() {
        return task_assigned_by;
    }

    public void setTask_assigned_by(String task_assigned_by) {
        this.task_assigned_by = task_assigned_by;
    }

    public String getTask_assigned_to() {
        return task_assigned_to;
    }

    public void setTask_assigned_to(String task_assigned_to) {
        this.task_assigned_to = task_assigned_to;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public ViewTaskModel(String task_id, String task_name, String task_assigned_by, String task_assigned_to, String status) {
        this.task_id = task_id;
        this.task_name = task_name;
        this.task_assigned_by = task_assigned_by;
        this.task_assigned_to = task_assigned_to;
        Status = status;
    }

    String task_name;
    String task_assigned_by;
    String task_assigned_to;
    String Status;


    public ViewTaskModel(){}
}
