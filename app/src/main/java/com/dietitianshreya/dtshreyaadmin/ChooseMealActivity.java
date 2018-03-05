package com.dietitianshreya.dtshreyaadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dietitianshreya.dtshreyaadmin.adapters.ChooseMealAdapter;
import com.dietitianshreya.dtshreyaadmin.adapters.ClientListAdapter;
import com.dietitianshreya.dtshreyaadmin.models.ChooseMealModel;
import com.dietitianshreya.dtshreyaadmin.models.ClientListModel;

import java.util.ArrayList;

public class ChooseMealActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button add;
    ArrayList<ChooseMealModel> mealList;
    ChooseMealAdapter mealAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_meal);
        recyclerView = (RecyclerView) findViewById(R.id.re);
        mealList=new ArrayList<>();
        mealAdapter = new ChooseMealAdapter(mealList,ChooseMealActivity.this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ChooseMealActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(ChooseMealActivity.this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mealAdapter);
        mealList.add(new ChooseMealModel("Poha","0"));
        mealList.add(new ChooseMealModel("Rice","0"));
        mealList.add(new ChooseMealModel("Dahi","0"));
        mealList.add(new ChooseMealModel("Dal","0"));
        mealList.add(new ChooseMealModel("Pepsi","0"));
        mealList.add(new ChooseMealModel("Juice","0"));
        mealList.add(new ChooseMealModel("Ice Cream","0"));
        mealList.add(new ChooseMealModel("Milk","0"));
        mealAdapter.notifyDataSetChanged();
        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ArrayList<ChooseMealModel> list = mealAdapter.getMealList();
                if(list.size()==0){
                    Toast.makeText(getApplicationContext(),"Please select some items",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent returnIntent = new Intent();
                    ArrayList<String> name = new ArrayList<String>();
                    ArrayList<String> quant = new ArrayList<String>();
                    for(int i=0;i<list.size();i++){
                        name.add(list.get(i).getMealName());
                        quant.add(list.get(i).getMealQuant());
                    }
                    returnIntent.putStringArrayListExtra("name",name);
                    returnIntent.putStringArrayListExtra("quant",quant);
                    setResult(RESULT_OK,returnIntent);
                    finish();
                }
            }
        });
    }
}
