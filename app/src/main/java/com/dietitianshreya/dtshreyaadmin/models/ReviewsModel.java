package com.dietitianshreya.dtshreyaadmin.models;

/**
 * Created by hp on 6/22/2018.
 */

public class ReviewsModel {
    float rating;
    String clientName,comment;

    public ReviewsModel(float rating, String clientName, String comment) {
        this.rating = rating;
        this.clientName = clientName;
        this.comment = comment;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
