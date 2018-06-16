package com.dietitianshreya.dtshreyaadmin.models;

/**
 * Created by manyamadan on 06/06/18.
 */

public class DiseaseModel {

    String DiseaseName;
    public  DiseaseModel(String DiseaseName)
    {
        this.DiseaseName = DiseaseName;

    }

    public String getDiseaseName() {
        return DiseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        DiseaseName = diseaseName;
    }


}
