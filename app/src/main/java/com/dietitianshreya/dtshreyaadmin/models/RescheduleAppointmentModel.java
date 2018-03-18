package com.dietitianshreya.dtshreyaadmin.models;

/**
 * Created by hp on 3/17/2018.
 */

public class RescheduleAppointmentModel {
    private String clientName,clientId,appointmentOn,requestedOn,status;

    public RescheduleAppointmentModel(String clientName, String clientId, String appointmentOn, String requestedOn, String status) {
        this.clientName = clientName;
        this.clientId = clientId;
        this.appointmentOn = appointmentOn;
        this.requestedOn = requestedOn;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
