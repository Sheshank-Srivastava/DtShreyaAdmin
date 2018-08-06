package com.dietitianshreya.eatrightdietadmin;

import android.Manifest;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pub.devrel.easypermissions.EasyPermissions;

import static com.dietitianshreya.eatrightdietadmin.Login.MyPREFERENCES;
import static com.dietitianshreya.eatrightdietadmin.Utils.MyFirebaseInstanceIdService.SharedPrefToken;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DashboardFragment.OnFragmentInteractionListener,
        BlankFragment.OnFragmentInteractionListener, ChatSelectionFragment.OnFragmentInteractionListener,
        AppointmentFragment.OnFragmentInteractionListener, AllClientsDetails.OnFragmentInteractionListener{

    FragmentManager fragmentManager;
    Fragment fragment;
    TabLayout tab_layout;
    private int BEFORE_TAG,RESULT_LOAD=1;
    String userid;
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
        SharedPreferences sharedpreferences = getSharedPreferences(SharedPrefToken, Context.MODE_PRIVATE);
//        String token = sharedpreferences.getString("token","zero");
        int change = sharedpreferences.getInt("change",1);
        Log.d("MainActivity",change+"");
        String deviceToken = FirebaseInstanceId.getInstance().getToken();
        SharedPreferences sharedpreferences1 = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        userid= String.valueOf(sharedpreferences1.getInt("clientId",0));
        if(change==1) {
            if (deviceToken!=null) {
                sendR(deviceToken, userid);
                Log.d("Token : ",deviceToken);
            }
        }
        long id = intentToWhatsapp(this);
       // sendIntent(id);
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

        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_home), 0);
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_chat_line), 1);
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_ladka_ladki), 2);
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_appointments), 3);
//        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_chat), 4);

        // set icon color pre-selected
     //   tab_layout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        fragmentManager = getSupportFragmentManager();
        fragment= new DashboardFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container,fragment).commit();
        tab_layout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        tab_layout.getTabAt(1).getIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        tab_layout.getTabAt(2).getIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        tab_layout.getTabAt(3).getIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);

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
//                        ChatSelectionFragment ch = (ChatSelectionFragment)fragment;
//                        ch.getData();
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
        View header = navigationView.getHeaderView(0);
        TextView clientName = (TextView) header.findViewById(R.id.clientName);
        TextView clientEmail = (TextView) header.findViewById(R.id.clientEmail);
        CircularImageView image = (CircularImageView) header.findViewById(R.id.profileImage);
//        clientEmail = (TextView) header.findViewById(R.id.clientEmail);
        SharedPreferences sharedpreferences2 = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        clientName.setText(sharedpreferences2.getString("user_name", "Unkonwn"));
        clientEmail.setText(sharedpreferences2.getString("email","Unknown"));
        Picasso.with(MainActivity.this).load(sharedpreferences2.getString("image","Unknown")).into(image);
//        clientEmail.setText(sharedpreferences2.getString("email", "Unknown"));
    }

    private void sendIntent(long id) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);

// the _ids you save goes here at the end of /data/12562
        intent.setDataAndType(Uri.parse("content://com.android.contacts/data/"+id),
                "vnd.android.cursor.item/vnd.com.whatsapp.voip.call");
        intent.setPackage("com.whatsapp");

        startActivity(intent);
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    public void MyFunction(){

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
            //start activity for reviews
            startActivity(new Intent(MainActivity.this,DietitianProfileReviews.class));
        } else if (id == R.id.notification) {
            startActivity(new Intent(MainActivity.this,SendGroupNotification.class));

        } else if (id == R.id.nav_share) {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=com.dietitianshreya.eatrightdietadmin");
            startActivity(Intent.createChooser(share,"Share Link"));
        } else if(id==R.id.logout){
            SharedPreferences mPrefs_delete = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor_delete = mPrefs_delete.edit();
            editor_delete.clear().commit();
            SharedPreferences mPrefs_delete1 = getSharedPreferences(SharedPrefToken, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor_delete1 = mPrefs_delete1.edit();
            editor_delete1.clear().commit();
            finish();
            startActivity(new Intent(MainActivity.this,Login.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



//
//    public void initiateSkypeUri(Context myContext, String mySkypeUri) {
//
//        // Make sure the Skype for Android client is installed.
//        if (!isSkypeClientInstalled(myContext)) {
//            goToMarket(myContext);
//            return;
//        }
//
//        // Create the Intent from our Skype URI.
//        Uri skypeUri = Uri.parse(mySkypeUri);
//        Intent myIntent = new Intent(Intent.ACTION_VIEW, skypeUri);
//
//        // Restrict the Intent to being handled by the Skype for Android client only.
//        myIntent.setComponent(new ComponentName("com.skype.raider", "com.skype.raider.Main"));
//        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        // Initiate the Intent. It should never fail because you've already established the
//        // presence of its handler (although there is an extremely minute window where that
//        // handler can go away).
//        myContext.startActivity(myIntent);
//
//        return;
//    }
//    public boolean isSkypeClientInstalled(Context myContext) {
//        PackageManager myPackageMgr = myContext.getPackageManager();
//        try {
//            myPackageMgr.getPackageInfo("com.skype.raider", PackageManager.GET_ACTIVITIES);
//        }
//        catch (PackageManager.NameNotFoundException e) {
//            return (false);
//        }
//        return (true);
//    }
//    public void goToMarket(Context myContext) {
//        Uri marketUri = Uri.parse("market://details?id=com.skype.raider");
//        Intent myIntent = new Intent(Intent.ACTION_VIEW, marketUri);
//        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        myContext.startActivity(myIntent);
//
//        return;
//    }
    private long intentToWhatsapp(Context context){
        long id=0;
        String[] galleryPermissions = {Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS};
        BEFORE_TAG=1;
        if (EasyPermissions.hasPermissions(this, galleryPermissions)) {

//            ContentResolver resolver = context.getContentResolver();
//            Cursor cursor = resolver.query(
//                    ContactsContract.Data.CONTENT_URI,
//                    null, null, null,
//                    ContactsContract.Contacts.DISPLAY_NAME);
//
////Now read data from cursor like
//
//            while (cursor.moveToNext()) {
//                long _id = cursor.getLong(cursor.getColumnIndex(ContactsContract.Data._ID));
//                String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
//                String mimeType = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.MIMETYPE));
//
//                Log.d("Data", _id+ " "+ displayName + " " + mimeType );
//
//            }

        } else {
            EasyPermissions.requestPermissions(this, "Access for storage",
                    101, galleryPermissions);
        }

        if (EasyPermissions.hasPermissions(this, galleryPermissions)) {

            ContentResolver resolver = context.getContentResolver();
            Cursor cursor = resolver.query(
                    ContactsContract.Data.CONTENT_URI,
                    null, null, null,
                    ContactsContract.Contacts.DISPLAY_NAME);

//Now read data from cursor like

            while (cursor.moveToNext()) {
                long _id = cursor.getLong(cursor.getColumnIndex(ContactsContract.Data._ID));
                String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                String mimeType = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.MIMETYPE));

                Log.d("Data", _id+ " "+ displayName + " " + mimeType );
                if(displayName.equalsIgnoreCase("Manya madan mecinies")&&mimeType.equals("vnd.android.cursor.item/vnd.com.whatsapp.video.call"))
                id = _id;
                //132791 mummy vnd.android.cursor.item/vnd.com.whatsapp.video.call
            }

        }

        return id;
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void sendR(final String token,final String userid) {

        String url = "https://shreyaapi.herokuapp.com/savetoken/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject result=new JSONObject();
                        try {
                            result = new JSONObject(response);
                            if(result.getInt("res")==1){
                                Log.d("res",response);

                                Log.d("Token","Saved");
                                SharedPreferences sharedpreferences = getSharedPreferences(SharedPrefToken, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putInt("change",0);
                                editor.commit();
                            }
                            else{
                                Log.d("Token","Not Saved");
//                                Toast.makeText(getApplicationContext(),result.getString("msg"),Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

//                        Toast.makeText(getApplicationContext(),"Something went wrong!\nCheck your Internet connection and try again..", Toast.LENGTH_LONG).show();
                        //Toast.makeText(MedicineData.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("userId",userid);
                params.put("token",token);
                params.put("dietitian","1");

                return params;
            }

        };

        int MY_SOCKET_TIMEOUT_MS = 50000;
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
