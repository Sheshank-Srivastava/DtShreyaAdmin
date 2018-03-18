package com.dietitianshreya.dtshreyaadmin.models;

/**
 * Created by hp on 3/17/2018.
 */

public class LastMonthUsersModel {

    private String clientName,clientId,startedOn,kgLost,plan,status;

    public LastMonthUsersModel(String clientName, String clientId, String startedOn, String kgLost, String plan, String status) {
        this.clientName = clientName;
        this.clientId = clientId;
        this.startedOn = startedOn;
        this.kgLost = kgLost;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
