package com.dietitianshreya.dtshreyaadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.dietitianshreya.dtshreyaadmin.adapters.DietNotesAdapter;
import com.dietitianshreya.dtshreyaadmin.models.DietNotesModel;
import com.dietitianshreya.dtshreyaadmin.models.DietPlanModel;
import com.dietitianshreya.dtshreyaadmin.models.MealModel;

import java.util.ArrayList;

public class NotesSelection extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> notes;
    ArrayList<String>notesList;
    DietNotesAdapter dietNotesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_selection);
        recyclerView = (RecyclerView) findViewById(R.id.re);
        notes = new ArrayList<>();
       // notesList = new ArrayList<>();
        Bundle extra = getIntent().getExtras();

        notesList = extra.getStringArrayList("notes");
        dietNotesAdapter = new DietNotesAdapter(notes,this);

        for (int i =0;i<notesList.size();i++)
        {
            notes.add(notesList.get(i));
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(dietNotesAdapter);
        dietNotesAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_diet_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.save){

            Log.d("mytag",dietNotesAdapter.getNotes().size()+"");

            Intent returnIntent = new Intent();
            returnIntent.putStringArrayListExtra("notes",dietNotesAdapter.getNotes());
            setResult(RESULT_OK,returnIntent);
            finish();

        }
        return super.onOptionsItemSelected(item);
    }



}
