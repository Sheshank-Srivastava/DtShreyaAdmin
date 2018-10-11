package com.dietitianshreya.eatrightdietadmin.models;

public class ClientListModel2 {
    private String clientName,clientId,daysLeft;
    private boolean read;
    private long timeStamp;

    public ClientListModel2() {
    }

    public ClientListModel2(String clientName, String clientId, String daysLeft, boolean read, long timeStamp) {
        this.clientName = clientName;
        this.clientId = clientId;
        this.daysLeft = daysLeft;
        this.read = read;
        this.timeStamp =timeStamp;

    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
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
