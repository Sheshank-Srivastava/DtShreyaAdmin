package com.dietitianshreya.dtshreyaadmin;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CompleteProfileActivity extends AppCompatActivity implements MedicalHistoryFragment.OnFragmentInteractionListener
, FoodSpecificationFragment.OnFragmentInteractionListener{

    int MAX_STEP=1,current_step=1;
    TextView stepCount;
    FragmentManager fragmentManager;
    ArrayList<Fragment> fragments;
    ArrayList<String> tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);
        stepCount = (TextView) findViewById(R.id.stepCount);
        tags = new ArrayList<>();
        //add to list of tags from extra
        tags.add("medical history");
        tags.add("food specification");
        MAX_STEP = tags.size();
        Log.d("Size",MAX_STEP+"");
        fragments = new ArrayList<Fragment>();
        //Check for extras to add fragments to the list
        addFragments();
        ((LinearLayout) findViewById(R.id.lyt_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backStep(current_step);
            }
        });

        ((LinearLayout) findViewById(R.id.lyt_next)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextStep(current_step);
            }
        });
        stepCount.setText("Step "+current_step+" of "+MAX_STEP);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container,fragments.get(current_step-1)).commit();
    }

    private void nextStep(int progress) {
        if (progress < MAX_STEP) {
            progress++;
            current_step = progress;
            Log.d("Size",progress+"");
            stepCount.setText("Step " + current_step + " of " + MAX_STEP);
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container, fragments.get(current_step - 1)).commit();
        }
    }

    private void backStep(int progress) {
        if (progress > 1) {
            progress--;
            current_step = progress;
            Log.d("Size",progress+"");
            stepCount.setText("Step " + current_step + " of " + MAX_STEP);
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container, fragments.get(current_step - 1)).commit();
        }
    }

    private void addFragments(){
        if(tags.contains("medical history")){
            fragments.add(new MedicalHistoryFragment());
//            MedicalHistoryFragment frag = (MedicalHistoryFragment)fragments.get(0);
//            frag.getData();
        }
        if(tags.contains("food specification")){
            fragments.add(new FoodSpecificationFragment());
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
