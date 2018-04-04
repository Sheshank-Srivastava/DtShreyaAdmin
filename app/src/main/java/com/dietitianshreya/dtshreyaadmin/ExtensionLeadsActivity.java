package com.dietitianshreya.dtshreyaadmin;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dietitianshreya.dtshreyaadmin.adapters.ExtensionLeadsAdaper;
import com.dietitianshreya.dtshreyaadmin.models.AppointmentDetailsModel;
import com.dietitianshreya.dtshreyaadmin.models.ExtensionLeadsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExtensionLeadsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ExtensionLeadsModel> leadList;
    ExtensionLeadsAdaper leadsAdaper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extension_leads);
        recyclerView = (RecyclerView) findViewById(R.id.re);
        leadList = new ArrayList<>();
        leadsAdaper = new ExtensionLeadsAdaper(leadList,this);
        recyclerView.setAdapter(leadsAdaper);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        fetchData();
//        leadList.add(new ExtensionLeadsModel("Akshit Tyagi","123456","10/02/2018","5 days","2 Months","awaited","4 days left"));
//        leadList.add(new ExtensionLeadsModel("Akshit Tyagi","123456","10/02/2018","5 days","2 Months","awaited","4 days left"));
//        leadList.add(new ExtensionLeadsModel("Akshit Tyagi","123456","10/02/2018","5 days","2 Months","awaited","4 days left"));
//        leadList.add(new ExtensionLeadsModel("Akshit Tyagi","123456","10/02/2018","5 days","2 Months","awaited","4 days left"));
//        leadList.add(new ExtensionLeadsModel("Akshit Tyagi","123456","10/02/2018","5 days","2 Months","awaited","4 days left"));
//        leadsAdaper.notifyDataSetChanged();
    }

    public void fetchData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching data...");
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/extension/";
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
                                        String startdate,userid,username,plan,daysleft,dayspassed,phone;
                                        username = ob.getString("username");
                                        dayspassed = ob.getString("dayspassed");
                                        plan = ob.getString("plan");
                                        daysleft = ob.getString("daysleft");
                                        phone = ob.getString("phone");
                                        userid = String.valueOf(ob.getInt("userid"));
                                        startdate = ob.getString("startdate");
                                        //change the url for upcoming leads, add days left with every client name
                                        leadList.add(new ExtensionLeadsModel(username,userid,startdate,dayspassed,plan,phone,daysleft+" days left"));
                                    }
                                    leadsAdaper.notifyDataSetChanged();
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
