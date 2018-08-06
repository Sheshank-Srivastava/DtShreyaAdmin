package com.dietitianshreya.eatrightdietadmin.adapters;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dietitianshreya.eatrightdietadmin.ChatActivity;
import com.dietitianshreya.eatrightdietadmin.ClientDetailActivity;
import com.dietitianshreya.eatrightdietadmin.R;
import com.dietitianshreya.eatrightdietadmin.models.LastMonthUsersModel;

import java.util.ArrayList;


public class LastMonthUsersAdapter extends RecyclerView.Adapter<LastMonthUsersAdapter.MyViewHolder> {

    private ArrayList<LastMonthUsersModel> clientList;
    private Context mCtx;


    public LastMonthUsersAdapter(ArrayList<LastMonthUsersModel> clientList, Context mCtx) {
        this.clientList = clientList;
        this.mCtx = mCtx;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView clientName,clientId,startedOn,kgsLost,plan,daysLeft;
        LinearLayout sendMail;
        LinearLayout chat,profileLayout;
        public MyViewHolder(View view) {
            super(view);
            clientName = (TextView)  view.findViewById(R.id.clientName);
            clientId = (TextView)  view.findViewById(R.id.clientId);
            startedOn = (TextView)  view.findViewById(R.id.startedOn);
            kgsLost = (TextView)  view.findViewById(R.id.kgLost);
            plan = (TextView)  view.findViewById(R.id.plan);
            sendMail = (LinearLayout)  view.findViewById(R.id.sendMail);
            chat = (LinearLayout) view.findViewById(R.id.chat);
            daysLeft = (TextView) view.findViewById(R.id.daysLeft);
            profileLayout = (LinearLayout) view.findViewById(R.id.profileLayout);
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
        holder.daysLeft.setText(client.getDaysLeft());
        holder.clientId.setText(client.getClientId());
        holder.startedOn.setText(client.getStartedOn());
        holder.kgsLost.setText(client.getKgLost());
        holder.plan.setText(client.getPlan());
        holder.sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] { client.getEmail() });
                //intent.putExtra(Intent.EXTRA_EMAIL,"hello@medicians.in");
                intent.putExtra(Intent.EXTRA_SUBJECT,"");
                intent.putExtra(Intent.EXTRA_TEXT,"Hello "+client.getClientName()+",\n");

                //startActivity(Intent.createChooser(intent, "Send Email"));
                mCtx.startActivity(intent);
            }
        });
        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mCtx, ChatActivity.class);
                i.putExtra("userId",client.getClientId());
                mCtx.startActivity(i);
            }
        });
        holder.profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mCtx, ClientDetailActivity.class);
                i.putExtra("client",client.getClientId());
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