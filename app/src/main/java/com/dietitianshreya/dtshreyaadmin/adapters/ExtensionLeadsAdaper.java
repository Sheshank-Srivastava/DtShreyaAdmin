package com.dietitianshreya.dtshreyaadmin.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dietitianshreya.dtshreyaadmin.ChatActivity;
import com.dietitianshreya.dtshreyaadmin.R;
import com.dietitianshreya.dtshreyaadmin.models.ExtensionLeadsModel;
import com.dietitianshreya.dtshreyaadmin.models.RescheduleAppointmentModel;

import java.util.ArrayList;


public class ExtensionLeadsAdaper extends RecyclerView.Adapter<ExtensionLeadsAdaper.MyViewHolder> {

    private ArrayList<ExtensionLeadsModel> leadsList;
    private Context mCtx;


    public ExtensionLeadsAdaper(ArrayList<ExtensionLeadsModel> leadsList, Context mCtx) {
        this.leadsList = leadsList;
        this.mCtx = mCtx;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView clientName,clientId,joinedOn,daysPassed,plan;
        LinearLayout extend,remove;
        LinearLayout chat;
        public MyViewHolder(View view) {
            super(view);
            clientName = (TextView)  view.findViewById(R.id.clientName);
            clientId = (TextView)  view.findViewById(R.id.clientId);
            joinedOn = (TextView)  view.findViewById(R.id.joinedOn);
            daysPassed = (TextView)  view.findViewById(R.id.daysPassed);
            plan = (TextView)  view.findViewById(R.id.plan);
            extend = (LinearLayout)  view.findViewById(R.id.extend);
            remove = (LinearLayout)  view.findViewById(R.id.remove);
            chat = (LinearLayout) view.findViewById(R.id.chat);
        }
    }


    @Override
    public ExtensionLeadsAdaper.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.extension_leads_list_row, parent, false);

        return new ExtensionLeadsAdaper.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExtensionLeadsAdaper.MyViewHolder holder, final int position) {

        final ExtensionLeadsModel lead = leadsList.get(position);
        holder.clientName.setText(lead.getClientName());
        holder.clientId.setText(lead.getClientId());
        holder.joinedOn.setText(lead.getJoinedOn());
        holder.daysPassed.setText(lead.getDaysPassed());
        holder.plan.setText(lead.getPlan());
        holder.extend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mCtx,"Extend!",Toast.LENGTH_SHORT).show();
            }
        });
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mCtx,"Remove!",Toast.LENGTH_SHORT).show();
                leadsList.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mCtx, ChatActivity.class);
                i.putExtra("clientId",lead.getClientId());
                mCtx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return leadsList.size();
    }



}