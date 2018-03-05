package com.dietitianshreya.dtshreyaadmin.adapters;


import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dietitianshreya.dtshreyaadmin.DividerItemDecoration;
import com.dietitianshreya.dtshreyaadmin.R;
import com.dietitianshreya.dtshreyaadmin.models.AllClientsCompoundModel;
import com.dietitianshreya.dtshreyaadmin.models.AppointmentHistoryModel;

import java.util.ArrayList;


public class AllClientsCompoundAdapter extends RecyclerView.Adapter<AllClientsCompoundAdapter.MyViewHolder> {

    private ArrayList<AllClientsCompoundModel> compoundList;
    private Context mCtx;


    public AllClientsCompoundAdapter(ArrayList<AllClientsCompoundModel> compoundList, Context mCtx) {
        this.compoundList = compoundList;
        this.mCtx = mCtx;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView head;
        public RecyclerView recyclerView;


        public MyViewHolder(View view) {
            super(view);
            head = (TextView) view.findViewById(R.id.head);
            recyclerView = (RecyclerView) view.findViewById(R.id.re);
        }
    }


    @Override
    public AllClientsCompoundAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_clients_compound_list_row, parent, false);

        return new AllClientsCompoundAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AllClientsCompoundAdapter.MyViewHolder holder, int position) {

        final AllClientsCompoundModel header = compoundList.get(position);
        holder.head.setText(header.getHead());

        AllClientListOthersAdapter detailsAdapter = new AllClientListOthersAdapter(header.getClientList(),mCtx);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mCtx);
        holder.recyclerView.addItemDecoration(new DividerItemDecoration(mCtx, LinearLayoutManager.VERTICAL));
        holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
        holder.recyclerView.setLayoutManager(mLayoutManager);
        holder.recyclerView.setAdapter(detailsAdapter);
        detailsAdapter.notifyDataSetChanged();

    }

    @Override
    public int getItemCount()
    {
        return compoundList.size();
    }



}