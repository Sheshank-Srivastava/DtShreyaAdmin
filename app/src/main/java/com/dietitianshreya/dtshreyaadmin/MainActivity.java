package com.dietitianshreya.dtshreyaadmin;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.dietitianshreya.dtshreyaadmin.adapters.AppointmentsAdapter;
import com.dietitianshreya.dtshreyaadmin.models.AppointmentsModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DashboardFragment.OnFragmentInteractionListener,
        BlankFragment.OnFragmentInteractionListener, ChatSelectionFragment.OnFragmentInteractionListener,
        AppointmentFragment.OnFragmentInteractionListener, AllClientsDetails.OnFragmentInteractionListener{

    FragmentManager fragmentManager;
    Fragment fragment;
    TabLayout tab_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

//        //ImageView definition
//        clientList = (ImageView) findViewById(R.id.clientList);
//        clientList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this,ClientListActivity.class));
//            }
//        });
//        appointmentImg = (ImageView) findViewById(R.id.appointmentImg);
//        appointmentImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this,CustomerDetail.class));
//            }
//        });

//
//        BottomNavigationView bottomNavigationView = (BottomNavigationView)
//                findViewById(R.id.bottom_navigation);
//
//        fragmentManager = getSupportFragmentManager();
//        fragment= new DashboardFragment();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.container,fragment).commit();
//        bottomNavigationView.setOnNavigationItemSelectedListener(
//                new BottomNavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(MenuItem item) {
//
//                        switch (item.getItemId()) {
//                            case R.id.dashboard:
//                                getSupportActionBar().setTitle("Dashboard");
//                                fragment = new DashboardFragment();
//                                break;
//                            case R.id.chat:
//                                getSupportActionBar().setTitle("Chat");
//                                fragment = new ChatSelectionFragment();
//                                break;
//                            case R.id.clients:
//                                getSupportActionBar().setTitle("Clients");
//                                fragment = new BlankFragment();
//                                break;
//                            case R.id.appointment:
//                                getSupportActionBar().setTitle("Appointment Details");
//                                fragment = new AppointmentFragment();
//                                break;
//                            case R.id.dietCreate:
//                                getSupportActionBar().setTitle("Diet Creation");
//                                fragment = new BlankFragment();
//                                break;
//                        }
//                        final FragmentTransaction transaction = fragmentManager.beginTransaction();
//                        transaction.replace(R.id.container,fragment).commit();
//                        return true;
//                    }
//                });

        tab_layout = (TabLayout) findViewById(R.id.tab_layout);

        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_chat), 0);
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_chat), 1);
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_chat), 2);
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_chat), 3);
//        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_chat), 4);

        // set icon color pre-selected
        tab_layout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        fragmentManager = getSupportFragmentManager();
        fragment= new DashboardFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container,fragment).commit();
        tab_layout.getTabAt(1).getIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        tab_layout.getTabAt(2).getIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        tab_layout.getTabAt(3).getIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        //tab_layout.getTabAt(4).getIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);

        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
                switch (tab.getPosition()) {
                    case 0:
                        getSupportActionBar().setTitle("Dashboard");
                        fragment = new DashboardFragment();
                        break;
                    case 1:
                        getSupportActionBar().setTitle("Chat");
                        fragment = new ChatSelectionFragment();
                        break;
                    case 2:
                        getSupportActionBar().setTitle("Client");
                        fragment = new AllClientsDetails();
                        break;
                    case 3:
                        getSupportActionBar().setTitle("Appointments");
                        fragment = new AppointmentFragment();
                        break;
//                    case 4:
//                        getSupportActionBar().setTitle("Diet Creation");
//                        fragment = new DashboardFragment();
//                        break;
                }

                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container,fragment).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.d("tag","Im here");
                tab.getIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_test) {

            Intent intent = new Intent(MainActivity.this,TestBooking.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
