package com.dietitianshreya.eatrightdietadmin;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dietitianshreya.eatrightdietadmin.Utils.VariablesModels;
import com.dietitianshreya.eatrightdietadmin.adapters.ChooseMealAdapter;
import com.dietitianshreya.eatrightdietadmin.models.ChooseMealModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChooseMealActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button add;
    ArrayList<ChooseMealModel> mealList;
    ArrayList<ChooseMealModel> fmealList;
    ChooseMealAdapter mealAdapter;
    ArrayList<String> diseases;
    String clientId,tag;
    int searchFlag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_meal);
        Bundle extra = getIntent().getExtras();
        tag=getIntent().getAction();
        diseases = new ArrayList<>();
        diseases = extra.getStringArrayList("diseases");
        clientId = extra.getString("ClientId");

        recyclerView = (RecyclerView) findViewById(R.id.re);
        mealList=new ArrayList<>();
        fmealList=new ArrayList<>();
        mealAdapter = new ChooseMealAdapter(mealList,ChooseMealActivity.this,0,null);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ChooseMealActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(ChooseMealActivity.this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mealAdapter);

        fetchData();
        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ArrayList<ChooseMealModel> list = mealAdapter.getMealList();
                if(list.size()==0){
                    Toast.makeText(getApplicationContext(),"Please select some items",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent returnIntent = new Intent();
                    ArrayList<String> name = new ArrayList<String>();
                    ArrayList<String> quant = new ArrayList<String>();
                    for(int i=0;i<list.size();i++){
                        name.add(list.get(i).getMealName());
                        quant.add(list.get(i).getMealQuant());
                    }
                    returnIntent.putStringArrayListExtra("name",name);
                    returnIntent.putStringArrayListExtra("quant",quant);
                    setResult(RESULT_OK,returnIntent);
                    finish();
                }
            }
        });
    }


    public void fetchData() {
        final ProgressDialog progressDialog = new ProgressDialog(ChooseMealActivity.this);
        progressDialog.setMessage("Fetching data...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/getallfood/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            progressDialog.dismiss();
                            Log.d("Data",response);
                            JSONObject object = new JSONObject(response);
                            int res = object.getInt(VariablesModels.res);
                            String msg= object.getString(VariablesModels.msg);
                            if(res==1) {
                                JSONArray array = object.getJSONArray(VariablesModels.response);
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject innerobject = array.getJSONObject(i);
                                    int id = innerobject.getInt("id");
                                    String name = innerobject.getString("name");
                                    int exempt = innerobject.getInt("exempt");

                                    mealList.add(new ChooseMealModel(name, id + "", exempt));
                                    mealList.get(i).setMealPos(i);
                                }
                            }

                            mealAdapter.notifyDataSetChanged();
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
                params.put(VariablesModels.userId,clientId);
                params.put("tag",tag);
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






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);

        MenuItem searchViewItem = menu.findItem(R.id.actionsearch);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) searchViewItem.getActionView();
        searchView.setQueryHint("Search for Foods.....");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);// Do not iconify the widget; expand it by defaul

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                // This is your adapter that will be filtered
                //Toast.makeText(getApplicationContext(),"textChanged :"+newText,Toast.LENGTH_LONG).show();
                    filter(newText);
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                // **Here you can get the value "query" which is entered in the search box.**
                filter(query);
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);

        return true;

    }



    public void filter(String charSequence)
    {
        if(!(TextUtils.isEmpty(charSequence)))

        {
            searchFlag = 1;
            if(fmealList!= null)
            fmealList.clear();
            ChooseMealAdapter filteredAdapter = new ChooseMealAdapter(fmealList, ChooseMealActivity.this,1,mealAdapter);
            for (ChooseMealModel row : mealList) {

                // name match condition. this might differ depending on your requirement
                // here we are looking for name or phone number match

                if (row.getMealName().toLowerCase().contains(charSequence.toLowerCase()))  {
                    fmealList.add(row);
                }
            }

            recyclerView.setAdapter(filteredAdapter);
            filteredAdapter.notifyDataSetChanged();
        }else

        {
            searchFlag = 0;
            recyclerView.setAdapter(mealAdapter);
            mealAdapter.notifyDataSetChanged();
        }
    }
    }

