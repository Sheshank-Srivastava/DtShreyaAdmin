package com.dietitianshreya.dtshreyaadmin.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dietitianshreya.dtshreyaadmin.R;
import com.dietitianshreya.dtshreyaadmin.models.AppointmentDetailsModel;

import java.util.ArrayList;


public class AppointmentDetailsAdapter extends RecyclerView.Adapter<AppointmentDetailsAdapter.MyViewHolder> {

    private ArrayList<AppointmentDetailsModel> appointmentDetailsList;
    private Context mCtx;


    public AppointmentDetailsAdapter(ArrayList<AppointmentDetailsModel> appointmentDetailsList, Context mCtx) {
        this.appointmentDetailsList = appointmentDetailsList;
        this.mCtx = mCtx;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView time,type,client,status;
        public ImageView typeImage;
        public MyViewHolder(View view) {
            super(view);
            time = (TextView)  view.findViewById(R.id.appointmentTime);
            type = (TextView)  view.findViewById(R.id.appointmentType);
            client = (TextView) view.findViewById(R.id.client);
            status = (TextView) view.findViewById(R.id.appointmentStatus);
            typeImage = (ImageView) view.findViewById(R.id.appointmentTypeImage);
        }
    }


    @Override
    public AppointmentDetailsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appointment_history_child_list_row, parent, false);

        return new AppointmentDetailsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AppointmentDetailsAdapter.MyViewHolder holder, int position) {

        final AppointmentDetailsModel appointment = appointmentDetailsList.get(position);
        holder.time.setText(appointment.getTime());
        holder.type.setText(appointment.getType());
        holder.status.setText(appointment.getStatus());
        holder.client.setText(appointment.getClient());
        if(appointment.getType().equalsIgnoreCase("in person")){
            //Change TypeImage
        }else{
            //Change TypeImage
        }
    }

    @Override
    public int getItemCount()
    {
        return appointmentDetailsList.size();
    }



}