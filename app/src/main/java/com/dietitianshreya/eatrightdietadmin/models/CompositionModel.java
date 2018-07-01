package com.dietitianshreya.eatrightdietadmin.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hp on 3/17/2018.
 */

public class CompositionModel implements Parcelable{
    private String date,weight,sfat,vfat,bmi,muscle,bodyage,resmeta,height;

    public CompositionModel(String date, String weight, String sfat, String vfat, String bmi, String muscle, String bodyage, String resmeta, String height) {
        this.date = date;
        this.weight = weight;
        this.sfat = sfat;
        this.vfat = vfat;
        this.bmi = bmi;
        this.muscle = muscle;
        this.bodyage = bodyage;
        this.resmeta = resmeta;
        this.height = height;
    }

    protected CompositionModel(Parcel in) {
        date = in.readString();
        weight = in.readString();
        height = in.readString();
        sfat = in.readString();
        vfat = in.readString();
        bodyage = in.readString();
        muscle = in.readString();
        bmi = in.readString();
        resmeta = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(weight);
        dest.writeString(height);
        dest.writeString(sfat);
        dest.writeString(vfat);
        dest.writeString(bodyage);
        dest.writeString(muscle);
        dest.writeString(bmi);
        dest.writeString(resmeta);
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

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public String getSfat() {
        return sfat;
    }

    public void setSfat(String sfat) {
        this.sfat = sfat;
    }

    public String getVfat() {
        return vfat;
    }

    public void setVfat(String vfat) {
        this.vfat = vfat;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getBodyage() {
        return bodyage;
    }

    public void setBodyage(String bodyage) {
        this.bodyage = bodyage;
    }

    public String getResmeta() {
        return resmeta;
    }

    public void setResmeta(String resmeta) {
        this.resmeta = resmeta;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
