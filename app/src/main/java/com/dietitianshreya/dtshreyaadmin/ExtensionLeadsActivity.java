package com.dietitianshreya.dtshreyaadmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dietitianshreya.dtshreyaadmin.adapters.ExtensionLeadsAdaper;
import com.dietitianshreya.dtshreyaadmin.models.ExtensionLeadsModel;

import java.util.ArrayList;

public class ExtensionLeadsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ExtensionLeadsModel> leadList;
    ExtensionLeadsAdaper leadsAdaper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extension_leads);
        recyclerView = (RecyclerView) findViewById(R.id.re);
        leadList = new ArrayList<>();
        leadsAdaper = new ExtensionLeadsAdaper(leadList,this);
        recyclerView.setAdapter(leadsAdaper);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        leadList.add(new ExtensionLeadsModel("Akshit Tyagi","123456","10/02/2018","5 days","2 Months","awaited"));
        leadList.add(new ExtensionLeadsModel("Akshit Tyagi","123456","10/02/2018","5 days","2 Months","awaited"));
        leadList.add(new ExtensionLeadsModel("Akshit Tyagi","123456","10/02/2018","5 days","2 Months","awaited"));
        leadList.add(new ExtensionLeadsModel("Akshit Tyagi","123456","10/02/2018","5 days","2 Months","awaited"));
        leadList.add(new ExtensionLeadsModel("Akshit Tyagi","123456","10/02/2018","5 days","2 Months","awaited"));
        leadsAdaper.notifyDataSetChanged();
    }
}
