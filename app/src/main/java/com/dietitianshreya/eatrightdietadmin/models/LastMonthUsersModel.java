package com.dietitianshreya.eatrightdietadmin.models;

/**
 * Created by hp on 3/17/2018.
 */

public class LastMonthUsersModel {

    private String clientName,clientId,startedOn,kgLost,plan,email,daysLeft;

    public LastMonthUsersModel(String clientName, String clientId, String startedOn, String kgLost, String plan, String email,String daysLeft) {
        this.clientName = clientName;
        this.clientId = clientId;
        this.startedOn = startedOn;
        this.kgLost = kgLost;
        this.plan = plan;
        this.email = email;
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

    public String getStartedOn() {
        return startedOn;
    }

    public void setStartedOn(String startedOn) {
        this.startedOn = startedOn;
    }

    public String getKgLost() {
        return kgLost;
    }

    public void setKgLost(String kgLost) {
        this.kgLost = kgLost;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(String daysLeft) {
        this.daysLeft = daysLeft;
    }
}
