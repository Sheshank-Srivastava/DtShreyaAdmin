package com.dietitianshreya.eatrightdietadmin;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dietitianshreya.eatrightdietadmin.Utils.VariablesModels;
import com.dietitianshreya.eatrightdietadmin.adapters.AllClientListOthersAdapter;
import com.dietitianshreya.eatrightdietadmin.adapters.AllClientsCompoundAdapter;
import com.dietitianshreya.eatrightdietadmin.models.AllClientListOthersModel;
import com.dietitianshreya.eatrightdietadmin.models.AllClientsCompoundModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.dietitianshreya.eatrightdietadmin.Login.MyPREFERENCES;

public class PendingDietCreationActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<AllClientsCompoundModel> clientList,filterclientList;

    ArrayList<AllClientListOthersModel> pendingdiet,otherclients,pendingfilterlist,otherclientfilterlist;
    AllClientListOthersAdapter detailsAdapter;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_diet_creation);
        getSupportActionBar().setTitle("Pending Diet Creation");
        recyclerView = (RecyclerView) findViewById(R.id.re);
        clientList=new ArrayList<>();
        filterclientList = new ArrayList<>();
        SharedPreferences sharedpreferences1 = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        userid= String.valueOf(sharedpreferences1.getInt("clientId",0));
//        clientListAdapter = new AllClientsCompoundAdapter(clientList,this);
//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(clientListAdapter);
        pendingdiet = new ArrayList<>();
        otherclients = new ArrayList<>();
        pendingfilterlist = new ArrayList<>();
        otherclientfilterlist = new ArrayList<>();



        detailsAdapter= new AllClientListOthersAdapter(pendingdiet,this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(detailsAdapter);
        detailsAdapter.notifyDataSetChanged();


        sendR();
    }

    public void sendR() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Clients...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/getclients/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            progressDialog.dismiss();
                            JSONObject result = new JSONObject(response);
                            Log.d("mytag",response);
                            if(result.getInt("res")==1) {
                                JSONArray ar = result.getJSONArray("response");
                                if (ar.length() != 0){
                                    for (int i = 0; i < ar.length(); i++) {
                                        JSONObject ob = ar.getJSONObject(i);
                                        String name,id,daysleft,createIn;
                                        int creatinInt;
                                        name = ob.getString(VariablesModels.user_name);
                                        id = ob.getInt(VariablesModels.userId)+"";
                                        daysleft = ob.getInt("daysleft")+" days left";
                                        creatinInt = ob.getInt("createIn");
                                        if(creatinInt<=3){
                                            pendingdiet.add(new AllClientListOthersModel(name,id,String.valueOf(creatinInt),daysleft));
                                        }
                                    }
                                    detailsAdapter.notifyDataSetChanged();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem searchViewItem = menu.findItem(R.id.actionsearch);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) searchViewItem.getActionView();
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
        return super.onCreateOptionsMenu(menu);
    }

    public void filter(String charSequence)
    {
        if(!(TextUtils.isEmpty(charSequence)))

        {
            if(filterclientList!=null){
                filterclientList.clear();
            }
            if(pendingfilterlist!= null)
                pendingfilterlist.clear();
            if(otherclientfilterlist!=null){
                otherclientfilterlist.clear();
            }
            AllClientsCompoundAdapter filteredAdapter = new AllClientsCompoundAdapter(filterclientList,this);
            for (AllClientListOthersModel row : pendingdiet) {

                // name match condition. this might differ depending on your requirement
                // here we are looking for name or phone number match

                if (row.getClientName().toLowerCase().contains(charSequence.toLowerCase()))  {
                    pendingfilterlist.add(row);
                }
            }
            for(AllClientListOthersModel row : otherclients){
                if (row.getClientName().toLowerCase().contains(charSequence.toLowerCase()))  {
                    otherclientfilterlist.add(row);
                }
            }

            if(pendingfilterlist.size()>0)
                filterclientList.add(new AllClientsCompoundModel("Pending Diet Creation", pendingfilterlist));
            if(otherclientfilterlist.size()>0)
                filterclientList.add(new AllClientsCompoundModel("Other Clients",otherclientfilterlist));
            recyclerView.setAdapter(filteredAdapter);
            filteredAdapter.notifyDataSetChanged();
        }else

        {
            recyclerView.setAdapter(detailsAdapter);
            detailsAdapter.notifyDataSetChanged();
        }
    }
}
