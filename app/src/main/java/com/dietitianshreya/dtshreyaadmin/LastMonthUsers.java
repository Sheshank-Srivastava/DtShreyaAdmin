package com.dietitianshreya.dtshreyaadmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dietitianshreya.dtshreyaadmin.adapters.ExtensionLeadsAdaper;
import com.dietitianshreya.dtshreyaadmin.adapters.LastMonthUsersAdapter;
import com.dietitianshreya.dtshreyaadmin.models.ExtensionLeadsModel;
import com.dietitianshreya.dtshreyaadmin.models.LastMonthUsersModel;

import java.util.ArrayList;

public class LastMonthUsers extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<LastMonthUsersModel> clientList;
    LastMonthUsersAdapter clientAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_month_users);
        recyclerView = (RecyclerView) findViewById(R.id.re);
        clientList = new ArrayList<>();
        clientAdapter = new LastMonthUsersAdapter(clientList,this);
        recyclerView.setAdapter(clientAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        clientList.add(new LastMonthUsersModel("Akshit Tyagi","123456","10/02/2018","5 kg","2 Months","awaited"));
        clientAdapter.notifyDataSetChanged();
    }
}
