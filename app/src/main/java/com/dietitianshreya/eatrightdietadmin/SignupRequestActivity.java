package com.dietitianshreya.eatrightdietadmin;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.dietitianshreya.eatrightdietadmin.adapters.SignupRequestAdapter;
import com.dietitianshreya.eatrightdietadmin.models.ClientListModel;


import java.util.ArrayList;

public class SignupRequestActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SignupRequestAdapter adapter;
    ArrayList<ClientListModel> clientList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigunup_request);

        initComponent();
        initToolbar();
        Toast.makeText(this, "Swipe up bottom sheet", Toast.LENGTH_SHORT).show();
    }

    private void initComponent() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        clientList = new ArrayList<>();
        //set data and list adapter
        adapter = new SignupRequestAdapter(clientList,this);
        recyclerView.setAdapter(adapter);
//        adapter.setOnItemClickListener(new SignupRequestAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, People obj, int pos) {
//                FragmentBottomSheetDialogFull fragment = new FragmentBottomSheetDialogFull();
//                fragment.setPeople(obj);
//                fragment.show(getSupportFragmentManager(), "dialog");
//            }
//        });

//        // display first sheet
//        FragmentBottomSheetDialogFull fragment = new FragmentBottomSheetDialogFull();
//        fragment.setPeople(adapter.getItem(0));
//        fragment.show(getSupportFragmentManager(), "dialog");
    }

    private void initToolbar() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Full");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_basic, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            finish();
//        } else {
//            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
//        }
//        return super.onOptionsItemSelected(item);
//    }

}
