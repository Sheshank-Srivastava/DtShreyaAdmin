package com.dietitianshreya.dtshreyaadmin.models;

/**
 * Created by hp on 3/17/2018.
 */

public class CompositionModel {
    private String date,weight,fat,water,muscle,bone;

    public CompositionModel(String date, String weight, String fat, String water, String muscle, String bone) {
        this.date = date;
        this.weight = weight;
        this.fat = fat;
        this.water = water;
        this.muscle = muscle;
        this.bone = bone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public String getBone() {
        return bone;
    }

    public void setBone(String bone) {
        this.bone = bone;
    }
}
