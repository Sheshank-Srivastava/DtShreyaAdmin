package com.dietitianshreya.eatrightdietadmin;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dietitianshreya.eatrightdietadmin.Utils.VariablesModels;
import com.dietitianshreya.eatrightdietadmin.models.ClientAppointmentModel;
import com.dietitianshreya.eatrightdietadmin.models.CompositionModel;
import com.dietitianshreya.eatrightdietadmin.models.DietPlanModel;
import com.dietitianshreya.eatrightdietadmin.models.MealModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ClientDetailActivity extends AppCompatActivity implements DietHistoryFragment.OnFragmentInteractionListener,
        ClinicalNotesFragment.OnFragmentInteractionListener, ClientAppointmentFragment.OnFragmentInteractionListener,
        BodyCompositionFragment.OnFragmentInteractionListener{
    String formattedDate;
    ViewPager viewPager;
    TabLayout tabLayout;
    ViewPagerAdapter adapter;
    String clientID;
    int month,date,year;
    String dateString;
    int bells,badge,level,kgs;
    ProgressDialog progressDialog ;

    ArrayList<DietPlanModel> dietList= new ArrayList<>();

    ArrayList<CompositionModel> compositionList;
    Intent intent;
    ArrayList<String> notesList= new ArrayList<>();
    ArrayList<MealModel> supplementList= new ArrayList<>();

    ArrayList<ClientAppointmentModel> appointmentList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_detail);
        getSupportActionBar().setElevation(0.0f);
        getSupportActionBar().setTitle("Client Details");
        Bundle extras = getIntent().getExtras();
        clientID = extras.getString("client");

progressDialog = new ProgressDialog(ClientDetailActivity.this);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        formattedDate = df.format(c);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        SimpleDateFormat currentdate= new SimpleDateFormat("dd-mm-yy");
        Date todaysDate = new Date();
        dateString= currentdate.format(todaysDate);
        compositionList = new ArrayList<>();

        fetchNotesData();

//Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void setupViewPager(ViewPager viewPager)
    {
        adapter = new ViewPagerAdapter(getSupportFragmentManager(),this);


        ClinicalNotesFragment clinicalNotesFragment= ClinicalNotesFragment.newInstance(clientID);
        Bundle data = new Bundle();//create bundle instance
        data.putStringArrayList("notes", notesList);
        clinicalNotesFragment.setArguments(data);//Set bundle data to fragment


        ClientAppointmentFragment appointment = ClientAppointmentFragment.newInstance(clientID);
        Bundle appointdata = new Bundle();//create bundle instance
        appointdata.putParcelableArrayList("appointments", appointmentList);
        appointment.setArguments(appointdata);//Set bundle data to fragment

        TodayDietFragment dietHistoryFragment= TodayDietFragment.newInstance(clientID,dietList);
        Bundle dietdata = new Bundle();//create bundle instance
        dietdata.putParcelableArrayList("lol", dietList);
        dietHistoryFragment.setArguments(dietdata);//Set bundle data to fragment

        BodyCompositionFragment bodyCompositionFragment = BodyCompositionFragment.newInstance(clientID);
        Bundle bcadata = new Bundle();//create bundle instance
        bcadata.putParcelableArrayList("bca", compositionList);
        bcadata.putString(VariablesModels.userId, clientID+"");

        bodyCompositionFragment.setArguments(bcadata);

        ProgressFragment  progressFragment=  ProgressFragment.newInstance(clientID);
        Bundle datapro = new Bundle();//create bundle instance
        datapro.putInt("bells",bells);
        datapro.putInt("badge",badge);
        datapro.putInt("level",level);
        datapro.putInt("kgs",kgs);
        datapro.putString(VariablesModels.userId,clientID);


        progressFragment.setArguments(datapro);

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
            i.putExtra(VariablesModels.userId,clientID);
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
                            intent.putExtra(VariablesModels.userId,clientID);
                            intent.putExtra("day",date+"-"+month+"-"+year);

                            startActivity(intent);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();



        }
        return super.onOptionsItemSelected(item);
    }

    public void fetchNotesData() {

      progressDialog.setMessage("Fetching data...");
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/getnote/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            Log.d("thanks",response);
                            JSONObject outerobject = new JSONObject(response);
                            int res= outerobject.getInt(VariablesModels.res);
                            String msg= outerobject.getString(VariablesModels.msg);
                            if( res==1) {

                                JSONArray array = outerobject.getJSONArray(VariablesModels.response);
                                for (int i = 0; i < array.length(); i++) {

                                    notesList.add(array.getString(i));
                                }
                            }
                            fetchAppointmentsData();



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
                params.put(VariablesModels.userId,clientID);
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


    public void fetchAppointmentsData() {
        String url = "https://shreyaapi.herokuapp.com/getuserappointment/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                           JSONObject object = new JSONObject(response);
                           String msg = object.getString("msg");
                           int res= object.getInt("res");
                           if(res==1) {
                               JSONArray array = object.getJSONArray("response");
                               Log.d("res_user_appointment", response);

                               for (int i = 0; i < array.length(); i++) {
                                   JSONObject response_object = array.getJSONObject(i);
                                   String datekey = response_object.getString("date");

                                   String date = datekey.split(" ")[0];
                                   String status = response_object.getString("status");
                                   String time = datekey.split(" ")[1];
                                   String dietitian = response_object.getString("dietitian");
                                   String type = response_object.getString("type");
                                   String id = response_object.getString(VariablesModels.appointmentId);
                                   appointmentList.add(new ClientAppointmentModel(date, time, type, status, dietitian));
                               }
//                           Log.d("app",appointmentList.get(0).getDate());
                           }

                            fetchProgressData();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Something went wrong!", Toast.LENGTH_LONG).show();
                        //Toast.makeText(MedicineData.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put(VariablesModels.userId,clientID+"");
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





    public void fetchBCAData() {

        String url = "https://shreyaapi.herokuapp.com/getbcadata/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject object = new JSONObject(response);
                            String msg = object.getString("msg");
                            int res= object.getInt("res");
                            JSONArray array = object.getJSONArray(VariablesModels.response);

                            for(int i = 0;i<array.length();i++)
                            {
                                JSONObject response_object=array.getJSONObject(i);
                                String date = response_object.getString("date");
                                String fat= response_object.getString("fat");
                                String water= response_object.getString("water");
                                String muscle= response_object.getString("muscle");
                                String weight= response_object.getString("weight");
                                String bone= response_object.getString("bone");

//                              compositionList.add(new CompositionModel(date,weight,fat,water,muscle,bone));

                            }
                            fetchProgressData();



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),"Something went wrong!", Toast.LENGTH_LONG).show();
                        //Toast.makeText(MedicineData.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put(VariablesModels.userId,clientID);

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

    public void fetchProgressData() {
        compositionList = new ArrayList<>();

        String url = "https://shreyaapi.herokuapp.com/getprogress/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            progressDialog.dismiss();
                            Log.d("res",response);
                            JSONObject object = new JSONObject(response);
                            String msg = object.getString("msg");
                            int res= object.getInt("res");
                            if(res==1) {
                                JSONObject innerobject = object.getJSONObject(VariablesModels.response);

                                bells = innerobject.getInt("bells");
                                badge = innerobject.getInt("badge");
                                level = innerobject.getInt("level");
                                kgs = innerobject.getInt("kgslost");
                            }




                            setupViewPager(viewPager);


                        } catch (JSONException e) {
                            setupViewPager(viewPager);
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
                params.put(VariablesModels.userId,clientID+"");
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
