package com.dietitianshreya.dtshreyaadmin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;

import com.dietitianshreya.dtshreyaadmin.adapters.ClientAppointmentAdapter;

import java.util.Calendar;

public class ClientDetailActivity extends AppCompatActivity implements DietHistoryFragment.OnFragmentInteractionListener,
        ClinicalNotesFragment.OnFragmentInteractionListener, ClientAppointmentFragment.OnFragmentInteractionListener,
        BodyCompositionFragment.OnFragmentInteractionListener{

    ViewPager viewPager;
    TabLayout tabLayout;
    ViewPagerAdapter adapter;
    String clientID;
    int month,date,year;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_detail);
        getSupportActionBar().setElevation(0.0f);
        Bundle extras = getIntent().getExtras();
        clientID = extras.getString("clientID");
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

//Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void setupViewPager(ViewPager viewPager)
    {
        adapter = new ViewPagerAdapter(getSupportFragmentManager(),this);

        ClinicalNotesFragment clinicalNotesFragment= ClinicalNotesFragment.newInstance(clientID);
       ProgressFragment  progressFragment= new ProgressFragment();
        ClientAppointmentFragment appointment = ClientAppointmentFragment.newInstance(clientID);
        DietHistoryFragment dietHistoryFragment= DietHistoryFragment.newInstance(clientID);
        BodyCompositionFragment bodyCompositionFragment = BodyCompositionFragment.newInstance(clientID);
        adapter.addFragment(clinicalNotesFragment,"Notes");
        adapter.addFragment(appointment,"Appoitments");
        adapter.addFragment(dietHistoryFragment,"Diet Log");
        adapter.addFragment(bodyCompositionFragment,"BCA");
        adapter.addFragment(progressFragment,"Progress");
        viewPager.setAdapter(adapter);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.client_details_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.profile){
            Intent i = new Intent(ClientDetailActivity.this,ClientProfileActivity.class);
            i.putExtra("clientId",clientID);
            startActivity(i);
        }
        else if(id == R.id.diary){
            // calender class's instance and get current date , month and year from calender
            intent = new Intent(ClientDetailActivity.this,DiaryViewActivity.class);
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR); // current year
            int mMonth = c.get(Calendar.MONTH); // current month
            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
            // date picker dialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(ClientDetailActivity.this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // set day of month , month and year value in the edit text
                            month = monthOfYear;
                            date = dayOfMonth;
                            ClientDetailActivity.this.year = year;
                            intent.putExtra("clientId",clientID);
                            intent.putExtra("day",date);
                            intent.putExtra("month",month);
                            intent.putExtra("year",year);
                            startActivity(intent);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();



        }
        return super.onOptionsItemSelected(item);
    }
}
