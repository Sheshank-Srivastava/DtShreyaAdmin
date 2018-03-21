package com.dietitianshreya.dtshreyaadmin;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dietitianshreya.dtshreyaadmin.adapters.AppointmentHistoryAdapter;
import com.dietitianshreya.dtshreyaadmin.adapters.TestAdapter;
import com.dietitianshreya.dtshreyaadmin.models.AppointmentDetailsModel;
import com.dietitianshreya.dtshreyaadmin.models.AppointmentHistoryModel;
import com.dietitianshreya.dtshreyaadmin.models.TestDetailModel;
import com.dietitianshreya.dtshreyaadmin.models.TestHistoryModel;

import java.util.ArrayList;

public class TestBooking extends AppCompatActivity {

    private AppointmentFragment.OnFragmentInteractionListener mListener;
    RecyclerView recyclerView;
    ArrayList<TestHistoryModel> testList;
    ArrayList<TestDetailModel> testDetailsList;
    TestAdapter testAdapter;
    FloatingActionButton fab;
    CoordinatorLayout coordinatorLayout;


    public static AppointmentFragment newInstance() {
        AppointmentFragment fragment = new AppointmentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_booking);

        // Inflate the layout for this fragment
        recyclerView = (RecyclerView) findViewById(R.id.re);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        testList=new ArrayList<>();
        testDetailsList = new ArrayList<>();
        testAdapter = new TestAdapter(testList,getApplicationContext());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(testAdapter);
        testDetailsList.add(new TestDetailModel("9:30 A.M.","Kidney Function Test","Empty Stomach","Akshit Tyagi"));
        testDetailsList.add(new TestDetailModel("10:30 A.M.","Renal Profile","With Pressure","Manya"));
        testDetailsList.add(new TestDetailModel("11:30 A.M.","Lipid Profile","after 2 days","Paras Garg"));
        testList.add(new TestHistoryModel("10/02/2018",testDetailsList));

        ArrayList<TestDetailModel> testDetailsList1 = new ArrayList<>();
        testDetailsList1.add(new TestDetailModel("9:30 A.M.","HBA1c","immidiately","Paras"));
        testDetailsList1.add(new TestDetailModel("9:30 A.M.","Vitamin D3","within 4 days","Balkeerat"));
        testDetailsList1.add(new TestDetailModel("9:30 A.M.","Thyroid Profile","Empty Stomach","Manya"));
        testList.add(new TestHistoryModel("11/02/2018",testDetailsList1));

        testAdapter.notifyDataSetChanged();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TestBooking.this,BookTest.class);
                startActivity(intent);
            }
        });

    }
}
