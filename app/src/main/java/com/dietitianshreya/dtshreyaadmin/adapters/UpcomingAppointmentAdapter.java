package com.dietitianshreya.dtshreyaadmin.adapters;


import android.content.Context;
import android.media.Image;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dietitianshreya.dtshreyaadmin.R;
import com.dietitianshreya.dtshreyaadmin.models.AppointmentDetailsModel;

import java.util.ArrayList;


public class UpcomingAppointmentAdapter extends RecyclerView.Adapter<UpcomingAppointmentAdapter.MyViewHolder> {

    private ArrayList<AppointmentDetailsModel> appointmentDetailsList;
    private Context mCtx;


    public UpcomingAppointmentAdapter(ArrayList<AppointmentDetailsModel> appointmentDetailsList, Context mCtx) {
        this.appointmentDetailsList = appointmentDetailsList;
        this.mCtx = mCtx;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView time,type,client;
        public ImageView typeImage,status;

        public MyViewHolder(View view) {
            super(view);
            time = (TextView)  view.findViewById(R.id.appointmentTime);
            type = (TextView)  view.findViewById(R.id.appointmentType);
            client = (TextView) view.findViewById(R.id.client);
            status = (ImageView) view.findViewById(R.id.optionsMenu);
            typeImage = (ImageView) view.findViewById(R.id.appointmentTypeImage);
        }
    }


    @Override
    public UpcomingAppointmentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.upcoming_appointment_list_row, parent, false);

        return new UpcomingAppointmentAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UpcomingAppointmentAdapter.MyViewHolder holder, int position) {

        final AppointmentDetailsModel appointment = appointmentDetailsList.get(position);
        holder.time.setText(appointment.getTime());
        holder.type.setText(appointment.getType());
        holder.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(mCtx,view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.appointments_menu,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(mCtx,item.getTitle(),Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
            }
        });

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