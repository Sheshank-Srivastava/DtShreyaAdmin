package com.dietitianshreya.eatrightdietadmin.models;

import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.pchmn.materialchips.model.ChipInterface;

/**
 * Created by hp on 6/30/2018.
 */



public class DiseaseChip implements ChipInterface {

    private String id;
    private String name, subName;


    public DiseaseChip(String name, String subName) {
        this.id = id;
        this.name = name;
        this.subName = subName;
    }

    @Override
    public Object getId() {
        return id;
    }

    @Override
    public Uri getAvatarUri() {
        return null;
    }

    @Override
    public Drawable getAvatarDrawable() {
        return null;
    }


    public String getSubName() {
        return subName;
    }


    @Override
    public String getLabel() {
        return name;



    }

    @Override
    public String getInfo() {
        return subName;
    }
}