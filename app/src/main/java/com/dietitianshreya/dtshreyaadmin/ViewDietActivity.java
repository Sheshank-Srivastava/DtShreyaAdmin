package com.dietitianshreya.dtshreyaadmin;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dietitianshreya.dtshreyaadmin.models.DietPlanModel;
import com.dietitianshreya.dtshreyaadmin.models.MealModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class ViewDietActivity extends AppCompatActivity implements DietViewFragment.OnFragmentInteractionListener,
BlankFragment.OnFragmentInteractionListener{

    ViewPager viewPager;
    TabLayout tabLayout;
    TextView monthTxt;
    ViewPagerDietViewAdapter adapter;
    String clientId,date;
    ArrayList<String> dates1;
    ArrayList<DietPlanModel> dietList;
    ArrayList<DietPlanModel> dietList1;
    ArrayList<DietPlanModel> dietList2;
    ArrayList<DietPlanModel> dietList3;
    ArrayList<DietPlanModel> dietList4;
    ArrayList<DietPlanModel> dietList5;
    ArrayList<DietPlanModel> dietList6;
    ArrayList<MealModel> mealList;
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

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                TextView text = (TextView) tab.getCustomView().findViewById(R.id.text2);
                text.setBackgroundResource(R.drawable.customer_image_placeholder_primarycolor);
                text.setTextColor(Color.parseColor("#FFFFFF"));

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
        dates1=new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd-MMM-yyyy");
        String month1="";
        for(int i=0;i<7;i++){
            Calendar calendar = new GregorianCalendar();
            calendar.set(year,month,day);
            calendar.add(Calendar.DATE,-i);

            String day = sdf.format(calendar.getTime());
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String datenew = df.format(calendar.getTime());



            Log.d("Date",Calendar.DATE+"");
            String dates[] = day.split(" ");
            Log.d("This Day",dates[0]);
            String mDay = ""+dates[0].charAt(0)+dates[0].charAt(1)+dates[0].charAt(2);
            String mDate = dates[1].split("-")[0];
            adapter.addTitles(mDay,mDate);
            month1 = dates[1].split("-")[1];
            monthTxt.setText(dates[1].split("-")[1]+" "+dates[1].split("-")[2]);
            this.dates.add(mDate);
            this.dates1.add(datenew);
        }
        monthTxt.setText(month1+" "+year);
        Log.d("mytag",dates1.get(0));
        DietViewFragment dietViewFragment1 = DietViewFragment.newInstance(clientId,dates1.get(0),dietList);
        DietViewFragment dietViewFragment2 = DietViewFragment.newInstance(clientId,dates1.get(1),dietList1);
        DietViewFragment dietViewFragment3 = DietViewFragment.newInstance(clientId,dates1.get(2),dietList2);
        DietViewFragment dietViewFragment4 = DietViewFragment.newInstance(clientId,dates1.get(3),dietList3);
        DietViewFragment dietViewFragment5 = DietViewFragment.newInstance(clientId,dates1.get(4),dietList4);
        DietViewFragment dietViewFragment6 = DietViewFragment.newInstance(clientId,dates1.get(5),dietList5);
        DietViewFragment dietViewFragment7 = DietViewFragment.newInstance(clientId,dates1.get(6),dietList6);


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


    public void fetchDietData() {
        final ProgressDialog progressDialog = new ProgressDialog(ViewDietActivity.this);
        progressDialog.setMessage("Fetching data...");
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/getdiet/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.d("res",response);
                            JSONObject outerobject = new JSONObject(response);
                            JSONObject innerobject = outerobject.getJSONObject("response");

                            JSONObject dateone= innerobject.getJSONObject(dates1.get(0)+"");
                            JSONObject datetwo= innerobject.getJSONObject(dates1.get(1)+"");
                            JSONObject datethree= innerobject.getJSONObject(dates1.get(2)+"");
                            JSONObject datefour= innerobject.getJSONObject(dates1.get(3)+"");
                            JSONObject datefive= innerobject.getJSONObject(dates1.get(4)+"");
                            JSONObject datesix= innerobject.getJSONObject(dates1.get(5)+"");
                            JSONObject dateseven= innerobject.getJSONObject(dates1.get(6)+"");


                            if(dateone.has("foodone"))
                            {
                                JSONArray array = innerobject.getJSONArray("foodone");
                                ArrayList<MealModel> mealList= new ArrayList<>();
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList.add(new DietPlanModel("Early Morning",mealList,1));
                            }
                            if(dateone.has("foodtwo"))
                            {ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodtwo");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList.add(new DietPlanModel("Breakfast",mealList,2));
                            }

                            if(dateone.has("foodthree"))
                            {
                                ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodthree");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList.add(new DietPlanModel("Mid Morning",mealList,3));
                            }

                            if(dateone.has("foodfour"))
                            {   ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodfour");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList.add(new DietPlanModel("Lunch",mealList,4));
                            }

                            if(dateone.has("foodfive"))
                            {       ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodfive");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList.add(new DietPlanModel("Evening",mealList,5));
                            }

                            if(dateone.has("foodsix"))
                            {     ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("sixfood");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList.add(new DietPlanModel("Dinner",mealList,6));
                            }

                            if(dateone.has("foodseven"))
                            {      ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodseven");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList.add(new DietPlanModel("Post Dinner",mealList,7));
                            }

                            if(innerobject.has("foodeight"))
                            {       ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodeight");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"Supplements",true));
                                }
                                dietList.add(new DietPlanModel("Supplements",mealList,8));
                            }





                            //************************************************************************

                            if(datetwo.has("foodone"))
                            {
                                JSONArray array = innerobject.getJSONArray("foodone");
                                ArrayList<MealModel> mealList= new ArrayList<>();
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList1.add(new DietPlanModel("Early Morning",mealList,1));
                            }
                            if(datetwo.has("foodtwo"))
                            {ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodtwo");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList1.add(new DietPlanModel("Breakfast",mealList,2));
                            }

                            if(datetwo.has("foodthree"))
                            {
                                ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodthree");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList1.add(new DietPlanModel("Mid Morning",mealList,3));
                            }

                            if(datetwo.has("foodfour"))
                            {   ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodfour");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList1.add(new DietPlanModel("Lunch",mealList,4));
                            }

                            if(datetwo.has("foodfive"))
                            {       ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodfive");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList1.add(new DietPlanModel("Evening",mealList,5));
                            }

                            if(datetwo.has("foodsix"))
                            {     ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("sixfood");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList1.add(new DietPlanModel("Dinner",mealList,6));
                            }

                            if(datetwo.has("foodseven"))
                            {      ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodseven");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList1.add(new DietPlanModel("Post Dinner",mealList,7));
                            }

                            if(datetwo.has("foodeight"))
                            {       ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodeight");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"Supplements",true));
                                }
                                dietList1.add(new DietPlanModel("Supplements",mealList,8));
                            }

   //**********************************************************************************************************



                            if(datethree.has("foodone"))
                            {
                                JSONArray array = innerobject.getJSONArray("foodone");
                                ArrayList<MealModel> mealList= new ArrayList<>();
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList2.add(new DietPlanModel("Early Morning",mealList,1));
                            }
                            if(datethree.has("foodtwo"))
                            {ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodtwo");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList2.add(new DietPlanModel("Breakfast",mealList,2));
                            }

                            if(datethree.has("foodthree"))
                            {
                                ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodthree");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList2.add(new DietPlanModel("Mid Morning",mealList,3));
                            }

                            if(datethree.has("foodfour"))
                            {   ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodfour");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList2.add(new DietPlanModel("Lunch",mealList,4));
                            }

                            if(datethree.has("foodfive"))
                            {       ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodfive");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList2.add(new DietPlanModel("Evening",mealList,5));
                            }

                            if(datethree.has("foodsix"))
                            {     ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("sixfood");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList2.add(new DietPlanModel("Dinner",mealList,6));
                            }

                            if(datethree.has("foodseven"))
                            {      ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodseven");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList2.add(new DietPlanModel("Post Dinner",mealList,7));
                            }

                            if(datethree.has("foodeight"))
                            {       ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodeight");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"Supplements",true));
                                }
                                dietList2.add(new DietPlanModel("Supplements",mealList,8));
                            }

//*********************************************************************************************************

                            if(datefour.has("foodone"))
                            {
                                JSONArray array = innerobject.getJSONArray("foodone");
                                ArrayList<MealModel> mealList= new ArrayList<>();
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList3.add(new DietPlanModel("Early Morning",mealList,1));
                            }
                            if(datefour.has("foodtwo"))
                            {ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodtwo");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList3.add(new DietPlanModel("Breakfast",mealList,2));
                            }

                            if(datefour.has("foodthree"))
                            {
                                ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodthree");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList3.add(new DietPlanModel("Mid Morning",mealList,3));
                            }

                            if(datefour.has("foodfour"))
                            {   ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodfour");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList3.add(new DietPlanModel("Lunch",mealList,4));
                            }

                            if(datefour.has("foodfive"))
                            {       ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodfive");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList3.add(new DietPlanModel("Evening",mealList,5));
                            }

                            if(datefour.has("foodsix"))
                            {     ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("sixfood");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList3.add(new DietPlanModel("Dinner",mealList,6));
                            }

                            if(datefour.has("foodseven"))
                            {      ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodseven");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList3.add(new DietPlanModel("Post Dinner",mealList,7));
                            }

                            if(datefour.has("foodeight"))
                            {       ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodeight");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"Supplements",true));
                                }
                                dietList3.add(new DietPlanModel("Supplements",mealList,8));
                            }
//**********************************************************************************************************
                            if(datefive.has("foodone"))
                            {
                                JSONArray array = innerobject.getJSONArray("foodone");
                                ArrayList<MealModel> mealList= new ArrayList<>();
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList4.add(new DietPlanModel("Early Morning",mealList,1));
                            }
                            if(datefive.has("foodtwo"))
                            {ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodtwo");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList4.add(new DietPlanModel("Breakfast",mealList,2));
                            }

                            if(datefive.has("foodthree"))
                            {
                                ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodthree");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList4.add(new DietPlanModel("Mid Morning",mealList,3));
                            }

                            if(datefive.has("foodfour"))
                            {   ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodfour");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList4.add(new DietPlanModel("Lunch",mealList,4));
                            }

                            if(datefive.has("foodfive"))
                            {       ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodfive");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList4.add(new DietPlanModel("Evening",mealList,5));
                            }

                            if(datefive.has("foodsix"))
                            {     ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("sixfood");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList4.add(new DietPlanModel("Dinner",mealList,6));
                            }

                            if(datefive.has("foodseven"))
                            {      ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodseven");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList4.add(new DietPlanModel("Post Dinner",mealList,7));
                            }

                            if(datefive.has("foodeight"))
                            {       ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodeight");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"Supplements",true));
                                }
                                dietList4.add(new DietPlanModel("Supplements",mealList,8));
                            }

//*********************************************************************************************************
                            if(datesix.has("foodone"))
                            {
                                JSONArray array = innerobject.getJSONArray("foodone");
                                ArrayList<MealModel> mealList= new ArrayList<>();
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList5.add(new DietPlanModel("Early Morning",mealList,1));
                            }
                            if(datesix.has("foodtwo"))
                            {ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodtwo");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList5.add(new DietPlanModel("Breakfast",mealList,2));
                            }

                            if(datesix.has("foodthree"))
                            {
                                ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodthree");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList5.add(new DietPlanModel("Mid Morning",mealList,3));
                            }

                            if(datesix.has("foodfour"))
                            {   ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodfour");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList5.add(new DietPlanModel("Lunch",mealList,4));
                            }

                            if(datesix.has("foodfive"))
                            {       ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodfive");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList5.add(new DietPlanModel("Evening",mealList,5));
                            }

                            if(datesix.has("foodsix"))
                            {     ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("sixfood");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList5.add(new DietPlanModel("Dinner",mealList,6));
                            }

                            if(datesix.has("foodseven"))
                            {      ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodseven");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList5.add(new DietPlanModel("Post Dinner",mealList,7));
                            }

                            if(datesix.has("foodeight"))
                            {       ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodeight");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"Supplements",true));
                                }
                                dietList5.add(new DietPlanModel("Supplements",mealList,8));
                            }


//*********************************************************************************************************

                            if(dateseven.has("foodone"))
                            {
                                JSONArray array = innerobject.getJSONArray("foodone");
                                ArrayList<MealModel> mealList= new ArrayList<>();
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList6.add(new DietPlanModel("Early Morning",mealList,1));
                            }
                            if(dateseven.has("foodtwo"))
                            {ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodtwo");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList6.add(new DietPlanModel("Breakfast",mealList,2));
                            }

                            if(dateseven.has("foodthree"))
                            {
                                ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodthree");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList6.add(new DietPlanModel("Mid Morning",mealList,3));
                            }

                            if(dateseven.has("foodfour"))
                            {   ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodfour");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList6.add(new DietPlanModel("Lunch",mealList,4));
                            }

                            if(dateseven.has("foodfive"))
                            {       ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodfive");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList6.add(new DietPlanModel("Evening",mealList,5));
                            }

                            if(dateseven.has("foodsix"))
                            {     ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("sixfood");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList6.add(new DietPlanModel("Dinner",mealList,6));
                            }

                            if(dateseven.has("foodseven"))
                            {      ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodseven");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"xy",true));
                                }
                                dietList6.add(new DietPlanModel("Post Dinner",mealList,7));
                            }

                            if(dateseven.has("foodeight"))
                            {       ArrayList<MealModel> mealList= new ArrayList<>();
                                JSONArray array = innerobject.getJSONArray("foodeight");
                                for (int i =0;i<array.length();i++)
                                {
                                    String food= array.getString(i);
                                    mealList.add(new MealModel(food,"Supplements",true));
                                }
                                dietList6.add(new DietPlanModel("Supplements",mealList,8));
                            }


//*********************************************************************************************************

                            progressDialog.dismiss();



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Something went wrong!", Toast.LENGTH_LONG).show();
                        //Toast.makeText(MedicineData.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("userid",clientId);
                params.put("date",dates1.get(0));
                return params;
            }

        };

        int MY_SOCKET_TIMEOUT_MS = 50000;
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
}
