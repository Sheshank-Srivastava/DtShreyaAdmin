package com.dietitianshreya.eatrightdietadmin.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.dietitianshreya.eatrightdietadmin.R;

import java.util.ArrayList;


public class DietNotesAdapter extends RecyclerView.Adapter<DietNotesAdapter.MyViewHolder> {

    private ArrayList<String> notes;
    private Context mCtx;


    public DietNotesAdapter(ArrayList<String> notes, Context mCtx) {
        this.notes = notes;
        this.mCtx = mCtx;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView note;
        public ImageView delete;
        public MyViewHolder(View view) {
            super(view);
            note = (TextView)  view.findViewById(R.id.note);
            delete = view.findViewById(R.id.delete_note);
        }
    }


    @Override
    public DietNotesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.diet_notes_list_row, parent, false);

        return new DietNotesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DietNotesAdapter.MyViewHolder holder, final int position) {


        holder.note.setText(notes.get(position));

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notes.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return notes.size();
    }

    public ArrayList<String> getNotes()
    {
        return notes;
    }



}