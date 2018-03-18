package com.dietitianshreya.dtshreyaadmin.models;

/**
 * Created by hp on 3/17/2018.
 */

public class ExtensionLeadsModel {

    private String clientName,clientId,joinedOn,daysPassed,plan,status;

    public ExtensionLeadsModel(String clientName, String clientId, String joinedOn, String daysPassed, String plan, String status) {
        this.clientName = clientName;
        this.clientId = clientId;
        this.joinedOn = joinedOn;
        this.daysPassed = daysPassed;
        this.plan = plan;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
