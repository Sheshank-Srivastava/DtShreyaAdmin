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
import com.dietitianshreya.dtshreyaadmin.models.CompositionModel;
import com.dietitianshreya.dtshreyaadmin.models.ExtensionLeadsModel;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class CompositionAdapter extends RecyclerView.Adapter<CompositionAdapter.MyViewHolder> {

    private ArrayList<CompositionModel> compositionList;
    private Context mCtx;


    public CompositionAdapter(ArrayList<CompositionModel> compositionList, Context mCtx) {
        this.compositionList = compositionList;
        this.mCtx = mCtx;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView date,weight,fat,water,muscle,bone;
        public MyViewHolder(View view) {
            super(view);
            date = (TextView)  view.findViewById(R.id.date);
            weight = (TextView)  view.findViewById(R.id.weight);
            fat = (TextView)  view.findViewById(R.id.fat);
            water = (TextView)  view.findViewById(R.id.water);
            muscle = (TextView)  view.findViewById(R.id.muscle);
            bone = (TextView)  view.findViewById(R.id.bone);
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
        holder.fat.setText(composition.getFat());
        holder.weight.setText(composition.getWeight());
        holder.muscle.setText(composition.getMuscle());
        holder.water.setText(composition.getWater());
        holder.bone.setText(composition.getBone());

    }

    @Override
    public int getItemCount()
    {
        return compositionList.size();
    }



}