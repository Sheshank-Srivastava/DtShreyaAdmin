package com.dietitianshreya.dtshreyaadmin.adapters;


import android.content.Context;
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
import com.dietitianshreya.dtshreyaadmin.models.AppointmentsModel;

import java.util.ArrayList;


public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.MyViewHolder> {

    private ArrayList<AppointmentsModel> appointmentList;
    private Context mCtx;


    public AppointmentsAdapter(ArrayList<AppointmentsModel> appointmentList, Context mCtx) {
        this.appointmentList = appointmentList;
        this.mCtx = mCtx;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView title,time,place,daysLeft;
        public ImageView optionsMenu;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView)  view.findViewById(R.id.title);
            time = (TextView)  view.findViewById(R.id.time);
            place = (TextView)  view.findViewById(R.id.place);
            optionsMenu= (ImageView) view.findViewById(R.id.optionsMenu);
            daysLeft = (TextView) view.findViewById(R.id.daysLeft);
        }
    }


    @Override
    public AppointmentsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appointment_list_row, parent, false);

        return new AppointmentsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AppointmentsAdapter.MyViewHolder holder, int position) {

        final AppointmentsModel appointment = appointmentList.get(position);
        holder.title.setText(appointment.getTitle());
        holder.time.setText(appointment.getTime());
        holder.daysLeft.setText(appointment.getDaysLeft());
        holder.place.setText(appointment.getPlace());
        holder.optionsMenu.setOnClickListener(new View.OnClickListener() {
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

    }

    @Override
    public int getItemCount()
    {
        return appointmentList.size();
    }



}