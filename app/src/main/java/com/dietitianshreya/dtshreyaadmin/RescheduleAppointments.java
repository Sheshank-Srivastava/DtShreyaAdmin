package com.dietitianshreya.dtshreyaadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dietitianshreya.dtshreyaadmin.adapters.RescheduleAppointmentAdaper;
import com.dietitianshreya.dtshreyaadmin.models.CompositionModel;
import com.dietitianshreya.dtshreyaadmin.models.RescheduleAppointmentModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RescheduleAppointments extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<RescheduleAppointmentModel> appointmentList;
    RescheduleAppointmentAdaper appointmentAdaper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reschedule_appointments);
        recyclerView = (RecyclerView) findViewById(R.id.re);
        appointmentList = new ArrayList<>();
        appointmentAdaper = new RescheduleAppointmentAdaper(appointmentList,RescheduleAppointments.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(appointmentAdaper);
        recyclerView.setLayoutManager(layoutManager);
        fetchData();
//        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
//            @Override
//            public void onClick(View view, int position) throws NoSuchFieldException, IllegalAccessException {
//                RescheduleAppointmentModel appointment = appointmentList.get(position);
//                Intent i = new Intent(RescheduleAppointments.this,ClientDetailActivity.class);
//                i.putExtra("clientID",appointment.getClientId());
//                startActivity(i);
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));
//        appointmentList.add(new RescheduleAppointmentModel("Akshit Tyagi","1234567","10/06/2018 - 9:30 A.M.","10/06/2018 - 9:30 A.M.","awaited","4 days left"));
//        appointmentList.add(new RescheduleAppointmentModel("Akshit Tyagi","1234567","10/06/2018 - 9:30 A.M.","10/06/2018 - 9:30 A.M.","awaited","4 days left"));
//        appointmentList.add(new RescheduleAppointmentModel("Akshit Tyagi","1234567","10/06/2018 - 9:30 A.M.","10/06/2018 - 9:30 A.M.","awaited","4 days left"));
//        appointmentList.add(new RescheduleAppointmentModel("Akshit Tyagi","1234567","10/06/2018 - 9:30 A.M.","10/06/2018 - 9:30 A.M.","awaited","4 days left"));
//        appointmentList.add(new RescheduleAppointmentModel("Akshit Tyagi","1234567","10/06/2018 - 9:30 A.M.","10/06/2018 - 9:30 A.M.","awaited","4 days left"));
//        appointmentList.add(new RescheduleAppointmentModel("Akshit Tyagi","1234567","10/06/2018 - 9:30 A.M.","10/06/2018 - 9:30 A.M.","awaited","4 days left"));
//        appointmentAdaper.notifyDataSetChanged();
    }

    public void fetchData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching data...");
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/reschedulerequestview/";
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
                                        String date,requested,id,daysleft,clientId,name;
                                        date = ob.getString("date");
                                        name = ob.getString("name");
                                        requested = ob.getString("requestedtime");
                                        id = ob.getString("id");
                                        daysleft = ob.getString("daysleft");
                                        clientId = ob.getString("clientId");
                                        appointmentList.add(new RescheduleAppointmentModel(name,clientId,date,requested,id,daysleft));
                                    }
                                    appointmentAdaper.notifyDataSetChanged();
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
                params.put("dietitianId","1");
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
