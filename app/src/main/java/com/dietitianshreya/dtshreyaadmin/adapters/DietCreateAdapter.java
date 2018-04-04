package com.dietitianshreya.dtshreyaadmin.adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dietitianshreya.dtshreyaadmin.ChooseMealActivity;
import com.dietitianshreya.dtshreyaadmin.R;
import com.dietitianshreya.dtshreyaadmin.models.DietPlanModel;
import com.dietitianshreya.dtshreyaadmin.models.MealModel;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


public class DietCreateAdapter extends RecyclerView.Adapter<DietCreateAdapter.MyViewHolder> {

    private ArrayList<DietPlanModel> dietPlanList;
    private Context mCtx;
    int defaultPos =0;


    public DietCreateAdapter(ArrayList<DietPlanModel> dietPlanList, Context mCtx) {
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
    public DietCreateAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.diet_create_list_row, parent, false);

        return new DietCreateAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DietCreateAdapter.MyViewHolder holder, final int position) {

        final DietPlanModel diet = dietPlanList.get(position);
        if(position!=defaultPos) {
            holder.DietChild.setVisibility(View.GONE);
            holder.recyclerView.setVisibility(View.GONE);
//            holder.groupIndicator.setImageResource(R.drawable.ic_chat);
        }
        else{
//            holder.groupIndicator.setImageResource(R.drawable.ic_chat);
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
        holder.dietRowHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.DietChild.getVisibility()==View.GONE) {
//                    holder.groupIndicator.setImageResource(R.drawable.ic_chat);
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
//                    holder.groupIndicator.setImageResource(R.drawable.ic_chat);
                    holder.DietChild.setVisibility(View.GONE);
                    holder.recyclerView.setVisibility(View.GONE);
                }
            }
        });

        holder.groupIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(mCtx, ChooseMealActivity.class);
                ArrayList<String> diseases= new ArrayList<String>();
                diseases.add("diabetes");
                diseases.add("blood pressure");
                i.putStringArrayListExtra("diseases",diseases);
                ((Activity)mCtx).startActivityForResult(i,position);

            }
        });
    }

    @Override
    public int getItemCount()
    {
        return dietPlanList.size();
    }

    public void onActivityResult(int requestCode,int resultCode,Intent data){
        if(resultCode==RESULT_OK) {
            defaultPos=requestCode;
            final DietPlanModel diet = dietPlanList.get(requestCode);
            ArrayList<MealModel> mealList = diet.getMealList();
            ArrayList<String> name = data.getStringArrayListExtra("name");
            ArrayList<String> quant = data.getStringArrayListExtra("quant");
            for (int i = 0; i <name.size(); i++) {
                mealList.add(new MealModel(name.get(i), quant.get(i), false));
                notifyDataSetChanged();
            }
        }
    }

}