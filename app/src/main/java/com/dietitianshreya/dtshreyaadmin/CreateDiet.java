package com.dietitianshreya.dtshreyaadmin;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CreateDiet extends AppCompatActivity implements DietCreateFragment.OnFragmentInteractionListener{

    ViewPager viewPager;
    TabLayout tabLayout;
    ViewPagerDietViewAdapter adapter;
    TextView monthTxt;
    String clientId;
    ArrayList<String> dates;
    ArrayList<String> dates1;
    EditText editText;
    int position=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);

        getSupportActionBar().setElevation(0.0f);
        Bundle extras = getIntent().getExtras();
        clientId = extras.getString("clientId");
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        monthTxt = (TextView) findViewById(R.id.month);
        tabLayout.setupWithViewPager(viewPager);
        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(7);
        setupViewPager(viewPager);

//Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < 7; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }


        TextView text = (TextView) tabLayout.getTabAt(0).getCustomView().findViewById(R.id.text2);
        text.setBackgroundResource(R.drawable.customer_image_placeholder_light);
        text.setTextColor(Color.parseColor("#E91E63"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView text = (TextView) tab.getCustomView().findViewById(R.id.text2);
                text.setBackgroundResource(R.drawable.customer_image_placeholder_light);
                text.setTextColor(Color.parseColor("#E91E63"));
                position = tab.getPosition();
//                Log.d("My tag","Selected"+pos);
//                View rootview = adapter.getTabView(pos);
//                TextView text = (TextView) rootview.findViewById(R.id.text1);
//                text.setTextColor(Color.WHITE);
//                tab.setCustomView(adapter.getSelectedTabView(tab.getPosition()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                TextView text = (TextView) tab.getCustomView().findViewById(R.id.text2);
                text.setBackgroundResource(R.drawable.customer_image_placeholder_primarycolor);
                text.setTextColor(Color.parseColor("#FFFFFF"));
//                int pos = tab.getPosition();
//                Log.d("My tag","Unselected"+pos);
//                View rootview = adapter.getTabView(pos);
//                TextView text = (TextView) rootview.findViewById(R.id.text1);
//                text.setTextColor(Color.CYAN);
//                tab.setCustomView(adapter.getTabView(tab.getPosition()));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    private void setupViewPager(ViewPager viewPager)
    {
         adapter = new ViewPagerDietViewAdapter(getSupportFragmentManager(),this);
            String year="";
        dates = new ArrayList<>();
        dates1=new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd-MMM-yyyy");

        String month1="";
        for(int i=0;i<7;i++){
            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DATE,i);
            String day = sdf.format(calendar.getTime());
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String datenew = df.format(calendar.getTime());
            Log.d("lolman",datenew);
            String dates[] = day.split(" ");
            Log.d("This Day",dates[0]);
            String mDay = ""+dates[0].charAt(0)+dates[0].charAt(1)+dates[0].charAt(2);
            String mDate = dates[1].split("-")[0];
            adapter.addTitles(mDay,mDate);
            String newDate=dates[1];
            month1 = dates[1].split("-")[1];
            year = dates[1].split("-")[2];
            monthTxt.setText(dates[1].split("-")[1]+" "+dates[1].split("-")[2]);
            this.dates.add(mDate);
            this.dates1.add(datenew);
        }
        monthTxt.setText(month1+" "+year);
        DietCreateFragment dietViewFragment1 = DietCreateFragment.newInstance(clientId,dates1.get(0));
        DietCreateFragment dietViewFragment2 = DietCreateFragment.newInstance(clientId,dates1.get(1));
        DietCreateFragment dietViewFragment3 = DietCreateFragment.newInstance(clientId,dates1.get(2));
        DietCreateFragment dietViewFragment4 = DietCreateFragment.newInstance(clientId,dates1.get(3));
        DietCreateFragment dietViewFragment5 = DietCreateFragment.newInstance(clientId,dates1.get(4));
        DietCreateFragment dietViewFragment6 = DietCreateFragment.newInstance(clientId,dates1.get(5));
        DietCreateFragment dietViewFragment7 = DietCreateFragment.newInstance(clientId,dates1.get(6));


        adapter.addFragment(dietViewFragment1,"1");
        adapter.addFragment(dietViewFragment2,"2");
        adapter.addFragment(dietViewFragment3,"3");
        adapter.addFragment(dietViewFragment4,"4");
        adapter.addFragment(dietViewFragment5,"5");
        adapter.addFragment(dietViewFragment6,"6");
        adapter.addFragment(dietViewFragment7,"7");
        viewPager.setAdapter(adapter);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("hey","hey");
        adapter.getItem(position).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

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
           // Toast.makeText(getApplicationContext(),"Diet Saved",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
