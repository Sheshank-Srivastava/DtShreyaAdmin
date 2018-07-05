package com.dietitianshreya.eatrightdietadmin.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dietitianshreya.eatrightdietadmin.R;
import com.dietitianshreya.eatrightdietadmin.models.ChooseMealModel;

import java.util.ArrayList;


public class ChooseMealAdapter extends RecyclerView.Adapter<ChooseMealAdapter.MyViewHolder>  {

    private ArrayList<ChooseMealModel> mealList;
    private ArrayList<String> diseases;
    private Context mCtx;
    private ArrayList<ChooseMealModel> choosenList;
    private ArrayList<ChooseMealModel> filteredmealList;
    EditText quant;
    int searchFlag =0;
    ChooseMealAdapter mealAdapter;


    public ChooseMealAdapter(ArrayList<ChooseMealModel> mealList, Context mCtx,int searchFlag,ChooseMealAdapter mealAdapter) {
        this.mealList = mealList;
        this.mCtx = mCtx;
        choosenList = new ArrayList<>();
        this.filteredmealList = mealList;
        this.searchFlag=searchFlag;
        this.mealAdapter=mealAdapter;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView name;
        public ImageView img;
        public Boolean kvp;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)  view.findViewById(R.id.name);
            img = (ImageView)  view.findViewById(R.id.img);
        }
    }


    @Override
    public ChooseMealAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.choose_meal_list_row, parent, false);

        return new ChooseMealAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ChooseMealAdapter.MyViewHolder holder, int position) {

        final ChooseMealModel meal = mealList.get(position);
        holder.name.setText(meal.getMealName());


        if(meal.getMealQuant().equals("")){
            holder.img.setImageResource(R.drawable.ic_tick);
            holder.img.setTag("tick");
        }else{
            holder.img.setImageResource(R.drawable.ic_add);
            holder.img.setTag("add");

        }
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
    if(holder.img.getTag().equals("add") ) {

    Log.d("lol","i am here");
    if (meal.getExempted() == 0) {
        choosenList.add(new ChooseMealModel(meal.getMealName(), meal.getMealQuant()+"", 0));
        Log.d("manya",meal.getMealName()+ choosenList.size()+"   "+meal.getMealQuant());
        meal.setMealQuant("");
        //set right image
        holder.img.setImageResource(R.drawable.ic_tick);
        holder.img.setTag("tick");
    } else {
        Toast.makeText(mCtx, "This food is exempted according to medical history", Toast.LENGTH_SHORT).show();
    }
        if(searchFlag==1){
            mealAdapter.appendList(choosenList);
        }
}
//                        }
//                    }
//                });
//                alertDialog.show();
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return filteredmealList.size();
    }

    public ArrayList<ChooseMealModel> getMealList(){
        return choosenList;
    }

    public void appendList(ArrayList<ChooseMealModel> list){
        for(int i=0;i<list.size();i++) {
            this.choosenList.add(list.get(i));
        }
    }




    }
