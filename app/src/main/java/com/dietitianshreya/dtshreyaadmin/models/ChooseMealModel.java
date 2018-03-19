package com.dietitianshreya.dtshreyaadmin.models;

import java.util.ArrayList;

/**
 * Created by hp on 3/5/2018.
 */

public class ChooseMealModel {
    String mealName,mealQuant;
    ArrayList<String> exempted;

    public ChooseMealModel(String mealName, String mealQuant,ArrayList<String> exempted) {
        this.mealName = mealName;
        this.mealQuant = mealQuant;
        this.exempted = exempted;
    }

    public ArrayList<String> getExempted() {
        return exempted;
    }

    public void setExempted(ArrayList<String> exempted) {
        this.exempted = exempted;
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
