package com.dietitianshreya.dtshreyaadmin.models;

/**
 * Created by hp on 2/15/2018.
 */

public class ClientListModel {
    private String clientName,clientId;

    public ClientListModel(String clientName, String clientId) {
        this.clientName = clientName;
        this.clientId = clientId;
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
}
