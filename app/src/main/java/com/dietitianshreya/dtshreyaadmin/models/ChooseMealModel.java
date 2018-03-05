package com.dietitianshreya.dtshreyaadmin.models;

/**
 * Created by hp on 3/5/2018.
 */

public class ChooseMealModel {
    String mealName,mealQuant;

    public ChooseMealModel(String mealName, String mealQuant) {
        this.mealName = mealName;
        this.mealQuant = mealQuant;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getMealQuant() {
        return mealQuant;
    }

    public void setMealQuant(String mealQuant) {
        this.mealQuant = mealQuant;
    }
}
