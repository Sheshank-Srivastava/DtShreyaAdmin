package com.dietitianshreya.dtshreyaadmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.dietitianshreya.dtshreyaadmin.adapters.DiaryViewAdapter;
import com.dietitianshreya.dtshreyaadmin.models.DiaryEntryModel;

import java.util.ArrayList;

public class DiaryViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView date,day,monthYear;
    ArrayList<DiaryEntryModel> diaryList;
    DiaryViewAdapter diaryViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_view);

    }
}
