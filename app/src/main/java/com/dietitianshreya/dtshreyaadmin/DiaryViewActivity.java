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
        recyclerView = (RecyclerView) findViewById(R.id.re);
        date = (TextView) findViewById(R.id.date);
        day = (TextView) findViewById(R.id.day);
        monthYear = (TextView) findViewById(R.id.monthYear);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(this, 4), true));
        recyclerView.setHasFixedSize(true);

        diaryList = new ArrayList<>();
        diaryViewAdapter = new DiaryViewAdapter(diaryList,DiaryViewActivity.this);
        diaryList.add(new DiaryEntryModel("Shopping List","Tap to edit item"));
        diaryList.add(new DiaryEntryModel("Wakeup Time","Tap to edit item"));
        diaryList.add(new DiaryEntryModel("Sleeping Time","Tap to edit item"));
        diaryList.add(new DiaryEntryModel("Total Hours of Sleep","Tap to edit item"));
        diaryList.add(new DiaryEntryModel("Today's Weight","Tap to edit item"));
        diaryList.add(new DiaryEntryModel("Early Morning","Tap to edit item"));
        diaryList.add(new DiaryEntryModel("Breakfast","Tap to edit item"));
        diaryList.add(new DiaryEntryModel("Mid Morning","Tap to edit item"));
        diaryList.add(new DiaryEntryModel("Lunch","Tap to edit item"));
        diaryList.add(new DiaryEntryModel("Evening","Tap to edit item"));
        diaryList.add(new DiaryEntryModel("Dinner","Tap to edit item"));
        diaryList.add(new DiaryEntryModel("Post Dinner","Tap to edit item"));
        diaryList.add(new DiaryEntryModel("No. of meals taken","Tap to edit item"));
        diaryList.add(new DiaryEntryModel("Supplement/Medication","Tap to edit item"));
        diaryList.add(new DiaryEntryModel("No. of glasses of water","Tap to edit item"));
        diaryList.add(new DiaryEntryModel("No. of cups of Green Tea","Tap to edit item"));
        diaryList.add(new DiaryEntryModel("Time Spent on Exercise","Tap to edit item"));
        diaryList.add(new DiaryEntryModel("No. of times pooped","Tap to edit item"));
        recyclerView.setAdapter(diaryViewAdapter);
        diaryViewAdapter.notifyDataSetChanged();
    }
}
