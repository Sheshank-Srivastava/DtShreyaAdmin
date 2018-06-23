package com.dietitianshreya.dtshreyaadmin;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dietitianshreya.dtshreyaadmin.adapters.AllClientsCompoundAdapter;
import com.dietitianshreya.dtshreyaadmin.adapters.ReviewsAdapter;
import com.dietitianshreya.dtshreyaadmin.models.ReviewsModel;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

import static com.dietitianshreya.dtshreyaadmin.Login.MyPREFERENCES;

public class DietitianProfileReviews extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ReviewsModel> list;
    ReviewsAdapter adapter;
    TextView name,avgRating;
    CircularImageView image;
    RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietitian_profile_reviews);
        recyclerView = (RecyclerView)findViewById(R.id.re);
        name = (TextView) findViewById(R.id.name);
        avgRating = (TextView) findViewById(R.id.avgRating);
        image = (CircularImageView) findViewById(R.id.image);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        list=new ArrayList<>();
        adapter = new ReviewsAdapter(list,this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        list.add(new ReviewsModel(3.5f,"Akshit tyagi(12345)","dfjhgfjdgsjfhhsdcbdcbdhfgdhfgkfdgshfdshkfbhfbehfvsfhskhfdvsjfbshfsdvhcvdsbfhdsvfhdgsfvgshfvsafvdsghkvjfdsfvsdhkfvdshfasfhsdvaf"));
        list.add(new ReviewsModel(3.5f,"Akshit tyagi(12345)","dfjhgfjdgsjfhhsdcbdcbdhfgdhfgkfdgshfdshkfbhfbehfvsfhskhfdvsjfbshfsdvhcvdsbfhdsvfhdgsfvgshfvsafvdsghkvjfdsfvsdhkfvdshfasfhsdvaf"));
        list.add(new ReviewsModel(3.5f,"Akshit tyagi(12345)","dfjhgfjdgsjfhhsdcbdcbdhfgdhfgkfdgshfdshkfbhfbehfvsfhskhfdvsjfbshfsdvhcvdsbfhdsvfhdgsfvgshfvsafvdsghkvjfdsfvsdhkfvdshfasfhsdvaf"));
        list.add(new ReviewsModel(3.5f,"Akshit tyagi(12345)","dfjhgfjdgsjfhhsdcbdcbdhfgdhfgkfdgshfdshkfbhfbehfvsfhskhfdvsjfbshfsdvhcvdsbfhdsvfhdgsfvgshfvsafvdsghkvjfdsfvsdhkfvdshfasfhsdvaf"));
        list.add(new ReviewsModel(3.5f,"Akshit tyagi(12345)","dfjhgfjdgsjfhhsdcbdcbdhfgdhfgkfdgshfdshkfbhfbehfvsfhskhfdvsjfbshfsdvhcvdsbfhdsvfhdgsfvgshfvsafvdsghkvjfdsfvsdhkfvdshfasfhsdvaf"));
        list.add(new ReviewsModel(3.5f,"Akshit tyagi(12345)","dfjhgfjdgsjfhhsdcbdcbdhfgdhfgkfdgshfdshkfbhfbehfvsfhskhfdvsjfbshfsdvhcvdsbfhdsvfhdgsfvgshfvsafvdsghkvjfdsfvsdhkfvdshfasfhsdvaf"));
        list.add(new ReviewsModel(3.5f,"Akshit tyagi(12345)","dfjhgfjdgsjfhhsdcbdcbdhfgdhfgkfdgshfdshkfbhfbehfvsfhskhfdvsjfbshfsdvhcvdsbfhdsvfhdgsfvgshfvsafvdsghkvjfdsfvsdhkfvdshfasfhsdvaf"));
        list.add(new ReviewsModel(3.5f,"Akshit tyagi(12345)","dfjhgfjdgsjfhhsdcbdcbdhfgdhfgkfdgshfdshkfbhfbehfvsfhskhfdvsjfbshfsdvhcvdsbfhdsvfhdgsfvgshfvsafvdsghkvjfdsfvsdhkfvdshfasfhsdvaf"));
        list.add(new ReviewsModel(3.5f,"Akshit tyagi(12345)","dfjhgfjdgsjfhhsdcbdcbdhfgdhfgkfdgshfdshkfbhfbehfvsfhskhfdvsjfbshfsdvhcvdsbfhdsvfhdgsfvgshfvsafvdsghkvjfdsfvsdhkfvdshfasfhsdvaf"));
        list.add(new ReviewsModel(3.5f,"Akshit tyagi(12345)","dfjhgfjdgsjfhhsdcbdcbdhfgdhfgkfdgshfdshkfbhfbehfvsfhskhfdvsjfbshfsdvhcvdsbfhdsvfhdgsfvgshfvsafvdsghkvjfdsfvsdhkfvdshfasfhsdvaf"));
        list.add(new ReviewsModel(3.5f,"Akshit tyagi(12345)","dfjhgfjdgsjfhhsdcbdcbdhfgdhfgkfdgshfdshkfbhfbehfvsfhskhfdvsjfbshfsdvhcvdsbfhdsvfhdgsfvgshfvsafvdsghkvjfdsfvsdhkfvdshfasfhsdvaf"));
        list.add(new ReviewsModel(3.5f,"Akshit tyagi(12345)","dfjhgfjdgsjfhhsdcbdcbdhfgdhfgkfdgshfdshkfbhfbehfvsfhskhfdvsjfbshfsdvhcvdsbfhdsvfhdgsfvgshfvsafvdsghkvjfdsfvsdhkfvdshfasfhsdvaf"));
        list.add(new ReviewsModel(3.5f,"Akshit tyagi(12345)","dfjhgfjdgsjfhhsdcbdcbdhfgdhfgkfdgshfdshkfbhfbehfvsfhskhfdvsjfbshfsdvhcvdsbfhdsvfhdgsfvgshfvsafvdsghkvjfdsfvsdhkfvdshfasfhsdvaf"));
        list.add(new ReviewsModel(3.5f,"Akshit tyagi(12345)","dfjhgfjdgsjfhhsdcbdcbdhfgdhfgkfdgshfdshkfbhfbehfvsfhskhfdvsjfbshfsdvhcvdsbfhdsvfhdgsfvgshfvsafvdsghkvjfdsfvsdhkfvdshfasfhsdvaf"));
        list.add(new ReviewsModel(3.5f,"Akshit tyagi(12345)","dfjhgfjdgsjfhhsdcbdcbdhfgdhfgkfdgshfdshkfbhfbehfvsfhskhfdvsjfbshfsdvhcvdsbfhdsvfhdgsfvgshfvsafvdsghkvjfdsfvsdhkfvdshfasfhsdvaf"));
        adapter.notifyDataSetChanged();

    }
}
