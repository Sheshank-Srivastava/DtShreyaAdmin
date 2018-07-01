package com.dietitianshreya.eatrightdietadmin.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hp on 2/9/2018.
 */

public class MealModel implements Parcelable{

    String mealHead, mealDesc;
    boolean status;

    public MealModel(String mealHead, String mealDesc, boolean status){
        this.mealHead = mealHead;
        this.mealDesc = mealDesc;
        this.status = status;

    }

    protected MealModel(Parcel in) {
        mealHead = in.readString();
        mealDesc = in.readString();
        status = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mealHead);
        dest.writeString(mealDesc);
        dest.writeByte((byte) (status ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MealModel> CREATOR = new Creator<MealModel>() {
        @Override
        public MealModel createFromParcel(Parcel in) {
            return new MealModel(in);
        }

        @Override
        public MealModel[] newArray(int size) {
            return new MealModel[size];
        }
    };

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
