package com.dietitianshreya.eatrightdietadmin.models;

/**
 * Created by hp on 2/15/2018.
 */

public class ClientListModel {
    private String clientName,clientId,daysLeft;



    public ClientListModel(String clientName, String clientId, String daysLeft) {
        this.clientName = clientName;
        this.clientId = clientId;
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
    public String getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(String daysLeft) {
        this.daysLeft = daysLeft;
    }
}
