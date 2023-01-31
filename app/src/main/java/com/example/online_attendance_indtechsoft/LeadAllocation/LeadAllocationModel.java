package com.example.online_attendance_indtechsoft.LeadAllocation;

public class LeadAllocationModel {
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public LeadAllocationModel(String mobile) {
        this.mobile = mobile;
        this.id = id;
        this.wind = wind;
    }

    String mobile;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    String wind;


    public LeadAllocationModel(){}
}
