package com.dietitianshreya.eatrightdietadmin.adapters;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dietitianshreya.eatrightdietadmin.R;
import com.dietitianshreya.eatrightdietadmin.models.ReviewsModel;

import java.util.ArrayList;


public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.MyViewHolder> {

    private ArrayList<ReviewsModel> list;
    private Context mCtx;



    public ReviewsAdapter(ArrayList<ReviewsModel> list, Context mCtx) {
        this.list = list;
        this.mCtx = mCtx;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView userName,userComment;
        public LinearLayout readMore;
        public RatingBar ratingClient;
        public MyViewHolder(View view) {
            super(view);
            userName = (TextView)  view.findViewById(R.id.userName);
            userComment = (TextView)  view.findViewById(R.id.userComment);
            readMore = (LinearLayout)  view.findViewById(R.id.readMore);
            ratingClient = view.findViewById(R.id.ratingClient);
        }
    }


    @Override
    public ReviewsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comments_list_row, parent, false);

        return new ReviewsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReviewsAdapter.MyViewHolder holder, final int position) {

        final ReviewsModel review = list.get(position);
        holder.userName.setText(review.getClientName());
        holder.userComment.setText(review.getComment());
        holder.ratingClient.setRating(review.getRating());
        holder.readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mCtx);
                // Setting Dialog Message
                alertDialog.setTitle("Review by "+review.getClientName());
                alertDialog.setMessage(review.getComment());
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.show();
//                Toast.makeText(mCtx,"You Clicked "+meal.getMealHead(),Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }



}