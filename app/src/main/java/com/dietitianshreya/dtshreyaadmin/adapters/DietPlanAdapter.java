package com.dietitianshreya.dtshreyaadmin.adapters;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dietitianshreya.dtshreyaadmin.R;
import com.dietitianshreya.dtshreyaadmin.models.DietPlanModel;

import java.util.ArrayList;


public class DietPlanAdapter extends RecyclerView.Adapter<DietPlanAdapter.MyViewHolder> {

    private ArrayList<DietPlanModel> dietPlanList;
    private Context mCtx;


    public DietPlanAdapter(ArrayList<DietPlanModel> dietPlanList, Context mCtx) {
        this.dietPlanList = dietPlanList;
        this.mCtx = mCtx;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView dietRowHead;
        public LinearLayout DietChild,headerLayout;
        public RecyclerView recyclerView;
        public ImageView groupIndicator;

        public MyViewHolder(View view) {
            super(view);
            dietRowHead = (TextView)  view.findViewById(R.id.dietRowHead);
            DietChild = (LinearLayout) view.findViewById(R.id.DietChild);
            recyclerView = (RecyclerView) view.findViewById(R.id.re);
            headerLayout = (LinearLayout) view.findViewById(R.id.headerLayout);
            groupIndicator = (ImageView) view.findViewById(R.id.grpIndicator);
        }
    }


    @Override
    public DietPlanAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.diet_list_row, parent, false);

        return new DietPlanAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DietPlanAdapter.MyViewHolder holder, int position) {

        final DietPlanModel diet = dietPlanList.get(position);
        if(position!=0) {
            holder.DietChild.setVisibility(View.GONE);
            holder.recyclerView.setVisibility(View.GONE);
            holder.groupIndicator.setImageResource(R.drawable.ic_chat);
        }
        else{
            holder.groupIndicator.setImageResource(R.drawable.ic_chat);
            holder.DietChild.setVisibility(View.VISIBLE);
            holder.recyclerView.setVisibility(View.VISIBLE);
            MealAdapter mealAdapter = new MealAdapter(diet.getMealList(),mCtx);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(mCtx);
//                    holder.recyclerView.addItemDecoration(new DividerItemDecoration(mCtx, LinearLayoutManager.VERTICAL));
//                    holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
            holder.recyclerView.setLayoutManager(mLayoutManager);
            holder.recyclerView.setAdapter(mealAdapter);
            mealAdapter.notifyDataSetChanged();
        }
        holder.dietRowHead.setText(diet.getTitle());
        holder.headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.DietChild.getVisibility()==View.GONE) {
                    holder.groupIndicator.setImageResource(R.drawable.ic_chat);
                    holder.DietChild.setVisibility(View.VISIBLE);
                    holder.recyclerView.setVisibility(View.VISIBLE);
                    MealAdapter mealAdapter = new MealAdapter(diet.getMealList(),mCtx);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(mCtx);
//                    holder.recyclerView.addItemDecoration(new DividerItemDecoration(mCtx, LinearLayoutManager.VERTICAL));
//                    holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
                    holder.recyclerView.setLayoutManager(mLayoutManager);
                    holder.recyclerView.setAdapter(mealAdapter);
                    mealAdapter.notifyDataSetChanged();
                }
                else {
                    holder.groupIndicator.setImageResource(R.drawable.ic_chat);
                    holder.DietChild.setVisibility(View.GONE);
                    holder.recyclerView.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return dietPlanList.size();
    }



}