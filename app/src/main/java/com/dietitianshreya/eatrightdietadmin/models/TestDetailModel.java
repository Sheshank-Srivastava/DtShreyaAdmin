package com.dietitianshreya.eatrightdietadmin.models;

/**
 * Created by manyamadan on 19/03/18.
 */


public class TestDetailModel {
    private String time,test,type,client,daysLeft;

    public TestDetailModel(String time, String test, String type, String client,String daysLeft) {
        this.time = time;
        this.test = test;
        this.type = type;
        this.client = client;
        this.daysLeft = daysLeft;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(String daysLeft) {
        this.daysLeft = daysLeft;
    }
}
