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
            breakfast,mid_morning,lunch,evening,late_evening,dinner,post_dinner,supplements;

    String wakeupTime_str,sleepTime_str,waterIntake_str,greenTea_str,pooped_str,early_morning_str,weight_str,
            breakfast_str,mid_morning_str,lunch_str,evening_str,late_eve_str,dinner_str,post_dinner_str,supplements_str;


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
        Log.d("Client ID",clientId);
        dateStr = extras.getString("day");
        Log.d("Day",dateStr);
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
        late_evening = (TextView)findViewById(R.id.late_eve_id);
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

                            Log.d("Diary",response);
                            JSONObject object = new JSONObject(response);
                            if (object.has("response")) {
//                                extravalues(response);
//                                {"response": {"sleeptime": "00:00", "wakeuptime": "00:00", "pooped": null, "waterintake": null,
//                                        "greentea": null, "todayweight": null,
//                                        "dietdata": {"foodfour": ",Steamed dahi bhalle,Overnight Gluten free oats",
//                                        "foodthree": ",pear,litchi", "foodtwo": ",Peach smoothie,Anar raita"}}
//                                    , "res": 1, "msg": "Diary"}
//
//
//                                fooddata(response);
                                if(object.getInt("res")==1){
                                    JSONObject result = object.getJSONObject("response");
                                    sleepTime.setText("Wake Up Time : "+result.getString("sleeptime"));
                                    wakeupTime.setText("Sleep Time : "+result.getString("wakeuptime"));
                                    pooped.setText("Pooped: "+result.getString("pooped")+" times");
                                    waterIntake.setText("Water Intake : "+result.getString("waterintake")+" Glasses");
                                    greenTea.setText("Green tea : "+result.getString("greentea")+" Cups");
                                    todaysweight.setText("Todays's Weight : "+result.getString("todayweight"));
                                    JSONObject diet = result.getJSONObject("dietdata");
                                    if(diet.has("foodone"))
                                        early_morning.setText(diet.getString("foodone"));
                                    if(diet.has("foodtwo"))
                                        breakfast.setText(diet.getString("foodtwo"));
                                    if(diet.has("foodthree"))
                                        mid_morning.setText(diet.getString("foodthree"));
                                    if(diet.has("foodfour"))
                                        lunch.setText(diet.getString("foodfour"));
                                    if(diet.has("foodfive"))
                                        evening.setText(diet.getString("foodfive"));
                                    if(diet.has("foodsix"))
                                        late_evening.setText(diet.getString("foodsix"));
                                    if(diet.has("foodseven"))
                                        dinner.setText(diet.getString("foodseven"));
                                    if(diet.has("foodeight"))
                                        post_dinner.setText(diet.getString("foodeight"));

                                }

                            }
                            else
                            {
                                //data no available image
                            }
                        }
                        catch (JSONException e) {
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
