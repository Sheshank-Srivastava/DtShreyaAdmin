package com.dietitianshreya.eatrightdietadmin;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.dietitianshreya.eatrightdietadmin.Utils.VariablesModels;
import com.dietitianshreya.eatrightdietadmin.adapters.AllClientListOthersAdapter;
import com.dietitianshreya.eatrightdietadmin.models.AllClientListOthersModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.dietitianshreya.eatrightdietadmin.Login.MyPREFERENCES;

public class  BookAppointment extends AppCompatActivity {

    EditText appointmentTime,appointmentDate,clientEdit;
    public String Date,type="In Person";
    Spinner appointmentTypeSpinner;
    Button book;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    ArrayList<AllClientListOthersModel> allclients;
    AllClientListOthersAdapter clientAdapter;
    String time24format,id="none";
    String userid,clinicId;
    int searchFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        getSupportActionBar().setTitle("Book Appointment");
        appointmentTime=(EditText) findViewById(R.id.appointmentTimeEdit);
        appointmentDate = (EditText) findViewById(R.id.appointmentDateEdit);
        clientEdit = (EditText) findViewById(R.id.clientEdit);

        SharedPreferences sharedpreferences1 = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        userid= String.valueOf(sharedpreferences1.getInt("clientId",0));
        clinicId= sharedpreferences1.getString("clinicId","0");
        allclients = new ArrayList<>();
        sendR();
        clientEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogContacts();
            }
        });
        appointmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog

                datePickerDialog = new DatePickerDialog(BookAppointment.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                int month = monthOfYear+1;
                                String smonth = month+"";
                                if(month<10)
                                {
                                    smonth = "0"+smonth;
                                }
                                Date = dayOfMonth+"-"+smonth+"-"+year;
                                appointmentDate.setText(Date);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });
        appointmentTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                // timw picker dialog

                timePickerDialog = new TimePickerDialog(BookAppointment.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                String amorpm="a.m.";
                                int hour=i;
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
                                appointmentTime.setText(i+":"+min+" "+amorpm);
                                time24format = hour+":"+i1;
                            }
                        },hour,minute, DateFormat.is24HourFormat(BookAppointment.this));
                timePickerDialog.show();
            }
        });

        appointmentTypeSpinner = (Spinner) findViewById(R.id.appointmentTypeSpinner);
        ArrayAdapter<CharSequence> weight_spinner_adapter = ArrayAdapter.createFromResource(this,R.array.appointmentTypes,android.R.layout.simple_spinner_item);
        weight_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        appointmentTypeSpinner.setAdapter(weight_spinner_adapter);
        appointmentTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Check for type
                type = appointmentTypeSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                type = "In Person";
            }
        });

        book = (Button) findViewById(R.id.book);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(appointmentDate.getText().toString())||TextUtils.isEmpty(appointmentTime.getText().toString().trim())||TextUtils.isEmpty(clientEdit.getText().toString().trim())){
                    Toast.makeText(getApplicationContext(),"Empty fields not allowed!",Toast.LENGTH_SHORT).show();
                }else{
                    Log.d("Time",time24format);
                    bookappointment(Date,time24format,type,id);
                }
            }
        });
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
                                        String name,daysleft,createIn;
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

    public void bookappointment(final String date,final String time,final String type, final String id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Booking Appointment...");
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/bookAppointment/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("res",response);
                        try {
                            progressDialog.dismiss();
                            JSONObject result = new JSONObject(response);
                            if(result.getInt("res")==1) {

                                Toast.makeText(getApplicationContext(),"Appointment booked",Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                //error
                                Toast.makeText(getApplicationContext(),"Some error occurred! Try again",Toast.LENGTH_SHORT).show();
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
                params.put("userId",id);
                params.put(VariablesModels.dietitianId,userid);
                params.put("clinicid",clinicId);
                params.put("date",date);
                params.put("time",time);
                params.put("type",type);
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
                    AllClientListOthersAdapter filteredAdapter = new AllClientListOthersAdapter(filteredList, BookAppointment.this);
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
                    clientEdit.setText(client.getClientName());
                    id = client.getClientId();
                }else{
                    AllClientListOthersModel client = filteredList.get(position);
                    clientEdit.setText(client.getClientName());
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

}
