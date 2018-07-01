package com.dietitianshreya.eatrightdietadmin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dietitianshreya.eatrightdietadmin.Utils.VariablesModels;
import com.dietitianshreya.eatrightdietadmin.adapters.TestAdapter;
import com.dietitianshreya.eatrightdietadmin.models.TestDetailModel;
import com.dietitianshreya.eatrightdietadmin.models.TestHistoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.dietitianshreya.eatrightdietadmin.Login.MyPREFERENCES;

public class TestBooking extends AppCompatActivity {

    private AppointmentFragment.OnFragmentInteractionListener mListener;
    RecyclerView recyclerView;
    ArrayList<TestHistoryModel> testList;
    ArrayList<TestDetailModel> testDetailsList;
    TestAdapter testAdapter;
    FloatingActionButton fab;
    CoordinatorLayout coordinatorLayout;
    String userid;


    public static AppointmentFragment newInstance() {
        AppointmentFragment fragment = new AppointmentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_booking);

        // Inflate the layout for this fragment
        recyclerView = (RecyclerView) findViewById(R.id.re);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        SharedPreferences sharedpreferences1 = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        userid= String.valueOf(sharedpreferences1.getInt("clientId",0));
        testList=new ArrayList<>();
        testDetailsList = new ArrayList<>();
        testAdapter = new TestAdapter(testList,getApplicationContext());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(testAdapter);

        fetchData();



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestBooking.this,BookTest.class);
                finish();
                startActivity(intent);
            }
        });

    }


    public void fetchData() {
        final ProgressDialog progressDialog = new ProgressDialog(TestBooking.this);
        progressDialog.setMessage("Fetching data...");
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/alltestappointments/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject result = new JSONObject(response);
                            Log.d("REs",response);
                            progressDialog.dismiss();

                            if(result.getInt("res")==1) {
                                JSONObject res = result.getJSONObject("response");
                                int count = result.getInt("count");
                                if (count != 0){
                                    for (int i = 1; i <= count; i++) {
                                        JSONArray ar = res.getJSONArray(i+"");
                                        String Date = "";
                                        ArrayList<TestDetailModel> testDetailsList1 = new ArrayList<>();

                                        for (int j = 0; j < ar.length(); j++) {
                                            JSONObject ob = ar.getJSONObject(j);
                                            String testname, time, daysleft, status, id, userid, username,notes;
                                            //type = ob.getString("type");
                                            time = ob.getString("time");
                                            int hour = Integer.parseInt(time.split(":")[0]);
                                            String ampm = "A.M.";
                                            if (hour > 12) {
                                                hour -= 12;
                                                ampm = "P.M.";
                                            }
                                            String newtime = hour + ":" + time.split(":")[1] + " " + ampm;
                                            status = ob.getString("status");
                                            username = ob.getString(VariablesModels.user_name);
                                            daysleft = ob.getString("daysleft");
                                            testname=ob.getString("testname");
                                            userid = String.valueOf(ob.getInt(VariablesModels.userId));
                                            Date = ob.getString("date");
                                            notes= ob.getString("notes");
                                            //change the url for upcoming appointments, add days left with every client name
                                            testDetailsList1.add(new TestDetailModel(newtime,testname,notes,username,daysleft + " days left"));
                                        }
                                        testList.add(new TestHistoryModel(Date,testDetailsList1));
                                    }
                                    testAdapter.notifyDataSetChanged();
                                }else{
                                    //show that there are no appointments
                                }
                            }else{
                                //error
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Something went wrong!\nCheck your Internet connection and try again..", Toast.LENGTH_LONG).show();
                        //Toast.makeText(MedicineData.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("dietitianId",userid);
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
