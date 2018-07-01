package com.dietitianshreya.eatrightdietadmin.models;

/**
 * Created by manyamadan on 30/04/18.
 */

public class TemplateModel {
    int id;
    String name;
    public TemplateModel(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
