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
import com.dietitianshreya.dtshreyaadmin.LastMonthUsers;
import com.dietitianshreya.dtshreyaadmin.R;
import com.dietitianshreya.dtshreyaadmin.models.ExtensionLeadsModel;
import com.dietitianshreya.dtshreyaadmin.models.LastMonthUsersModel;

import java.util.ArrayList;


public class LastMonthUsersAdapter extends RecyclerView.Adapter<LastMonthUsersAdapter.MyViewHolder> {

    private ArrayList<LastMonthUsersModel> clientList;
    private Context mCtx;


    public LastMonthUsersAdapter(ArrayList<LastMonthUsersModel> clientList, Context mCtx) {
        this.clientList = clientList;
        this.mCtx = mCtx;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView clientName,clientId,startedOn,kgsLost,plan;
        LinearLayout sendMail;
        LinearLayout chat;
        public MyViewHolder(View view) {
            super(view);
            clientName = (TextView)  view.findViewById(R.id.clientName);
            clientId = (TextView)  view.findViewById(R.id.clientId);
            startedOn = (TextView)  view.findViewById(R.id.startedOn);
            kgsLost = (TextView)  view.findViewById(R.id.kgLost);
            plan = (TextView)  view.findViewById(R.id.plan);
            sendMail = (LinearLayout)  view.findViewById(R.id.sendMail);
            chat = (LinearLayout) view.findViewById(R.id.chat);
        }
    }


    @Override
    public LastMonthUsersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.last_month_users_list_row, parent, false);

        return new LastMonthUsersAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LastMonthUsersAdapter.MyViewHolder holder, final int position) {

        final LastMonthUsersModel client = clientList.get(position);
        holder.clientName.setText(client.getClientName());
        holder.clientId.setText(client.getClientId());
        holder.startedOn.setText(client.getStartedOn());
        holder.kgsLost.setText(client.getKgLost());
        holder.plan.setText(client.getPlan());
        holder.sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mCtx,"Send mail!",Toast.LENGTH_SHORT).show();
                //Handle mail handling
            }
        });
        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mCtx, ChatActivity.class);
                i.putExtra("clientId",client.getClientId());
                mCtx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return clientList.size();
    }



}