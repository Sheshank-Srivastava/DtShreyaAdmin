package com.dietitianshreya.eatrightdietadmin.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dietitianshreya.eatrightdietadmin.R;

import java.util.ArrayList;


public class ClinicalNotesAdapter extends RecyclerView.Adapter<ClinicalNotesAdapter.MyViewHolder> {

    private ArrayList<String> notes;
    private Context mCtx;


    public ClinicalNotesAdapter(ArrayList<String> notes, Context mCtx) {
        this.notes = notes;
        this.mCtx = mCtx;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView notesText;
        public MyViewHolder(View view) {
            super(view);
            notesText = (TextView)  view.findViewById(R.id.notesText);
        }
    }


    @Override
    public ClinicalNotesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.clinical_notes_row, parent, false);

        return new ClinicalNotesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ClinicalNotesAdapter.MyViewHolder holder, int position) {


        holder.notesText.setText(notes.get(position));
    }

    @Override
    public int getItemCount()
    {
        return notes.size();
    }



}