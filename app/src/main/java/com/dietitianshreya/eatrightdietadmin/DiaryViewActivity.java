package com.dietitianshreya.eatrightdietadmin;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
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
import com.dietitianshreya.eatrightdietadmin.Utils.VariablesModels;
import com.dietitianshreya.eatrightdietadmin.adapters.DiaryViewAdapter;
import com.dietitianshreya.eatrightdietadmin.models.DiaryEntryModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DiaryViewActivity extends AppCompatActivity {
    String MyPREFERENCES = "MyPrefs" ;
    String clientId,dateStr;
    TextView wakeupTime,sleepTime,waterIntake,greenTea,pooped,early_morning,todaysweight,
            breakfast,mid_morning,lunch,evening,dinner,post_dinner,supplements;

    String wakeupTime_str,sleepTime_str,waterIntake_str,greenTea_str,pooped_str,early_morning_str,weight_str,
            breakfast_str,mid_morning_str,lunch_str,evening_str,dinner_str,post_dinner_str,supplements_str;


    RecyclerView recyclerView;
    TextView date,day,monthYear;
    ArrayList<DiaryEntryModel> diaryList;
    DiaryViewAdapter diaryViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_view);

        Bundle extras = getIntent().getExtras();
        clientId = extras.getString(VariablesModels.userId);
        dateStr = extras.getString("day");
        wakeupTime=(TextView)findViewById(R.id.wakeupTime);
        sleepTime=(TextView)findViewById(R.id.sleepTime);
        waterIntake=(TextView)findViewById(R.id.waterIntake);
        greenTea= (TextView)findViewById(R.id.greenTea);
        pooped= (TextView)findViewById(R.id.pooped);
        early_morning = (TextView)findViewById(R.id.early_id);
        breakfast = (TextView)findViewById(R.id.break_id);
        mid_morning =(TextView) findViewById(R.id.mid_id);
        lunch = (TextView)findViewById(R.id.lunch_id);
        evening = (TextView)findViewById(R.id.eve_id);
        dinner = (TextView)findViewById(R.id.dinner_id);
        post_dinner = (TextView)findViewById(R.id.post_id);
        supplements = (TextView)findViewById(R.id.supplements_id);
        todaysweight = (TextView)findViewById(R.id.todaysweight);

        getDiarydata();

    }


    public void getDiarydata()
    {
        final ProgressDialog progressDialog = new ProgressDialog(DiaryViewActivity.this);
        progressDialog.setMessage("Updating data...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/getdatediary/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {

                            JSONObject object = new JSONObject(response);
                            String msg = object.getString(VariablesModels.msg);
                            int res= object.getInt(VariablesModels.res);
                            Log.d("manya",response);
                            if(res==1){
                            JSONObject innerobject = object.getJSONObject(VariablesModels.response);
                            if(res==1) {
                                if (!innerobject.getString(VariablesModels.sleeptime).equals(null)) {
                                    sleepTime_str = innerobject.getString(VariablesModels.sleeptime);
                                }
                                if (!innerobject.getString(VariablesModels.wakeuptime).equals(null)) {
                                    wakeupTime_str = innerobject.getString(VariablesModels.wakeuptime);
                                }

                                waterIntake_str = innerobject.getInt(VariablesModels.waterintake) + "";
                                pooped_str = innerobject.getInt(VariablesModels.pooped) + "";
                                greenTea_str = innerobject.getInt(VariablesModels.greentea) + "";
                                weight_str = innerobject.getString(VariablesModels.todayweight) + "";

                                JSONObject dietobject = innerobject.getJSONObject(VariablesModels.dietdata);
                                if (dietobject.has(VariablesModels.foodone)) {
                                    early_morning_str = dietobject.getString(VariablesModels.foodone);
                                }
                                if (dietobject.has(VariablesModels.foodtwo)) {
                                    breakfast_str = dietobject.getString(VariablesModels.foodtwo);
                                }
                                if (dietobject.has(VariablesModels.foodthree)) {
                                    mid_morning_str = dietobject.getString(VariablesModels.foodthree);
                                }
                                if (dietobject.has(VariablesModels.foodfour)) {
                                    lunch_str = dietobject.getString(VariablesModels.foodfour);
                                }
                                if (dietobject.has(VariablesModels.foodfive)) {
                                    evening_str = dietobject.getString(VariablesModels.foodfive);
                                }

                                if (dietobject.has(VariablesModels.foodsix)) {
                                    dinner_str = dietobject.getString(VariablesModels.foodsix);
                                }
                                if (dietobject.has(VariablesModels.foodseven)) {
                                    post_dinner_str = dietobject.getString(VariablesModels.foodseven);
                                }
                                if (dietobject.has(VariablesModels.foodeight)) {
                                    supplements_str = dietobject.getString(VariablesModels.foodeight);
                                }
                            }

                            }

                            wakeupTime.setText(wakeupTime_str);
                            sleepTime.setText(sleepTime_str);
                            waterIntake.setText(waterIntake_str);
                            pooped.setText(pooped_str);
                            greenTea.setText(greenTea_str);
                            todaysweight.setText(weight_str);
                            early_morning.setText(early_morning_str);
                            breakfast.setText(breakfast_str);
                            mid_morning.setText(mid_morning_str);
                            lunch.setText(lunch_str);
                            evening.setText(evening_str);
                            dinner.setText(dinner_str);
                            post_dinner.setText(post_dinner_str);


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
                params.put(VariablesModels.userId,clientId+"");
                params.put(VariablesModels.date,dateStr);
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
