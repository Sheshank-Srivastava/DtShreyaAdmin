package com.dietitianshreya.eatrightdietadmin.models;

import java.util.ArrayList;

/**
 * Created by manyamadan on 19/03/18.
 */

public class TestHistoryModel {

    String date;
    ArrayList<TestDetailModel> testDetailsList;

    public TestHistoryModel(String date, ArrayList<TestDetailModel> testDetailsList){
        this.date = date;
        this.testDetailsList = testDetailsList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<TestDetailModel> getTestDetailsList() {
        return testDetailsList;
    }

    public void setTestDetailsList(ArrayList<TestDetailModel> testDetailsList) {
        this.testDetailsList = testDetailsList;
    }
}
