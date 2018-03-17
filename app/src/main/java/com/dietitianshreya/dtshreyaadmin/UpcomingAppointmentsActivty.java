package com.dietitianshreya.dtshreyaadmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dietitianshreya.dtshreyaadmin.adapters.UpcomingAppointmentAdapter;
import com.dietitianshreya.dtshreyaadmin.models.AppointmentDetailsModel;

import java.util.ArrayList;

public class UpcomingAppointmentsActivty extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<AppointmentDetailsModel> appointmentDetailsList;
    UpcomingAppointmentAdapter appointmentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_appointments);
        getSupportActionBar().setTitle("Upcoming Appointments");
        recyclerView = (RecyclerView) findViewById(R.id.re);
        appointmentDetailsList = new ArrayList<>();
        appointmentAdapter = new UpcomingAppointmentAdapter(appointmentDetailsList,UpcomingAppointmentsActivty.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(UpcomingAppointmentsActivty.this);
        recyclerView.setAdapter(appointmentAdapter);
        recyclerView.setLayoutManager(mLayoutManager);
        appointmentDetailsList.add(new AppointmentDetailsModel("9:30 A.M.","In Person","0","Akshit Tyagi"));
        appointmentDetailsList.add(new AppointmentDetailsModel("9:30 A.M.","In Person","0","Akshit Tyagi"));
        appointmentDetailsList.add(new AppointmentDetailsModel("9:30 A.M.","In Person","0","Akshit Tyagi"));
        appointmentDetailsList.add(new AppointmentDetailsModel("9:30 A.M.","In Person","0","Akshit Tyagi"));
        appointmentDetailsList.add(new AppointmentDetailsModel("9:30 A.M.","In Person","0","Akshit Tyagi"));
        appointmentDetailsList.add(new AppointmentDetailsModel("9:30 A.M.","In Person","0","Akshit Tyagi"));
        appointmentDetailsList.add(new AppointmentDetailsModel("9:30 A.M.","In Person","0","Akshit Tyagi"));
        appointmentDetailsList.add(new AppointmentDetailsModel("9:30 A.M.","In Person","0","Akshit Tyagi"));
        appointmentDetailsList.add(new AppointmentDetailsModel("9:30 A.M.","In Person","0","Akshit Tyagi"));
        appointmentDetailsList.add(new AppointmentDetailsModel("9:30 A.M.","In Person","0","Akshit Tyagi"));
        appointmentDetailsList.add(new AppointmentDetailsModel("9:30 A.M.","In Person","0","Akshit Tyagi"));
        appointmentDetailsList.add(new AppointmentDetailsModel("9:30 A.M.","In Person","0","Akshit Tyagi"));
        appointmentAdapter.notifyDataSetChanged();
    }
}
