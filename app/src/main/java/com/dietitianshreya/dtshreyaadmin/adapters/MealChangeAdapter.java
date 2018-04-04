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
import com.dietitianshreya.dtshreyaadmin.ClientDetailActivity;
import com.dietitianshreya.dtshreyaadmin.CreateDiet;
import com.dietitianshreya.dtshreyaadmin.MealChangeActivity;
import com.dietitianshreya.dtshreyaadmin.R;
import com.dietitianshreya.dtshreyaadmin.models.AllClientListOthersModel;
import com.dietitianshreya.dtshreyaadmin.models.MealChangeModel;

import java.util.ArrayList;


public class MealChangeAdapter extends RecyclerView.Adapter<MealChangeAdapter.MyViewHolder> {

    private ArrayList<MealChangeModel> clientList;
    private Context mCtx;

    public MealChangeAdapter(ArrayList<MealChangeModel> clientList, Context mCtx) {
        this.clientList = clientList;
        this.mCtx = mCtx;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView clientName,previousMeal,requestedMeal,mealType,daysLeft;
        LinearLayout accept,decline;
        LinearLayout chat,profileLayout;
        public MyViewHolder(View view) {
            super(view);
            previousMeal = (TextView)  view.findViewById(R.id.previousMeal);
            clientName = (TextView)  view.findViewById(R.id.clientName);
            requestedMeal = (TextView)  view.findViewById(R.id.requestedMeal);
            mealType = (TextView)  view.findViewById(R.id.mealType);
            accept = (LinearLayout) view.findViewById(R.id.accept);
            decline = (LinearLayout) view.findViewById(R.id.decline);
            chat = (LinearLayout) view.findViewById(R.id.chat);
            daysLeft = (TextView) view.findViewById(R.id.daysLeft);
            profileLayout = (LinearLayout) view.findViewById(R.id.profileLayout);
        }
    }

    @Override
    public MealChangeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meal_change_list_row, parent, false);
        return new MealChangeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MealChangeAdapter.MyViewHolder holder, int position) {

        final MealChangeModel client = clientList.get(position);
        holder.clientName.setText(client.getClientName());
        holder.daysLeft.setText(client.getDaysLeft());
        holder.previousMeal.setText(client.getPreviousMeal());
        holder.mealType.setText(client.getMealType()+" "+client.getMealTime());
        holder.requestedMeal.setText(client.getRequestedMeal());
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
                i.putExtra("clientId",client.getClientId());
                mCtx.startActivity(i);
            }
        });
        holder.profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mCtx, ClientDetailActivity.class);
                i.putExtra("clientID",client.getClientId());
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