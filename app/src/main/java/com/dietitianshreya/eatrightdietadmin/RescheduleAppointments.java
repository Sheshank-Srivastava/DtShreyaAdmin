package com.dietitianshreya.eatrightdietadmin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dietitianshreya.eatrightdietadmin.Utils.VariablesModels;
import com.dietitianshreya.eatrightdietadmin.adapters.RescheduleAppointmentAdaper;
import com.dietitianshreya.eatrightdietadmin.models.RescheduleAppointmentModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.dietitianshreya.eatrightdietadmin.Login.MyPREFERENCES;

public class RescheduleAppointments extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<RescheduleAppointmentModel> appointmentList;
    RescheduleAppointmentAdaper appointmentAdaper;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reschedule_appointments);
        getSupportActionBar().setTitle("Reschedule Requests");
        recyclerView = (RecyclerView) findViewById(R.id.re);
        appointmentList = new ArrayList<>();
        SharedPreferences sharedpreferences1 = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        userid= String.valueOf(sharedpreferences1.getInt("clientId",0));
        appointmentAdaper = new RescheduleAppointmentAdaper(appointmentList, RescheduleAppointments.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(appointmentAdaper);
        recyclerView.setLayoutManager(layoutManager);
        fetchData();

    }

    public void fetchData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching data...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/reschedulerequestview/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response",response);

                        try {
                            progressDialog.dismiss();
                            JSONObject result = new JSONObject(response);

                            if(result.getInt("res")==1) {
                                JSONArray ar = result.getJSONArray("response");
                                if (ar.length() != 0){
                                    for (int i = 0; i < ar.length(); i++) {
                                        JSONObject ob = ar.getJSONObject(i);
                                        String date,requested,id,daysleft,clientId,name;
                                        date = ob.getString(VariablesModels.date);
                                        name = ob.getString(VariablesModels.user_name);
                                        requested = ob.getString("requestedtime");
                                        id = ob.getString(VariablesModels.appointmentId);
                                        daysleft = ob.getString("daysleft");
                                        clientId = ob.getString(VariablesModels.userId);
                                        appointmentList.add(new RescheduleAppointmentModel(name,clientId,date,requested,id,daysleft));
                                    }
                                    appointmentAdaper.notifyDataSetChanged();
                                    findViewById(R.id.noDietView).setVisibility(View.GONE);
                                }else{
                                    //show that there are no appointments
                                    findViewById(R.id.noDietView).setVisibility(View.VISIBLE);
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
