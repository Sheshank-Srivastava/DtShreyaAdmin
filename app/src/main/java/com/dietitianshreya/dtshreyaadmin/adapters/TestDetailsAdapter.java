package com.dietitianshreya.dtshreyaadmin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dietitianshreya.dtshreyaadmin.R;
import com.dietitianshreya.dtshreyaadmin.models.AppointmentDetailsModel;
import com.dietitianshreya.dtshreyaadmin.models.TestDetailModel;

import java.util.ArrayList;

/**
 * Created by manyamadan on 19/03/18.
 */
public class TestDetailsAdapter extends RecyclerView.Adapter<TestDetailsAdapter.MyViewHolder> {

    private ArrayList<TestDetailModel> testDetailsList;
    private Context mCtx;


    public TestDetailsAdapter(ArrayList<TestDetailModel> testDetailsList, Context mCtx) {
        this.testDetailsList = testDetailsList;
        this.mCtx = mCtx;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView time,type,client,testName,daysLeft;
        public ImageView typeImage;
        public MyViewHolder(View view) {
            super(view);
            time = (TextView)  view.findViewById(R.id.testTimings);
            type = (TextView)  view.findViewById(R.id.testType);
            client = (TextView) view.findViewById(R.id.client);
             testName= (TextView) view.findViewById(R.id.testName);
            typeImage = (ImageView) view.findViewById(R.id.appointmentTypeImage);
            daysLeft = (TextView) view.findViewById(R.id.daysLeft);
        }
    }


    @Override
    public TestDetailsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_history_child_list_row, parent, false);

        return new TestDetailsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TestDetailsAdapter.MyViewHolder holder, int position) {

        final TestDetailModel test = testDetailsList.get(position);
        holder.time.setText(test.getTime());
        holder.daysLeft.setText(test.getDaysLeft());
        holder.type.setText(test.getType());
        holder.testName.setText(test.getTest());
        holder.client.setText(test.getClient());

    }

    @Override
    public int getItemCount()
    {
        return testDetailsList.size();
    }



}