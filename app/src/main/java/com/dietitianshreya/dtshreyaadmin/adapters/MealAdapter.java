package com.dietitianshreya.dtshreyaadmin.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dietitianshreya.dtshreyaadmin.R;
import com.dietitianshreya.dtshreyaadmin.models.MealModel;

import java.util.ArrayList;


public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MyViewHolder> {

    private ArrayList<MealModel> mealList;
    private Context mCtx;



    public MealAdapter(ArrayList<MealModel> mealList,Context mCtx) {
        this.mealList = mealList;
        this.mCtx = mCtx;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView mealHead,mealDesc;
        ImageView cross;
        public MyViewHolder(View view) {
            super(view);
            mealHead = (TextView)  view.findViewById(R.id.mealHeader);
            mealDesc = (TextView)  view.findViewById(R.id.mealDetail);
            cross = view.findViewById(R.id.cross);


        }
    }


    @Override
    public MealAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meal_list_row, parent, false);

        return new MealAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MealAdapter.MyViewHolder holder, final int position) {

        final MealModel meal = mealList.get(position);
        holder.mealHead.setText(meal.getMealHead());
        holder.mealHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mCtx,"You Clicked "+meal.getMealHead(),Toast.LENGTH_SHORT).show();
            }
        });

        holder.mealDesc.setText(meal.getMealDesc());

        holder.cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return mealList.size();
    }



}