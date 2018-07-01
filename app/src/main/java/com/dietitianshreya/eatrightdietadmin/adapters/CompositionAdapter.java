package com.dietitianshreya.eatrightdietadmin.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dietitianshreya.eatrightdietadmin.R;
import com.dietitianshreya.eatrightdietadmin.models.CompositionModel;

import java.util.ArrayList;


public class CompositionAdapter extends RecyclerView.Adapter<CompositionAdapter.MyViewHolder> {

    private ArrayList<CompositionModel> compositionList;
    private Context mCtx;


    public CompositionAdapter(ArrayList<CompositionModel> compositionList, Context mCtx) {
        this.compositionList = compositionList;
        this.mCtx = mCtx;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView date,weight,height,bodyage,vfat,sfat,bmi,resMeta,muscle;
        public MyViewHolder(View view) {
            super(view);
            date = (TextView)  view.findViewById(R.id.date);
            weight = (TextView)  view.findViewById(R.id.weight);
            bodyage = (TextView)  view.findViewById(R.id.bodyAge);
            bmi = (TextView)  view.findViewById(R.id.bmi);
            muscle = (TextView)  view.findViewById(R.id.muscle);
            vfat = (TextView)  view.findViewById(R.id.vfat);
            sfat = (TextView)  view.findViewById(R.id.sfat);
            resMeta = (TextView)  view.findViewById(R.id.resMeta);
            height = (TextView) view.findViewById(R.id.height);
        }
    }


    @Override
    public CompositionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.composition_list_row, parent, false);

        return new CompositionAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CompositionAdapter.MyViewHolder holder, final int position) {

        final CompositionModel composition = compositionList.get(position);
        holder.date.setText(composition.getDate());
        holder.sfat.setText(composition.getSfat());
        holder.vfat.setText(composition.getVfat());
        holder.weight.setText(composition.getWeight());
        holder.muscle.setText(composition.getMuscle());
        holder.height.setText(composition.getHeight());
        holder.bmi.setText(composition.getBmi());
        holder.bodyage.setText(composition.getBodyage());
        holder.resMeta.setText(composition.getResmeta());

    }

    @Override
    public int getItemCount()
    {
        return compositionList.size();
    }



}