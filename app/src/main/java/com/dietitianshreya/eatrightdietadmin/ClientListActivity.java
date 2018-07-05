package com.dietitianshreya.eatrightdietadmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dietitianshreya.eatrightdietadmin.adapters.ClientListAdapter;
import com.dietitianshreya.eatrightdietadmin.models.ClientListModel;

import java.util.ArrayList;

public class ClientListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ClientListModel> clientList;
    ClientListAdapter clientListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);
        getSupportActionBar().setTitle("Clients");
        recyclerView = (RecyclerView) findViewById(R.id.re);
        clientList=new ArrayList<>();
        clientListAdapter = new ClientListAdapter(clientList,ClientListActivity.this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(ClientListActivity.this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(clientListAdapter);
        clientList.add(new ClientListModel("Akshit Tyagi","Id : 345rde3","4 days left"));
        clientList.add(new ClientListModel("Paras Garg","Id : 345rde3","4 days left"));
        clientList.add(new ClientListModel("Balkeerat","Id : 345rde3","4 days left"));
        clientList.add(new ClientListModel("Manya ","Id : 345rde3","4 days left"));
        clientListAdapter.notifyDataSetChanged();
    }
}
