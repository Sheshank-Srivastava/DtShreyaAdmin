package com.dietitianshreya.dtshreyaadmin.models;

/**
 * Created by hp on 2/21/2018.
 */

public class ClientAppointmentModel {
    private String time,type,status,dietitian,date;

    public ClientAppointmentModel(String date, String time, String type, String status, String dietitian) {
        this.date = date;

        this.time = time;
        this.type = type;
        this.status = status;
        this.dietitian = dietitian;
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

    public String getDietitian() {
        return dietitian;
    }

    public void setDietitian(String client) {
        this.dietitian = client;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
