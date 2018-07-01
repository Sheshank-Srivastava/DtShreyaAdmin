package com.dietitianshreya.eatrightdietadmin.models;

/**
 * Created by hp on 3/5/2018.
 */

public class ChooseMealModel {
    String mealName,mealQuant;
    int exempted,pos;

    public ChooseMealModel(String mealName, String mealQuant,int exempted) {
        this.mealName = mealName;
        this.mealQuant = mealQuant;
        this.exempted = exempted;
    }

    public int getExempted() {
        return exempted;
    }

    public void setExempted(int exempted) {
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

    public  void setMealPos(int pos)
    {
        this.pos = pos;
    }

    public  int getMealPos()
    {
        return pos;
    }


}
