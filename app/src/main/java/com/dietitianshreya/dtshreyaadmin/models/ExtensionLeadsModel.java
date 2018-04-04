package com.dietitianshreya.dtshreyaadmin.models;

/**
 * Created by hp on 3/17/2018.
 */

public class ExtensionLeadsModel {

    private String clientName,clientId,joinedOn,daysPassed,plan,phone,daysLeft;

    public ExtensionLeadsModel(String clientName, String clientId, String joinedOn, String daysPassed, String plan, String phone,String daysLeft) {
        this.clientName = clientName;
        this.clientId = clientId;
        this.joinedOn = joinedOn;
        this.daysPassed = daysPassed;
        this.plan = plan;
        this.phone = phone;
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

    public String getJoinedOn() {
        return joinedOn;
    }

    public void setJoinedOn(String joinedOn) {
        this.joinedOn = joinedOn;
    }

    public String getDaysPassed() {
        return daysPassed;
    }

    public void setDaysPassed(String daysPassed) {
        this.daysPassed = daysPassed;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(String daysLeft) {
        this.daysLeft = daysLeft;
    }
}
