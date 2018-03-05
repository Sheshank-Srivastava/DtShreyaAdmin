package com.dietitianshreya.dtshreyaadmin.models;

import java.util.ArrayList;

/**
 * Created by hp on 2/9/2018.
 */

public class DietPlanModel {

    String title;
    ArrayList<MealModel> mealList;

    public DietPlanModel(String title,ArrayList<MealModel> mealList){

        this.title = title;
        this.mealList = mealList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<MealModel> getMealList() {
        return mealList;
    }

    public void setMealList(ArrayList<MealModel> mealList) {
        this.mealList = mealList;
    }
}
