package com.dietitianshreya.eatrightdietadmin.models;

import java.util.ArrayList;

/**
 * Created by hp on 2/9/2018.
 */

public class AppointmentHistoryModel {

    String date;
    ArrayList<AppointmentDetailsModel> appointmentDetailsList;

    public AppointmentHistoryModel(String date, ArrayList<AppointmentDetailsModel> appointmentDetailsList){
        this.date = date;
        this.appointmentDetailsList = appointmentDetailsList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<AppointmentDetailsModel> getAppointmentDetailsList() {
        return appointmentDetailsList;
    }

    public void setAppointmentDetailsList(ArrayList<AppointmentDetailsModel> appointmentDetailsList) {
        this.appointmentDetailsList = appointmentDetailsList;
    }
}
