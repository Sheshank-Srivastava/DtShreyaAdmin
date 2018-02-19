package com.dietitianshreya.dtshreyaadmin.models;

/**
 * Created by hp on 2/9/2018.
 */

public class AppointmentsModel {

    String title,time,place;

    public AppointmentsModel(String title, String time, String place){
        this.title = title;
        this.time = time;
        this.place = place;

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
}
