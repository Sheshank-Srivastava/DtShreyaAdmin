package com.dietitianshreya.dtshreyaadmin.models;

/**
 * Created by hp on 3/9/2018.
 */

public class DiaryEntryModel {
    private String head,body;

    public DiaryEntryModel(String head, String body) {
        this.head = head;
        this.body = body;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

