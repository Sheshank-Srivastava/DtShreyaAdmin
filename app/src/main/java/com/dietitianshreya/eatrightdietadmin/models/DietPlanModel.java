package com.dietitianshreya.eatrightdietadmin.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by hp on 2/9/2018.
 */

public class DietPlanModel implements Parcelable {

    String title;
    int pos;
    ArrayList<MealModel> mealList;

    public DietPlanModel(String title,ArrayList<MealModel> mealList,int pos){

        this.title = title;
        this.mealList = mealList;
        this.pos=pos;
    }

    protected DietPlanModel(Parcel in) {
        title = in.readString();
        pos = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(pos);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DietPlanModel> CREATOR = new Creator<DietPlanModel>() {
        @Override
        public DietPlanModel createFromParcel(Parcel in) {
            return new DietPlanModel(in);
        }

        @Override
        public DietPlanModel[] newArray(int size) {
            return new DietPlanModel[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public ArrayList<MealModel> getMealList() {
        return mealList;
    }

    public void setMealList(ArrayList<MealModel> mealList) {
        this.mealList = mealList;
    }
}
