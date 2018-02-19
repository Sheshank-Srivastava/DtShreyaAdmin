package com.dietitianshreya.dtshreyaadmin;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CustomerDetail extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);
        getSupportActionBar().setElevation(0.0f);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);
        setupViewPager(viewPager);

//Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);



    }
    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        BlankFragment callsFragment=new BlankFragment();
        BlankFragment chatFragment=new BlankFragment();
        BlankFragment contactsFragment=new BlankFragment();
        adapter.addFragment(callsFragment,"CALLS");
        adapter.addFragment(chatFragment,"CHAT");
        adapter.addFragment(contactsFragment,"CONTACTS");
        viewPager.setAdapter(adapter);
    }
}
