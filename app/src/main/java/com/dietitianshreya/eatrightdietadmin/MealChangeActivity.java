package com.dietitianshreya.eatrightdietadmin;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MealChangeActivity extends AppCompatActivity implements DietCreateFragment.OnFragmentInteractionListener {

    FragmentManager fragmentManager;
    String date,clientId;
    Fragment fragment;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_change);
        Bundle extra = getIntent().getExtras();
        clientId = extra.getString("clientId");
        date = extra.getString("date");
        fragmentManager = getSupportFragmentManager();
        fragment= DietCreateFragment.newInstance(clientId,date);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container,fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_diet_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.notes){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            // Get the layout inflater
            LayoutInflater linf = LayoutInflater.from(this);
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
                    if(!(TextUtils.isEmpty(editText.getText().toString().trim()))){
                        //Handle diet notes here
                    }
                }
            });
            alertDialog.show();
        }
        else if(id == R.id.save){
            Toast.makeText(getApplicationContext(),"Diet Saved",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("hey","hey");
        fragment.onActivityResult(requestCode, resultCode, data);
    }
}
