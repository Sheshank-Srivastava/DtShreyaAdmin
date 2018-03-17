package com.dietitianshreya.dtshreyaadmin.adapters;


import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dietitianshreya.dtshreyaadmin.R;
import com.dietitianshreya.dtshreyaadmin.models.ClientListModel;
import com.dietitianshreya.dtshreyaadmin.models.MealModel;

import java.util.ArrayList;


public class SignupRequestAdapter extends RecyclerView.Adapter<SignupRequestAdapter.MyViewHolder> {

    private ArrayList<ClientListModel> clientList;
    private Context mCtx;


    public SignupRequestAdapter(ArrayList<ClientListModel> clientList, Context mCtx) {
        this.clientList = clientList;
        this.mCtx = mCtx;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView clientName,placeholder;
        public ImageView confirm;
        public MyViewHolder(View view) {
            super(view);
            clientName = (TextView)  view.findViewById(R.id.clientName);
            placeholder = (TextView)  view.findViewById(R.id.placeholder);
            confirm = (ImageView) view.findViewById(R.id.confirm);
        }
    }


    @Override
    public SignupRequestAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.signup_request_list_row, parent, false);

        return new SignupRequestAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SignupRequestAdapter.MyViewHolder holder, int position) {

        final ClientListModel client = clientList.get(position);
        holder.clientName.setText(client.getClientName());
        char placeholdertext = client.getClientName().charAt(0);
        holder.placeholder.setText(placeholdertext+"");
        holder.clientName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        holder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount()
    {
        return clientList.size();
    }



}