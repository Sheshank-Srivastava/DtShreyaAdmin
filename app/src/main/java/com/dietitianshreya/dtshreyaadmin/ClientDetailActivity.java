package com.dietitianshreya.dtshreyaadmin;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dietitianshreya.dtshreyaadmin.adapters.ClientAppointmentAdapter;

public class ClientDetailActivity extends AppCompatActivity implements DietHistoryFragment.OnFragmentInteractionListener,
        BasicInfoFragment.OnFragmentInteractionListener, ClientAppointmentFragment.OnFragmentInteractionListener{

    ViewPager viewPager;
    TabLayout tabLayout;
    ViewPagerAdapter adapter;
    String clientID;
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
        viewPager.setOffscreenPageLimit(7);
        setupViewPager(viewPager);

//Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void setupViewPager(ViewPager viewPager)
    {
        adapter = new ViewPagerAdapter(getSupportFragmentManager(),this);

        BasicInfoFragment basicInfoFragment= BasicInfoFragment.newInstance(clientID);
        ClientAppointmentFragment appointment = ClientAppointmentFragment.newInstance(clientID);
        DietHistoryFragment dietHistoryFragment= DietHistoryFragment.newInstance(clientID);
        adapter.addFragment(basicInfoFragment,"Basic Information");
        adapter.addFragment(appointment,"Appointments");
        adapter.addFragment(dietHistoryFragment,"Diet History");
        viewPager.setAdapter(adapter);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
