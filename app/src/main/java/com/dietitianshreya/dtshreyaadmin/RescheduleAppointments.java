package com.dietitianshreya.dtshreyaadmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dietitianshreya.dtshreyaadmin.adapters.RescheduleAppointmentAdaper;
import com.dietitianshreya.dtshreyaadmin.models.RescheduleAppointmentModel;

import java.util.ArrayList;

public class RescheduleAppointments extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<RescheduleAppointmentModel> appointmentList;
    RescheduleAppointmentAdaper appointmentAdaper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reschedule_appointments);
        recyclerView = (RecyclerView) findViewById(R.id.re);
        appointmentList = new ArrayList<>();
        appointmentAdaper = new RescheduleAppointmentAdaper(appointmentList,RescheduleAppointments.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(appointmentAdaper);
        recyclerView.setLayoutManager(layoutManager);
        appointmentList.add(new RescheduleAppointmentModel("Akshit Tyagi","1234567","10/06/2018 - 9:30 A.M.","10/06/2018 - 9:30 A.M.","awaited"));
        appointmentList.add(new RescheduleAppointmentModel("Akshit Tyagi","1234567","10/06/2018 - 9:30 A.M.","10/06/2018 - 9:30 A.M.","awaited"));
        appointmentList.add(new RescheduleAppointmentModel("Akshit Tyagi","1234567","10/06/2018 - 9:30 A.M.","10/06/2018 - 9:30 A.M.","awaited"));
        appointmentList.add(new RescheduleAppointmentModel("Akshit Tyagi","1234567","10/06/2018 - 9:30 A.M.","10/06/2018 - 9:30 A.M.","awaited"));
        appointmentList.add(new RescheduleAppointmentModel("Akshit Tyagi","1234567","10/06/2018 - 9:30 A.M.","10/06/2018 - 9:30 A.M.","awaited"));
        appointmentList.add(new RescheduleAppointmentModel("Akshit Tyagi","1234567","10/06/2018 - 9:30 A.M.","10/06/2018 - 9:30 A.M.","awaited"));
        appointmentAdaper.notifyDataSetChanged();
    }
}
