package com.dietitianshreya.dtshreyaadmin;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
    FloatingActionButton addNotes;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_selection);
        recyclerView = (RecyclerView) findViewById(R.id.re);
        addNotes = findViewById(R.id.fabNotes);
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

        addNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAddNotes();
            }
        });

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

public  void setAddNotes()
{
    AlertDialog.Builder alertDialog = new AlertDialog.Builder(NotesSelection.this);
    // Get the layout inflater
    LayoutInflater linf = LayoutInflater.from(NotesSelection.this);
    final View inflator = linf.inflate(R.layout.custom_dialog, null);
    alertDialog.setTitle("Add a diet note...");
    alertDialog.setView(inflator);
    editText = (EditText) inflator.findViewById(R.id.quant);
    editText.setHint("Type here the note");
    TextView text = (TextView) inflator.findViewById(R.id.text);
    TextView dot = (TextView) inflator.findViewById(R.id.dot);
    text.setVisibility(View.GONE);
    dot.setVisibility(View.GONE);
    alertDialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
          //  notesarray.put(editText.getText().toString());

           // Log.d("notes",notesarray.length()+"");
            if(!(TextUtils.isEmpty(editText.getText().toString().trim()))){
                //Handle diet notes here
                notes.add(editText.getText().toString());
                dietNotesAdapter.notifyDataSetChanged();
            }
        }
    });
    alertDialog.show();
}

}
