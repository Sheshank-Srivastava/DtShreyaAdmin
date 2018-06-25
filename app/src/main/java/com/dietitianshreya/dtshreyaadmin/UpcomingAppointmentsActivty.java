package com.dietitianshreya.dtshreyaadmin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dietitianshreya.dtshreyaadmin.Utils.VariablesModels;
import com.dietitianshreya.dtshreyaadmin.adapters.UpcomingAppointmentAdapter;
import com.dietitianshreya.dtshreyaadmin.models.AppointmentDetailsModel;
import com.dietitianshreya.dtshreyaadmin.models.LastMonthUsersModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.dietitianshreya.dtshreyaadmin.Login.MyPREFERENCES;

public class UpcomingAppointmentsActivty extends AppCompatActivity {

    String userid;
    RecyclerView recyclerView;
    ArrayList<AppointmentDetailsModel> appointmentDetailsList;
    UpcomingAppointmentAdapter appointmentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_appointments);
        getSupportActionBar().setTitle("Upcoming Appointments");
        recyclerView = (RecyclerView) findViewById(R.id.re);
        appointmentDetailsList = new ArrayList<>();
        appointmentAdapter = new UpcomingAppointmentAdapter(appointmentDetailsList,UpcomingAppointmentsActivty.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(UpcomingAppointmentsActivty.this);
        recyclerView.setAdapter(appointmentAdapter);
        recyclerView.setLayoutManager(mLayoutManager);
        SharedPreferences sharedpreferences1 = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        userid= String.valueOf(sharedpreferences1.getInt("clientId",0));

        fetchData();




    }

    public void fetchData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching data...");
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/upcomingappointments/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            progressDialog.dismiss();
                            JSONObject result = new JSONObject(response);
                            if(result.getInt("res")==1) {
                                JSONArray ar = result.getJSONArray("response");
                                if (ar.length() != 0){
                                    for (int i = 0; i < ar.length(); i++) {
                                        JSONObject ob = ar.getJSONObject(i);
                                        String type,time,daysleft,status,id,userid,username;
                                        type = ob.getString("type");
                                        time = ob.getString("time");
                                        int hour = Integer.parseInt(time.split(":")[0]);
                                        String ampm = "A.M.";
                                        if(hour>12){
                                            hour-=12;
                                            ampm="P.M.";
                                        }
                                        String newtime = hour + ":" + time.split(":")[1]+" "+ampm;
                                        status = ob.getString("status");
                                        id = String.valueOf(ob.getInt(VariablesModels.userId));
                                        username = ob.getString(VariablesModels.user_name);
                                        daysleft = ob.getString("daysleft");
                                        //change the url for upcoming appointments, add days left with every client name
                                        appointmentDetailsList.add(new AppointmentDetailsModel(newtime,type,status,username,daysleft+ " days left",id));
                                    }
                                    appointmentAdapter.notifyDataSetChanged();
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

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
