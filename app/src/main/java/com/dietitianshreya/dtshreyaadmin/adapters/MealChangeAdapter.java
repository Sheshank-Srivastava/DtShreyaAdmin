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


        public TextView placeholder,clientName,date;
        public LinearLayout body;
        public ImageView createDiet;
        public MyViewHolder(View view) {
            super(view);
            placeholder = (TextView)  view.findViewById(R.id.placeholder);
            clientName = (TextView)  view.findViewById(R.id.clientName);
            date = (TextView)  view.findViewById(R.id.date);
            body = (LinearLayout) view.findViewById(R.id.bodyLayout);
            createDiet = (ImageView) view.findViewById(R.id.dietCreate);
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
        //holder.placeholder.setText(client.getClientName());
        holder.clientName.setText(client.getClientName());
        holder.date.setText(client.getDate());
        char placeholdertext = client.getClientName().charAt(0);
        holder.placeholder.setText(placeholdertext+"");

        holder.createDiet.setVisibility(View.VISIBLE);
        holder.createDiet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Send Intent to diet creation activity
                    Intent i = new Intent(mCtx, MealChangeActivity.class);
                    i.putExtra("clientId",client.getClientId());
                    i.putExtra("date",client.getDate());
                    mCtx.startActivity(i);
                }
            });

        holder.body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send intent to customer details class
                Intent i = new Intent(mCtx, ClientDetailActivity.class);
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