package com.dietitianshreya.dtshreyaadmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dietitianshreya.dtshreyaadmin.adapters.MealChangeAdapter;
import com.dietitianshreya.dtshreyaadmin.models.MealChangeModel;

import java.util.ArrayList;

public class MealChangeRequestActivty extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<MealChangeModel> clientList;
    MealChangeAdapter mealChangeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_change_request);
        recyclerView = (RecyclerView) findViewById(R.id.re);
        clientList = new ArrayList<>();
        mealChangeAdapter = new MealChangeAdapter(clientList,MealChangeRequestActivty.this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setAdapter(mealChangeAdapter);
        recyclerView.setLayoutManager(manager);
        clientList.add(new MealChangeModel("Akshit Tyagi","12345","poha","corn flakes","Breakfast","awaited","10 A.M.","4 days left"));
        clientList.add(new MealChangeModel("Akshit Tyagi","12345","poha","corn flakes","Breakfast","awaited","10 A.M.","4 days left"));
        clientList.add(new MealChangeModel("Akshit Tyagi","12345","poha","corn flakes","Breakfast","awaited","10 A.M.","4 days left"));
        clientList.add(new MealChangeModel("Akshit Tyagi","12345","poha","corn flakes","Breakfast","awaited","10 A.M.","4 days left"));
        clientList.add(new MealChangeModel("Akshit Tyagi","12345","poha","corn flakes","Breakfast","awaited","10 A.M.","4 days left"));
        clientList.add(new MealChangeModel("Akshit Tyagi","12345","poha","corn flakes","Breakfast","awaited","10 A.M.","4 days left"));
        clientList.add(new MealChangeModel("Akshit Tyagi","12345","poha","corn flakes","Breakfast","awaited","10 A.M.","4 days left"));
        clientList.add(new MealChangeModel("Akshit Tyagi","12345","poha","corn flakes","Breakfast","awaited","10 A.M.","4 days left"));
        mealChangeAdapter.notifyDataSetChanged();
    }

}
