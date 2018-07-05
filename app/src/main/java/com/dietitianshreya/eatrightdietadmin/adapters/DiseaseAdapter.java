package com.dietitianshreya.eatrightdietadmin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dietitianshreya.eatrightdietadmin.R;
import com.dietitianshreya.eatrightdietadmin.models.DiseaseModel;

import java.util.ArrayList;

/**
 * Created by manyamadan on 06/06/18.
 */

public class DiseaseAdapter extends RecyclerView.Adapter<DiseaseAdapter.MyViewHolder> {

    private ArrayList<DiseaseModel> diseaseList;
    private Context mCtx;

    public DiseaseAdapter(ArrayList<DiseaseModel> diseaseList, Context mCtx) {
        this.diseaseList = diseaseList;
        this.mCtx = mCtx;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView placeholder,clientName,clientId,daysRemaining,daysLeft;
        public LinearLayout body;
        public LinearLayout textLayout;
        public ImageView createDiet;
        public MyViewHolder(View view) {
            super(view);
            placeholder = (TextView)  view.findViewById(R.id.placeholder);
            clientName = (TextView)  view.findViewById(R.id.clientName);
            clientId = (TextView)  view.findViewById(R.id.clientId);
            daysRemaining = (TextView) view.findViewById(R.id.daysRemaining);
            body = (LinearLayout) view.findViewById(R.id.bodyLayout);
            textLayout = (LinearLayout) view.findViewById(R.id.textLayout);
            createDiet = (ImageView) view.findViewById(R.id.dietCreate);
            daysLeft = (TextView) view.findViewById(R.id.daysLeft);
        }
    }

    @Override
    public DiseaseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.disease_list_item, parent, false);

        return new DiseaseAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DiseaseAdapter.MyViewHolder holder, int position) {

         final DiseaseModel disease = diseaseList.get(position);
        //holder.placeholder.setText(client.getClientName());
        holder.clientName.setText(disease.getDiseaseName());



    }

    @Override
    public int getItemCount()
    {
        return diseaseList.size();
    }



}