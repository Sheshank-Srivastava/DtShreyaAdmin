package com.dietitianshreya.dtshreyaadmin.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dietitianshreya.dtshreyaadmin.ChatActivity;
import com.dietitianshreya.dtshreyaadmin.R;
import com.dietitianshreya.dtshreyaadmin.models.MealModel;
import com.dietitianshreya.dtshreyaadmin.models.RescheduleAppointmentModel;

import java.util.ArrayList;


public class RescheduleAppointmentAdaper extends RecyclerView.Adapter<RescheduleAppointmentAdaper.MyViewHolder> {

    private ArrayList<RescheduleAppointmentModel> appointmentList;
    private Context mCtx;


    public RescheduleAppointmentAdaper(ArrayList<RescheduleAppointmentModel> appointmentList, Context mCtx) {
        this.appointmentList = appointmentList;
        this.mCtx = mCtx;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView clientName,previousDateTime,requestedDateTime;
        LinearLayout accept,decline;
        LinearLayout chat;
        public MyViewHolder(View view) {
            super(view);
            clientName = (TextView)  view.findViewById(R.id.clientName);
            previousDateTime = (TextView)  view.findViewById(R.id.previousDateTime);
            requestedDateTime = (TextView)  view.findViewById(R.id.requestedDateTime);
            accept = (LinearLayout)  view.findViewById(R.id.accept);
            decline = (LinearLayout)  view.findViewById(R.id.decline);
            chat = (LinearLayout) view.findViewById(R.id.chat);
        }
    }


    @Override
    public RescheduleAppointmentAdaper.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reschedule_appointment_list_row, parent, false);

        return new RescheduleAppointmentAdaper.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RescheduleAppointmentAdaper.MyViewHolder holder, int position) {

        final RescheduleAppointmentModel appointment = appointmentList.get(position);
        holder.clientName.setText(appointment.getClientName());
        holder.previousDateTime.setText(appointment.getAppointmentOn());
        holder.requestedDateTime.setText(appointment.getRequestedOn());
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mCtx,"Accepted!",Toast.LENGTH_SHORT).show();
            }
        });
        holder.decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mCtx,"Declined!",Toast.LENGTH_SHORT).show();
            }
        });
        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mCtx, ChatActivity.class);
                i.putExtra("clientId",appointment.getClientId());
                mCtx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return appointmentList.size();
    }



}