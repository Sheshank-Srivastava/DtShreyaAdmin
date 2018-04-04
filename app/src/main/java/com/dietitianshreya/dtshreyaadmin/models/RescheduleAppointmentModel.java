package com.dietitianshreya.dtshreyaadmin.models;

/**
 * Created by hp on 3/17/2018.
 */

public class RescheduleAppointmentModel {
    private String clientName,clientId,appointmentOn,requestedOn,id,daysLeft;

    public RescheduleAppointmentModel(String clientName, String clientId, String appointmentOn, String requestedOn, String id,String daysLeft) {
        this.clientName = clientName;
        this.clientId = clientId;
        this.appointmentOn = appointmentOn;
        this.requestedOn = requestedOn;
        this.id = id;
        this.daysLeft = daysLeft;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAppointmentOn() {
        return appointmentOn;
    }

    public void setAppointmentOn(String appointmentOn) {
        this.appointmentOn = appointmentOn;
    }

    public String getRequestedOn() {
        return requestedOn;
    }

    public void setRequestedOn(String requestedOn) {
        this.requestedOn = requestedOn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(String daysLeft) {
        this.daysLeft = daysLeft;
    }
}
