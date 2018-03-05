package com.dietitianshreya.dtshreyaadmin.models;

/**
 * Created by hp on 2/9/2018.
 */

public class MealModel {

    String mealHead, mealDesc;
    boolean status;

    public MealModel(String mealHead, String mealDesc, boolean status){
        this.mealHead = mealHead;
        this.mealDesc = mealDesc;
        this.status = status;

    }

    public String getMealHead() {
        return mealHead;
    }

    public void setMealHead(String mealHead) {
        this.mealHead = mealHead;
    }

    public String getMealDesc() {
        return mealDesc;
    }

    public void setMealDesc(String mealDesc) {
        this.mealDesc = mealDesc;
    }

    public boolean getstatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


}
