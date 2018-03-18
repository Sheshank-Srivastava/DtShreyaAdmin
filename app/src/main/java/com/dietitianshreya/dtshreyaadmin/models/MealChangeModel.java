package com.dietitianshreya.dtshreyaadmin.models;

/**
 * Created by hp on 3/13/2018.
 */

public class MealChangeModel {
    private String clientName,clientId,previousMeal,requestedMeal,mealType,status,mealTime;

    public MealChangeModel(String clientName, String clientId, String previousMeal, String requestedMeal, String mealType, String status, String mealTime) {
        this.clientName = clientName;
        this.clientId = clientId;
        this.previousMeal = previousMeal;
        this.requestedMeal = requestedMeal;
        this.mealType = mealType;
        this.status = status;
        this.mealTime = mealTime;
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

    public String getPreviousMeal() {
        return previousMeal;
    }

    public void setPreviousMeal(String previousMeal) {
        this.previousMeal = previousMeal;
    }

    public String getRequestedMeal() {
        return requestedMeal;
    }

    public void setRequestedMeal(String requestedMeal) {
        this.requestedMeal = requestedMeal;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMealTime() {
        return mealTime;
    }

    public void setMealTime(String mealTime) {
        this.mealTime = mealTime;
    }
}
