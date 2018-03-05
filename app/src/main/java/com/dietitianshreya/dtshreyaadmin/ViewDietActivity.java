package com.dietitianshreya.dtshreyaadmin;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ViewDietActivity extends AppCompatActivity implements DietViewFragment.OnFragmentInteractionListener,
BlankFragment.OnFragmentInteractionListener{

    ViewPager viewPager;
    TabLayout tabLayout;
    TextView monthTxt;
    ViewPagerDietViewAdapter adapter;
    String clientId,date;
    int month,year,day;
    ArrayList<String> dates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);
        Bundle extras = getIntent().getExtras();
        clientId = extras.getString("clientId");
        date = extras.getString("date");
        day = Integer.parseInt(extras.getString("day"));
        month = Integer.parseInt(extras.getString("month"));
        year = Integer.parseInt(extras.getString("year"));
        getSupportActionBar().setElevation(0.0f);
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
//                int pos = tab.getPosition();
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
        dates = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd-MMM-yyyy");
        String month1="";
        for(int i=0;i<7;i++){
            Calendar calendar = new GregorianCalendar();
            calendar.set(year,month,day);
            calendar.add(Calendar.DATE,i);
            String day = sdf.format(calendar.getTime());
            Log.d("day",day);
            Log.d("Date",Calendar.DATE+"");
            String dates[] = day.split(" ");
            Log.d("This Day",dates[0]);
            String mDay = ""+dates[0].charAt(0)+dates[0].charAt(1)+dates[0].charAt(2);
            String mDate = dates[1].split("-")[0];
            adapter.addTitles(mDay,mDate);
            month1 = dates[1].split("-")[1];
            monthTxt.setText(dates[1].split("-")[1]+" "+dates[1].split("-")[2]);
            this.dates.add(mDate);
        }
        monthTxt.setText(month1+" "+year);
        DietViewFragment dietViewFragment1 = DietViewFragment.newInstance(clientId,dates.get(0));
        DietViewFragment dietViewFragment2 = DietViewFragment.newInstance(clientId,dates.get(1));
        DietViewFragment dietViewFragment3 = DietViewFragment.newInstance(clientId,dates.get(2));
        DietViewFragment dietViewFragment4 = DietViewFragment.newInstance(clientId,dates.get(3));
        DietViewFragment dietViewFragment5 = DietViewFragment.newInstance(clientId,dates.get(4));
        DietViewFragment dietViewFragment6 = DietViewFragment.newInstance(clientId,dates.get(5));
        DietViewFragment dietViewFragment7 = DietViewFragment.newInstance(clientId,dates.get(6));


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
    public void onFragmentInteraction(Uri uri) {

    }
}
