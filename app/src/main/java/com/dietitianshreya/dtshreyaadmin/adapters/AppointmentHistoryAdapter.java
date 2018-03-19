package com.dietitianshreya.dtshreyaadmin.adapters;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dietitianshreya.dtshreyaadmin.R;
import com.dietitianshreya.dtshreyaadmin.models.AppointmentHistoryModel;

import java.util.ArrayList;


public class AppointmentHistoryAdapter extends RecyclerView.Adapter<AppointmentHistoryAdapter.MyViewHolder> {

    private ArrayList<AppointmentHistoryModel> appointmentHistoryList;
    private Context mCtx;


    public AppointmentHistoryAdapter(ArrayList<AppointmentHistoryModel> appointmentHistoryList, Context mCtx) {
        this.appointmentHistoryList = appointmentHistoryList;
        this.mCtx = mCtx;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView date;
        public LinearLayout parentLayout,childLayout;
        public RecyclerView recyclerView;
        public ImageView groupIndicator,appointmentTypeImage;


        public MyViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.appointmentDate);
            recyclerView = (RecyclerView) view.findViewById(R.id.childRe);
            groupIndicator = (ImageView) view.findViewById(R.id.grpIndicator);
            parentLayout = (LinearLayout) view.findViewById(R.id.parentLayout);
            childLayout = (LinearLayout) view.findViewById(R.id.childLayout);
            appointmentTypeImage = (ImageView) view.findViewById(R.id.appointmentTypeImage);
        }
    }


    @Override
    public AppointmentHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appointment_history_list_row, parent, false);

        return new AppointmentHistoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AppointmentHistoryAdapter.MyViewHolder holder, int position) {

        final AppointmentHistoryModel header = appointmentHistoryList.get(position);
        holder.childLayout.setVisibility(View.GONE);
        holder.recyclerView.setVisibility(View.GONE);
        holder.groupIndicator.setImageResource(R.drawable.ic_down);
        holder.date.setText(header.getDate());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.childLayout.getVisibility()==View.GONE) {
                    holder.groupIndicator.setImageResource(R.drawable.ic_up);
                    holder.childLayout.setVisibility(View.VISIBLE);
                    holder.recyclerView.setVisibility(View.VISIBLE);
                    AppointmentDetailsAdapter detailsAdapter = new AppointmentDetailsAdapter(header.getAppointmentDetailsList(),mCtx);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(mCtx);
//                    holder.recyclerView.addItemDecoration(new DividerItemDecoration(mCtx, LinearLayoutManager.VERTICAL));
//                    holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
                    holder.recyclerView.setLayoutManager(mLayoutManager);
                    holder.recyclerView.setAdapter(detailsAdapter);
                    detailsAdapter.notifyDataSetChanged();
                }
                else {
                    holder.groupIndicator.setImageResource(R.drawable.ic_down);
                    holder.childLayout.setVisibility(View.GONE);
                    holder.recyclerView.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return appointmentHistoryList.size();
    }



}