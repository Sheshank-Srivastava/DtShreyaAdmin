package com.dietitianshreya.dtshreyaadmin.models;

/**
 * Created by hp on 2/21/2018.
 */

public class AppointmentDetailsModel {
    private String time,type,status,client,daysLeft,id;

    public AppointmentDetailsModel(String time, String type, String status, String client,String daysLeft,String id) {
        this.time = time;
        this.type = type;
        this.status = status;
        this.client = client;
        this.daysLeft =daysLeft;
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(String daysLeft) {
        this.daysLeft = daysLeft;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
