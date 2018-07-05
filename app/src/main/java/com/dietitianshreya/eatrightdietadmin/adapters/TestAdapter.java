package com.dietitianshreya.eatrightdietadmin.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dietitianshreya.eatrightdietadmin.R;
import com.dietitianshreya.eatrightdietadmin.models.TestHistoryModel;

import java.util.ArrayList;

/**
 * Created by manyamadan on 19/03/18.
 */

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.MyViewHolder> {

    private ArrayList<TestHistoryModel> testHistoryList;
    private Context mCtx;


    public TestAdapter(ArrayList<TestHistoryModel> testHistoryList, Context mCtx) {
        this.testHistoryList = testHistoryList;
        this.mCtx = mCtx;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView date;
        public LinearLayout parentLayout,childLayout;
        public RecyclerView recyclerView;
        public ImageView groupIndicator,appointmentTypeImage;


        public MyViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.testDate);
            recyclerView = (RecyclerView) view.findViewById(R.id.testChildRe);
            groupIndicator = (ImageView) view.findViewById(R.id.grpIndicator);
            parentLayout = (LinearLayout) view.findViewById(R.id.parentLayout);
            childLayout = (LinearLayout) view.findViewById(R.id.childLayout);
            appointmentTypeImage = (ImageView) view.findViewById(R.id.appointmentTypeImage);
        }
    }


    @Override
    public TestAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_history_list_row, parent, false);

        return new TestAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TestAdapter.MyViewHolder holder, int position) {

        final TestHistoryModel header = testHistoryList.get(position);
        holder.groupIndicator.setImageResource(R.drawable.ic_chat);
        holder.childLayout.setVisibility(View.VISIBLE);
        holder.recyclerView.setVisibility(View.VISIBLE);
        TestDetailsAdapter detailsAdapter = new TestDetailsAdapter(header.getTestDetailsList(),mCtx);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mCtx);
//                    holder.recyclerView.addItemDecoration(new DividerItemDecoration(mCtx, LinearLayoutManager.VERTICAL));
//                    holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
        holder.recyclerView.setLayoutManager(mLayoutManager);
        holder.recyclerView.setAdapter(detailsAdapter);
        detailsAdapter.notifyDataSetChanged();
        holder.groupIndicator.setImageResource(R.drawable.ic_down);
        holder.date.setText(header.getDate());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.childLayout.getVisibility()==View.GONE) {
                    holder.groupIndicator.setImageResource(R.drawable.ic_up);
                    holder.childLayout.setVisibility(View.VISIBLE);
                    holder.recyclerView.setVisibility(View.VISIBLE);
                    TestDetailsAdapter detailsAdapter = new TestDetailsAdapter(header.getTestDetailsList(),mCtx);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(mCtx);
//                    holder.recyclerView.addItemDecoration(new DividerItemDecoration(mCtx, LinearLayoutManager.VERTICAL));
//                    holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
                    holder.recyclerView.setLayoutManager(mLayoutManager);
                    holder.recyclerView.setAdapter(detailsAdapter);
                    detailsAdapter.notifyDataSetChanged();
                }
                else {
                    holder.groupIndicator.setImageResource(R.drawable.ic_down);
                    holder.childLayout.setVisibility(View.GONE);
                    holder.recyclerView.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return testHistoryList.size();
    }



}