package com.dietitianshreya.dtshreyaadmin.adapters;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dietitianshreya.dtshreyaadmin.R;
import com.dietitianshreya.dtshreyaadmin.models.ChooseMealModel;
import com.dietitianshreya.dtshreyaadmin.models.MealModel;

import java.util.ArrayList;


public class ChooseMealAdapter extends RecyclerView.Adapter<ChooseMealAdapter.MyViewHolder> {

    private ArrayList<ChooseMealModel> mealList;
    private Context mCtx;
    private ArrayList<ChooseMealModel> choosenList;
    EditText quant;


    public ChooseMealAdapter(ArrayList<ChooseMealModel> mealList, Context mCtx) {
        this.mealList = mealList;
        this.mCtx = mCtx;
        choosenList = new ArrayList<>();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView name;
        public ImageView img;
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
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mCtx,"You Clicked "+meal.getMealHead(),Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mCtx);
                // Get the layout inflater
                LayoutInflater linf = LayoutInflater.from(mCtx);
                final View inflator = linf.inflate(R.layout.custom_dialog, null);
                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout

                // Setting Dialog Title
                //alertDialog.setTitle("Confirm Save...");

                // Setting Dialog Message
                alertDialog.setMessage("Update the quantity");
                // Setting Icon to Dialog
                //alertDialog.setIcon(R.drawable.saveicon);
                alertDialog.setView(inflator);
                quant = (EditText) inflator.findViewById(R.id.quant);
                alertDialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!(TextUtils.isEmpty(quant.getText().toString().trim()))){
                            choosenList.add(new ChooseMealModel(meal.getMealName(),quant.getText().toString().trim()));
                            meal.setMealQuant(quant.getText().toString().trim());
                            //set right image
                            holder.img.setImageResource(R.drawable.ic_chat);
                        }
                    }
                });
                alertDialog.show();
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return mealList.size();
    }

    public ArrayList<ChooseMealModel> getMealList(){
        return choosenList;
    }


}