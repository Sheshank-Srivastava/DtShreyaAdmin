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
import android.widget.TextView;
import android.widget.Toast;

import com.dietitianshreya.dtshreyaadmin.R;
import com.dietitianshreya.dtshreyaadmin.models.ChooseMealModel;
import com.dietitianshreya.dtshreyaadmin.models.DiaryEntryModel;
import com.dietitianshreya.dtshreyaadmin.models.MealModel;

import java.util.ArrayList;


public class DiaryViewAdapter extends RecyclerView.Adapter<DiaryViewAdapter.MyViewHolder> {

    private ArrayList<DiaryEntryModel> diaryEntryList;
    private Context mCtx;
    EditText editText;
    TextView text,dot;


    public DiaryViewAdapter(ArrayList<DiaryEntryModel> diaryEntryList, Context mCtx) {
        this.diaryEntryList = diaryEntryList;
        this.mCtx = mCtx;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView head,body;
        public MyViewHolder(View view) {
            super(view);
            head = (TextView)  view.findViewById(R.id.entryHead);
            body = (TextView)  view.findViewById(R.id.entryBody);
        }
    }


    @Override
    public DiaryViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.diary_grid_item, parent, false);

        return new DiaryViewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DiaryViewAdapter.MyViewHolder holder, int position) {

        final DiaryEntryModel entry = diaryEntryList.get(position);
        holder.head.setText(entry.getHead());
        holder.body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mCtx,"You Clicked "+meal.getMealHead(),Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mCtx);
                // Get the layout inflater
                LayoutInflater linf = LayoutInflater.from(mCtx);
                final View inflator = linf.inflate(R.layout.custom_dialog, null);
                alertDialog.setMessage("Update Information");
                alertDialog.setView(inflator);
                editText = (EditText) inflator.findViewById(R.id.quant);
                editText.setHint("Update the entry information");
                text = (TextView) inflator.findViewById(R.id.text);
                dot = (TextView) inflator.findViewById(R.id.dot);
                text.setVisibility(View.GONE);
                dot.setVisibility(View.GONE);
                alertDialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!(TextUtils.isEmpty(editText.getText().toString().trim()))){
                            entry.setBody(editText.getText().toString().trim());
                            notifyDataSetChanged();
                        }
                    }
                });
                alertDialog.show();
            }
        });

        holder.body.setText(entry.getBody());
    }

    @Override
    public int getItemCount()
    {
        return diaryEntryList.size();
    }



}