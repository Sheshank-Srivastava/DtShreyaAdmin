package com.dietitianshreya.eatrightdietadmin.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dietitianshreya.eatrightdietadmin.ClientDetailActivity;
import com.dietitianshreya.eatrightdietadmin.CreateDiet;
import com.dietitianshreya.eatrightdietadmin.R;
import com.dietitianshreya.eatrightdietadmin.models.AllClientListOthersModel;

import java.util.ArrayList;


public class AllClientListOthersAdapter extends RecyclerView.Adapter<AllClientListOthersAdapter.MyViewHolder> {

    private ArrayList<AllClientListOthersModel> clientList;
    private Context mCtx;

    public AllClientListOthersAdapter(ArrayList<AllClientListOthersModel> clientList, Context mCtx) {
        this.clientList = clientList;
        this.mCtx = mCtx;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView placeholder,clientName,clientId,daysRemaining,daysLeft;
        public LinearLayout body;
        public LinearLayout textLayout;
        public ImageView createDiet;
        public MyViewHolder(View view) {
            super(view);
            placeholder = (TextView)  view.findViewById(R.id.placeholder);
            clientName = (TextView)  view.findViewById(R.id.clientName);
            clientId = (TextView)  view.findViewById(R.id.clientId);
            daysRemaining = (TextView) view.findViewById(R.id.daysRemaining);
            body = (LinearLayout) view.findViewById(R.id.bodyLayout);
            textLayout = (LinearLayout) view.findViewById(R.id.textLayout);
            createDiet = (ImageView) view.findViewById(R.id.dietCreate);
            daysLeft = (TextView) view.findViewById(R.id.daysLeft);
        }
    }

    @Override
    public AllClientListOthersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_client_list_row_others, parent, false);

        return new AllClientListOthersAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AllClientListOthersAdapter.MyViewHolder holder, int position) {

        final AllClientListOthersModel client = clientList.get(position);
        //holder.placeholder.setText(client.getClientName());
        holder.clientName.setText(client.getClientName());
        holder.daysLeft.setText(client.getDaysLeft());
        holder.clientId.setText(client.getClientId());
        char placeholdertext = client.getClientName().charAt(0);
        holder.placeholder.setText(placeholdertext+"");
        try{
        if(Integer.parseInt(client.getDays())<=3){
            holder.textLayout.setVisibility(View.GONE);
            holder.createDiet.setVisibility(View.VISIBLE);
            holder.createDiet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Send Intent to diet creation activity
                    Intent i = new Intent(mCtx, CreateDiet.class);
                    i.putExtra("clientId",client.getClientId());
                    mCtx.startActivity(i);
                }
            });

        }
        else {
            holder.textLayout.setVisibility(View.VISIBLE);
            holder.daysRemaining.setText(client.getDays() + " Days");
            holder.createDiet.setVisibility(View.GONE);
        }
        }catch(NumberFormatException e) {
            if (client.getDays().equals("na")) {
                holder.textLayout.setVisibility(View.GONE);
                holder.createDiet.setVisibility(View.GONE);
            }
        }
        holder.body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send intent to customer details class
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