package com.dietitianshreya.dtshreyaadmin.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hp on 3/17/2018.
 */

public class CompositionModel implements Parcelable{
    private String date,weight,fat,water,muscle,bone;

    public CompositionModel(String date, String weight, String fat, String water, String muscle, String bone) {
        this.date = date;
        this.weight = weight;
        this.fat = fat;
        this.water = water;
        this.muscle = muscle;
        this.bone = bone;
    }

    protected CompositionModel(Parcel in) {
        date = in.readString();
        weight = in.readString();
        fat = in.readString();
        water = in.readString();
        muscle = in.readString();
        bone = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(weight);
        dest.writeString(fat);
        dest.writeString(water);
        dest.writeString(muscle);
        dest.writeString(bone);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CompositionModel> CREATOR = new Creator<CompositionModel>() {
        @Override
        public CompositionModel createFromParcel(Parcel in) {
            return new CompositionModel(in);
        }

        @Override
        public CompositionModel[] newArray(int size) {
            return new CompositionModel[size];
        }
    };

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
