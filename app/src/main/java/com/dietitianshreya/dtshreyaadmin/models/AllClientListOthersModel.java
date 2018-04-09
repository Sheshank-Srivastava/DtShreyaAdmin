package com.dietitianshreya.dtshreyaadmin.models;

/**
 * Created by hp on 2/15/2018.
 */

public class AllClientListOthersModel {
    private String clientName,clientId,days,daysLeft;

    public AllClientListOthersModel(String clientName, String clientId, String days, String daysLeft) {
        this.clientName = clientName;
        this.clientId = clientId;
        this.days = days;
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

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(String daysLeft) {
        this.daysLeft = daysLeft;
    }
}
