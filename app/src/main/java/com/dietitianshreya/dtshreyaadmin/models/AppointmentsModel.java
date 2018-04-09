package com.dietitianshreya.dtshreyaadmin.models;

/**
 * Created by hp on 2/9/2018.
 */

public class AppointmentsModel {

    String title,time,place,daysLeft,id;

    public AppointmentsModel(String title, String time, String place,String daysLeft,String id){
        this.title = title;
        this.time = time;
        this.place = place;
        this.daysLeft = daysLeft;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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
