package com.dietitianshreya.dtshreyaadmin;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dietitianshreya.dtshreyaadmin.Utils.VariablesModels;
import com.dietitianshreya.dtshreyaadmin.adapters.AllClientListOthersAdapter;
import com.dietitianshreya.dtshreyaadmin.adapters.DiseaseAdapter;
import com.dietitianshreya.dtshreyaadmin.models.AllClientListOthersModel;
import com.dietitianshreya.dtshreyaadmin.models.DiseaseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.dietitianshreya.dtshreyaadmin.Login.MyPREFERENCES;


public class BookTest extends AppCompatActivity {

    EditText test_time,test_date,test_notes,test_name,test_client;
    public String Date,type="In Person";
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    ArrayList<AllClientListOthersModel> allclients;
    AllClientListOthersAdapter clientAdapter;
    String time24format,id="none";
    String notes,test_text;
    int searchFlag = 0;
    Button bookTest;
    String userid;
    ArrayList<DiseaseModel> testList;
    DiseaseAdapter testAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_test);
        SharedPreferences sharedpreferences1 = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        userid= String.valueOf(sharedpreferences1.getInt("clientId",0));
        test_client = (EditText) findViewById(R.id.TestClientName);
        test_time= (EditText) findViewById(R.id.testTime);
        test_date = (EditText) findViewById(R.id.testDate);
        test_name = (EditText) findViewById(R.id.testName);
        test_notes= (EditText) findViewById(R.id.testNotes);
        bookTest= (Button) findViewById(R.id.booktestbutton);
        allclients = new ArrayList<>();
        sendR();
        bookTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notes= test_notes.getText().toString();
                Log.d("notes",notes);
                PostTestData();
            }
        });
        test_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogContacts();
            }
        });
            String testname=test_name.getText().toString();

        test_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                // timw picker dialog

                timePickerDialog = new TimePickerDialog(BookTest.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                String amorpm="a.m.";
                                String min;
                                if(i>12){
                                    amorpm="p.m.";
                                    i-=12;
                                }
                                if(i==12){
                                    amorpm="p.m.";
                                }
                                if(i==24){
                                    amorpm="a.m.";
                                }
                                if(i1<10){
                                    min="0"+i1;
                                }
                                else{
                                    min=""+i1;
                                }
                                test_time.setText(i+":"+min+" "+amorpm);
                                time24format = i+":"+i1;
                            }
                        },hour,minute, DateFormat.is24HourFormat(BookTest.this));
                timePickerDialog.show();
            }
        });

        test_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog

                datePickerDialog = new DatePickerDialog(BookTest.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                int month = monthOfYear+1;
                                Date = dayOfMonth+"-"+month+"-"+year;
                                test_date.setText(Date);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        test_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                testList = new ArrayList<>();
                getTestData();
            }
        });





    }


    public void PostTestData() {
        final ProgressDialog progressDialog = new ProgressDialog(BookTest.this);
        progressDialog.setMessage("Fetching data...");
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/booktest/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject object = new JSONObject(response);
                            String message= object.getString("msg");
                            int res= object.getInt("res");
                            if(res ==1){

                                Intent i = new Intent(BookTest.this,TestBooking.class);
                                startActivity(i);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext()," There is some error",Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getApplicationContext(),"Something went wrong!", Toast.LENGTH_LONG).show();
                        //Toast.makeText(MedicineData.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put(VariablesModels.dietitianId,userid);
                Log.d(VariablesModels.dietitianId,userid);
                params.put(VariablesModels.userId,id);
                params.put("time",time24format);
                params.put("date",test_date.getText().toString());
                params.put("notes",notes);
                params.put("testname",test_name.getText().toString());
                params.put("clinicid","3");

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


    private void dialogContacts() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_clients);
        dialog.setCancelable(true);
        searchFlag=0;
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        final ArrayList<AllClientListOthersModel> filteredList = new ArrayList<AllClientListOthersModel>();
        final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        clientAdapter = new AllClientListOthersAdapter(allclients,this);
        recyclerView.setAdapter(clientAdapter);
        clientAdapter.notifyDataSetChanged();
        EditText search = (EditText) dialog.findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("before text changed",charSequence+"");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("on text changed",charSequence+"");
                if(!(TextUtils.isEmpty(charSequence))) {
                    searchFlag=1;
                    filteredList.clear();
                    AllClientListOthersAdapter filteredAdapter = new AllClientListOthersAdapter(filteredList, BookTest.this);
                    for (AllClientListOthersModel row : allclients) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        String charString = charSequence + "";
                        if (row.getClientName().toLowerCase().contains(charString.toLowerCase()) || row.getClientId().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }
                    recyclerView.setAdapter(filteredAdapter);
                    filteredAdapter.notifyDataSetChanged();
                }else{
                    searchFlag=0;
                    recyclerView.setAdapter(clientAdapter);
                    clientAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("after text changed",editable.toString());
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) throws NoSuchFieldException, IllegalAccessException {
                if(searchFlag==0) {
                    AllClientListOthersModel client = allclients.get(position);
                    test_client.setText(client.getClientName());
                    Log.d("manya",client.getClientId());
                    id = client.getClientId();
                }else{
                    AllClientListOthersModel client = filteredList.get(position);
                    test_client.setText(client.getClientName());
                    id = client.getClientId();
                }
                dialog.dismiss();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    public void sendR() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Clients...");
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/getclients/";
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
                                        String name,id,daysleft,createIn;
                                        int creatinInt;
                                        name = ob.getString(VariablesModels.user_name);
                                        id = String.valueOf(ob.getInt(VariablesModels.userId));
                                        daysleft = ob.getInt("daysleft")+" days left";
                                       // creatinInt = ob.getInt("createIn");
                                        allclients.add(new AllClientListOthersModel(name,id,"na",daysleft));

                                    }


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
                params.put(VariablesModels.dietitianId,userid);
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


    public void getTestData() {
        final ProgressDialog progressDialog = new ProgressDialog(BookTest.this);
        progressDialog.setMessage("Fetching data...");
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/gettest/";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                            try {

                                Log.d("res", response);
                                JSONObject object = new JSONObject(response);
                                String msg = object.getString("msg");
                                int res = object.getInt("res");
                                if (res == 1) {
                                    JSONArray array = object.getJSONArray(VariablesModels.response);
                                    for (int i = 0; i < array.length(); i++) {
                                        testList.add(new DiseaseModel(array.getString(i)));
                                    }
                                    Log.d("lolwa", testList.size() + "");
                                }

                                dialogtest();
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
                        Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                        //Toast.makeText(MedicineData.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }) {





        };

        int MY_SOCKET_TIMEOUT_MS = 50000;
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


    private void dialogtest() {
        final Dialog dialog = new Dialog(this);
        Log.d("lol",testList.size()+"");
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_clients);
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        final ArrayList<DiseaseModel> filteredList = new ArrayList<DiseaseModel>();
        final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        testAdapter= new DiseaseAdapter(testList,this);
        // Log.d("lol",diseaseList.get(1).getDiseaseName());

        recyclerView.setAdapter(testAdapter);
        testAdapter.notifyDataSetChanged();
        EditText search = (EditText) dialog.findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("before text changed",charSequence+"");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("on text changed",charSequence+"");
                if(!(TextUtils.isEmpty(charSequence))) {
                    searchFlag=1;
                    filteredList.clear();
                    DiseaseAdapter filteredAdapter = new DiseaseAdapter(filteredList, BookTest.this);
                    for (DiseaseModel row : testList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        String charString = charSequence + "";
                        if (row.getDiseaseName().toLowerCase().contains(charString.toLowerCase()) ) {
                            filteredList.add(row);
                        }
                    }
                    recyclerView.setAdapter(filteredAdapter);
                    filteredAdapter.notifyDataSetChanged();
                }else{
                    searchFlag=0;
                    recyclerView.setAdapter(testAdapter);
                    testAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("after text changed",editable.toString());
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) throws NoSuchFieldException, IllegalAccessException {
                if(searchFlag==0) {
                    DiseaseModel client = testList.get(position);
                    //test_client.setText(client.getDiseaseName());
                    if(test_text==null)
                    {
                        test_text= client.getDiseaseName();
                        test_name.setText(test_text);

                    }
                    else {
                        test_text = test_text + " + " + client.getDiseaseName();
                        test_name.setText(test_text);
                    }
                    //id = client.getClientId();
                }else{
                    DiseaseModel client = filteredList.get(position);
                    if(test_text==null)
                    {
                        test_text= client.getDiseaseName();
                        test_name.setText(test_text);
                    }
                    else {
                        test_text = test_text + " + " + client.getDiseaseName();
                        test_name.setText(test_text);
                    }
                    // test_client.setText(client.getDiseaseName());
                    // id = client.getClientId();
                }
                dialog.dismiss();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

}
