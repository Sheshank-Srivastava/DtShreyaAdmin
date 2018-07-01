package com.dietitianshreya.eatrightdietadmin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dietitianshreya.eatrightdietadmin.Utils.VariablesModels;
import com.dietitianshreya.eatrightdietadmin.adapters.MealChangeAdapter;
import com.dietitianshreya.eatrightdietadmin.models.MealChangeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.dietitianshreya.eatrightdietadmin.Login.MyPREFERENCES;

public class MealChangeRequestActivty extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<MealChangeModel> clientList;
    MealChangeAdapter mealChangeAdapter;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_change_request);
        recyclerView = (RecyclerView) findViewById(R.id.re);
        clientList = new ArrayList<>();
        SharedPreferences sharedpreferences1 = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        userid= String.valueOf(sharedpreferences1.getInt("clientId",0));
        mealChangeAdapter = new MealChangeAdapter(clientList,MealChangeRequestActivty.this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setAdapter(mealChangeAdapter);
        recyclerView.setLayoutManager(manager);

        fetchData();

    }


    public void fetchData() {
        final ProgressDialog progressDialog = new ProgressDialog(MealChangeRequestActivty.this);
        progressDialog.setMessage("Fetching data...");
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/mealrequestview/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.d("response",response);
                            progressDialog.dismiss();

                            JSONObject outerobject = new JSONObject(response);
                            int res=outerobject.getInt("res");
                            if(res==1) {
                                JSONArray array = outerobject.getJSONArray("response");

                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.getJSONObject(i);
                                    JSONArray food= object.getJSONArray("food");

                                    String type = object.getString("type");
                                    String date = object.getString("dietdate");
                                    String user = object.getString(VariablesModels.user_name);
                                    String id = object.getString(VariablesModels.userId);
                                    JSONArray newfood = object.getJSONArray("rfood");
                                   // String daysleft = object.getString("daysleft");

                                    clientList.add(new MealChangeModel(user, id, food+"", newfood+"", type, "awaited", "10 A.M.",  " days left",date));


                                }
                            }
                            mealChangeAdapter.notifyDataSetChanged();

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
