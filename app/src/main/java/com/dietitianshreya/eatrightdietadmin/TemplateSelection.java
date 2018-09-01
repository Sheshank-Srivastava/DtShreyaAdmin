package com.dietitianshreya.eatrightdietadmin;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
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
import com.dietitianshreya.eatrightdietadmin.adapters.TemplateListAdapter;
import com.dietitianshreya.eatrightdietadmin.models.ChooseMealModel;
import com.dietitianshreya.eatrightdietadmin.models.DietPlanModel;
import com.dietitianshreya.eatrightdietadmin.models.MealModel;
import com.dietitianshreya.eatrightdietadmin.models.TemplateModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TemplateSelection extends AppCompatActivity implements View.OnClickListener {

    private View parent_view;
    ArrayList<DietPlanModel> dietList;
    ArrayList<MealModel> mealList;
    private RecyclerView recyclerView;
    private TemplateListAdapter mAdapter;
    ArrayList<TemplateModel> list = new ArrayList<>();
    ArrayList<TemplateModel> filtered = new ArrayList<>();
    int searchFlag=0;
    String clientId;
    ArrayList<String> dateTxt;
    TemplateListAdapter filteredAdapter;
    CheckBox selectAll;
    CheckBox[] dates = new CheckBox[7];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_selection);
        parent_view = findViewById(android.R.id.content);
        dateTxt = getIntent().getExtras().getStringArrayList("dates");

        initComponent();
    }



    private void initComponent() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Bundle extras = getIntent().getExtras();
        clientId = extras.getString(VariablesModels.userId);
        mAdapter = new TemplateListAdapter(this, list);
        recyclerView.setAdapter(mAdapter);
        fetchData();
        // on item list clicked
        mAdapter.setOnItemClickListener(new TemplateListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String value, int position) {
                if(searchFlag==0) {
//                    fetchTemplateData(list.get(position).getId() + "");
                    showCustomDialog(list.get(position).getId());
                }

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

     void showCustomDialog(final int pos) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater linf = LayoutInflater.from(this);

        final View inflator = linf.inflate(R.layout.dialog_select_date, null);
        // Setting Dialog Message
//        alertDialog.setMessage("Request Reschedule");
        alertDialog.setView(inflator);
        selectAll = inflator.findViewById(R.id.selectAll);
        dates[0] = inflator.findViewById(R.id.date1);
        dates[1] = inflator.findViewById(R.id.date2);
        dates[2] = inflator.findViewById(R.id.date3);
        dates[3] = inflator.findViewById(R.id.date4);
        dates[4] = inflator.findViewById(R.id.date5);
        dates[5] = inflator.findViewById(R.id.date6);
        dates[6] = inflator.findViewById(R.id.date7);
        for(int i=0;i<7;i++){
            dates[i].setText(dateTxt.get(i));
            dates[i].setOnClickListener(this);
        }
        selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectAll.isChecked()){
                    for(int i=0;i<7;i++){
                        dates[i].setChecked(true);
                    }
                }else{
                    for(int i=0;i<7;i++){
                        dates[i].setChecked(false);
                    }
                }
            }
        });
        alertDialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                JSONArray array = new JSONArray();
                for(int it=0;it<7;it++){
                    if(dates[it].isChecked()){
                        array.put(dates[it].getText());
                    }
                }
                sendR(array,pos);
                Log.d("Array date",array+"");
            }
        });
        alertDialog.show();
    }

    void sendR(final JSONArray array,final int pos){
        final ProgressDialog progressDialog = new ProgressDialog(TemplateSelection.this);
        progressDialog.setMessage("Fetching data...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/savediettemplat/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject result = new JSONObject(response);
                            Log.d("REs",response);
                            progressDialog.dismiss();
                            int res= result.getInt("res");
                            if(res==1){
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
                params.put("templateId",pos+"");
                params.put("dietdate",array+"");
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


    @Override
    public void onClick(View view) {
        CheckBox c = (CheckBox)view;
        if(!(c.isChecked())){
            if(selectAll.isChecked()){
                selectAll.setChecked(false);
            }
        }else{
            int flag=0;
            for(int i=0;i<7;i++){
                if(!(dates[i].isChecked())) {
                    flag++;
                }
            }
            if(flag!=0){
                selectAll.setChecked(false);
            }else{
                selectAll.setChecked(true);
            }
        }
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
                                        Log.d("Meal 1",array.getString(i));
                                        meallist1.add(new MealModel(array.getString(i), "early morning", true));
                                    }
                                }

                                if (result.has(VariablesModels.foodtwo)) {
                                    JSONArray array = result.getJSONArray(VariablesModels.foodtwo);
                                    for (int i = 0; i < array.length(); i++) {
                                        Log.d("Meal 2",array.getString(i));
                                        meallist2.add(new MealModel(array.getString(i), " morning", true));
                                    }
                                }

                                if (result.has(VariablesModels.foodthree)) {
                                    JSONArray array = result.getJSONArray(VariablesModels.foodthree);
                                    for (int i = 0; i < array.length(); i++) {
                                        Log.d("Meal 3",array.getString(i));
                                        meallist3.add(new MealModel(array.getString(i), " mid morning", true));
                                    }
                                }

                                if (result.has(VariablesModels.foodfour)) {
                                    JSONArray array = result.getJSONArray(VariablesModels.foodfour);
                                    for (int i = 0; i < array.length(); i++) {
                                        Log.d("Meal 4",array.getString(i));
                                        meallist4.add(new MealModel(array.getString(i), " lunch", true));
                                    }
                                }

                                if (result.has(VariablesModels.foodfive)) {
                                    JSONArray array = result.getJSONArray(VariablesModels.foodfive);
                                    for (int i = 0; i < array.length(); i++) {
                                        Log.d("Meal 5",array.getString(i));
                                        meallist5.add(new MealModel(array.getString(i), " Evening", true));
                                    }
                                }

                                if (result.has(VariablesModels.foodsix)) {
                                    JSONArray array = result.getJSONArray(VariablesModels.foodsix);
                                    for (int i = 0; i < array.length(); i++) {
                                        Log.d("Meal 6",array.getString(i));
                                        meallist6.add(new MealModel(array.getString(i), " Late Evening", true));
                                    }
                                }

                                if (result.has(VariablesModels.foodseven)) {
                                    JSONArray array = result.getJSONArray(VariablesModels.foodseven);
                                    for (int i = 0; i < array.length(); i++) {
                                        Log.d("Meal 7",array.getString(i));
                                        meallist7.add(new MealModel(array.getString(i), " Dinner", true));
                                    }
                                }

                                if (result.has(VariablesModels.foodeight)) {
                                    JSONArray array = result.getJSONArray(VariablesModels.foodeight);
                                    for (int i = 0; i < array.length(); i++) {
                                        Log.d("Meal 8",array.getString(i));
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
            if(filtered!= null)
                filtered.clear();
             filteredAdapter= new TemplateListAdapter(TemplateSelection.this,filtered);
            filteredAdapter.setOnItemClickListener(new TemplateListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, String value, int position) {
//                    fetchTemplateData(filtered.get(position).getId() + "");
                    showCustomDialog(filtered.get(position).getId());
                }
            });
            for (TemplateModel row : list) {

                // name match condition. this might differ depending on your requirement
                // here we are looking for name or phone number match

                if (row.getName().toLowerCase().contains(charSequence.toLowerCase()))  {
                    filtered.add(row);
                }
            }

            recyclerView.setAdapter(filteredAdapter);
            filteredAdapter.notifyDataSetChanged();
        }else

        {
            searchFlag = 0;
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }


}
