package com.dietitianshreya.eatrightdietadmin.models;

import java.util.ArrayList;

/**
 * Created by hp on 3/1/2018.
 */

public class AllClientsCompoundModel {
    String head;
    ArrayList<AllClientListOthersModel> clientList;

    public AllClientsCompoundModel(String head, ArrayList<AllClientListOthersModel> clientList) {
        this.head = head;
        this.clientList = clientList;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public ArrayList<AllClientListOthersModel> getClientList() {
        return clientList;
    }

    public void setClientList(ArrayList<AllClientListOthersModel> clientList) {
        this.clientList = clientList;
    }
}
