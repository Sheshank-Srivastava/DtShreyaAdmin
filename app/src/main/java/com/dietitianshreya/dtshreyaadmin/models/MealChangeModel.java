package com.dietitianshreya.dtshreyaadmin.models;

/**
 * Created by hp on 3/13/2018.
 */

public class MealChangeModel {
    private String clientName,clientId,date;

    public MealChangeModel(String clientName, String clientId, String date) {
        this.clientName = clientName;
        this.clientId = clientId;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
