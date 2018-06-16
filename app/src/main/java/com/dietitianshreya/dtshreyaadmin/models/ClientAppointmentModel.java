package com.dietitianshreya.dtshreyaadmin.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hp on 2/21/2018.
 */

public class ClientAppointmentModel implements Parcelable {
    private String time,type,status,dietitian,date;

    public ClientAppointmentModel(String date, String time, String type, String status, String dietitian) {
        this.date = date;

        this.time = time;
        this.type = type;
        this.status = status;
        this.dietitian = dietitian;
    }

    protected ClientAppointmentModel(Parcel in) {
        time = in.readString();
        type = in.readString();
        status = in.readString();
        dietitian = in.readString();
        date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(time);
        dest.writeString(type);
        dest.writeString(status);
        dest.writeString(dietitian);
        dest.writeString(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ClientAppointmentModel> CREATOR = new Creator<ClientAppointmentModel>() {
        @Override
        public ClientAppointmentModel createFromParcel(Parcel in) {
            return new ClientAppointmentModel(in);
        }

        @Override
        public ClientAppointmentModel[] newArray(int size) {
            return new ClientAppointmentModel[size];
        }
    };

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDietitian() {
        return dietitian;
    }

    public void setDietitian(String client) {
        this.dietitian = client;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
