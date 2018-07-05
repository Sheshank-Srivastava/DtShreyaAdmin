package com.dietitianshreya.eatrightdietadmin.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dietitianshreya.eatrightdietadmin.ChatActivity;
import com.dietitianshreya.eatrightdietadmin.R;
import com.dietitianshreya.eatrightdietadmin.Utils.VariablesModels;
import com.dietitianshreya.eatrightdietadmin.models.ClientListModel;

import java.util.ArrayList;


public class ClientListAdapter extends RecyclerView.Adapter<ClientListAdapter.MyViewHolder> {

    private ArrayList<ClientListModel> clientList;
    private Context mCtx;


    public ClientListAdapter(ArrayList<ClientListModel> clientList, Context mCtx) {
        this.clientList = clientList;
        this.mCtx = mCtx;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView placeholder,clientName,clientId,daysLeft;
        LinearLayout layout;
        public MyViewHolder(View view) {
            super(view);
            placeholder = (TextView)  view.findViewById(R.id.placeholder);
            clientName = (TextView)  view.findViewById(R.id.clientName);
            clientId = (TextView)  view.findViewById(R.id.clientId);
            daysLeft = (TextView) view.findViewById(R.id.daysLeft);
            layout = (LinearLayout) view.findViewById(R.id.body);
        }
    }


    @Override
    public ClientListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.client_list_row, parent, false);

        return new ClientListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ClientListAdapter.MyViewHolder holder, int position) {

        final ClientListModel client = clientList.get(position);
        //holder.placeholder.setText(client.getClientName());
        holder.daysLeft.setText(client.getDaysLeft());
        holder.clientName.setText(client.getClientName());
        holder.clientId.setText(client.getClientId());
        char placeholdertext = client.getClientName().charAt(0);
        holder.placeholder.setText(placeholdertext+"");

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mCtx, ChatActivity.class);
                i.putExtra(VariablesModels.userId,client.getClientId());
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