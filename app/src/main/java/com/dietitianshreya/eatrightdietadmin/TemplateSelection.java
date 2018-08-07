package com.dietitianshreya.eatrightdietadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
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
import com.dietitianshreya.eatrightdietadmin.models.DietPlanModel;
import com.dietitianshreya.eatrightdietadmin.models.MealModel;
import com.dietitianshreya.eatrightdietadmin.models.TemplateModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TemplateSelection extends AppCompatActivity {

    private View parent_view;
    ArrayList<DietPlanModel> dietList;
    ArrayList<MealModel> mealList;
    private RecyclerView recyclerView;
    private AdapterGridSingleLine mAdapter;
    ArrayList<TemplateModel> list = new ArrayList<>();
    String clientId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_selection);
        parent_view = findViewById(android.R.id.content);


        initComponent();
    }



    private void initComponent() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(this, 3), true));
        recyclerView.setHasFixedSize(true);
        Bundle extras = getIntent().getExtras();

        clientId = extras.getString(VariablesModels.userId);
        mAdapter = new AdapterGridSingleLine(this, list);
        recyclerView.setAdapter(mAdapter);
        fetchData();
        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterGridSingleLine.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String value, int position) {
                fetchTemplateData(list.get(position).getId()+"");

              /*  Intent returnIntent = new Intent();
                ArrayList<String> name = new ArrayList<String>();
                ArrayList<String> quant = new ArrayList<String>();
                for(int i=0;i<list.size();i++){
                    name.add(list.get(i).getMealName());
                    quant.add(list.get(i).getMealQuant());
                }
                returnIntent.putStringArrayListExtra("name",name);
                returnIntent.putStringArrayListExtra("quant",quant);
                setResult(RESULT_OK,returnIntent);
                finish();*/

            }


        });

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    public void fetchData() {
        final ProgressDialog progressDialog = new ProgressDialog(TemplateSelection.this);
        progressDialog.setMessage("Fetching data...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/templatename/";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject result = new JSONObject(response);
                            Log.d("REs",response);
                            progressDialog.dismiss();
                            int res= result.getInt("res");
                            JSONArray array = new JSONArray();
                            array = result.getJSONArray("response");
                            for(int i=0;i<array.length();i++)
                            { JSONObject object = array.getJSONObject(i);
                                String name= object.getString("templatename");
                                int id = object.getInt("templateId");

                                list.add(new TemplateModel(id,name));

                            }
                            mAdapter.notifyDataSetChanged();

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


    public void fetchTemplateData(final String name) {
        final ProgressDialog progressDialog = new ProgressDialog(TemplateSelection.this);
        progressDialog.setMessage("Fetching data...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/getdiettemplate/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("teamplate",response);
                        ArrayList<MealModel> meallist1= new ArrayList<>();
                        ArrayList<MealModel> meallist2= new ArrayList<>();
                        ArrayList<MealModel> meallist3= new ArrayList<>();
                        ArrayList<MealModel> meallist4= new ArrayList<>();
                        ArrayList<MealModel> meallist5= new ArrayList<>();
                        ArrayList<MealModel> meallist6= new ArrayList<>();
                        ArrayList<MealModel> meallist7= new ArrayList<>();
                        ArrayList<MealModel> meallist8= new ArrayList<>();
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.getInt(VariablesModels.res)==1) {
                                Log.d("REs", response);
                                JSONObject result = object.getJSONObject(VariablesModels.response);
                                progressDialog.dismiss();
                                if (result.has(VariablesModels.foodone)) {
                                    JSONArray array = result.getJSONArray(VariablesModels.foodone);
                                    for (int i = 0; i < array.length(); i++) {
                                        meallist1.add(new MealModel(array.getString(i), "early morning", true));
                                    }
                                }

                                if (result.has(VariablesModels.foodtwo)) {
                                    JSONArray array = result.getJSONArray(VariablesModels.foodtwo);
                                    for (int i = 0; i < array.length(); i++) {
                                        meallist2.add(new MealModel(array.getString(i), " morning", true));
                                    }
                                }

                                if (result.has(VariablesModels.foodthree)) {
                                    JSONArray array = result.getJSONArray(VariablesModels.foodthree);
                                    for (int i = 0; i < array.length(); i++) {
                                        meallist3.add(new MealModel(array.getString(i), " mid morning", true));
                                    }
                                }

                                if (result.has(VariablesModels.foodfour)) {
                                    JSONArray array = result.getJSONArray(VariablesModels.foodfour);
                                    for (int i = 0; i < array.length(); i++) {
                                        meallist4.add(new MealModel(array.getString(i), " lunch", true));
                                    }
                                }

                                if (result.has(VariablesModels.foodfive)) {
                                    JSONArray array = result.getJSONArray(VariablesModels.foodfive);
                                    for (int i = 0; i < array.length(); i++) {
                                        meallist5.add(new MealModel(array.getString(i), " Evening", true));
                                    }
                                }

                                if (result.has(VariablesModels.foodsix)) {
                                    JSONArray array = result.getJSONArray(VariablesModels.foodsix);
                                    for (int i = 0; i < array.length(); i++) {
                                        meallist6.add(new MealModel(array.getString(i), " Late Evening", true));
                                    }
                                }

                                if (result.has(VariablesModels.foodseven)) {
                                    JSONArray array = result.getJSONArray(VariablesModels.foodseven);
                                    for (int i = 0; i < array.length(); i++) {
                                        meallist7.add(new MealModel(array.getString(i), " Dinner", true));
                                    }
                                }

                                if (result.has(VariablesModels.foodeight)) {
                                    JSONArray array = result.getJSONArray(VariablesModels.foodseven);
                                    for (int i = 0; i < array.length(); i++) {
                                        meallist8.add(new MealModel(array.getString(i), " Post dinner", true));
                                    }
                                }

                                Intent returnIntent = new Intent();

                                returnIntent.putParcelableArrayListExtra("meal1", meallist1);
                                returnIntent.putParcelableArrayListExtra("meal2", meallist2);
                                returnIntent.putParcelableArrayListExtra("meal3", meallist3);
                                returnIntent.putParcelableArrayListExtra("meal4", meallist4);
                                returnIntent.putParcelableArrayListExtra("meal5", meallist5);
                                returnIntent.putParcelableArrayListExtra("meal6", meallist6);
                                returnIntent.putParcelableArrayListExtra("meal7", meallist7);
                                returnIntent.putParcelableArrayListExtra("meal8", meallist8);


                                setResult(RESULT_OK, returnIntent);
                                finish();
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
                params.put("templateId",name);
                params.put(VariablesModels.userId,clientId);
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
