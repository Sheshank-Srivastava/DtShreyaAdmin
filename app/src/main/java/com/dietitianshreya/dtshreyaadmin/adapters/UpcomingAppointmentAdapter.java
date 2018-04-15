package com.dietitianshreya.dtshreyaadmin.adapters;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dietitianshreya.dtshreyaadmin.ClientDetailActivity;
import com.dietitianshreya.dtshreyaadmin.R;
import com.dietitianshreya.dtshreyaadmin.models.AppointmentDetailsModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class UpcomingAppointmentAdapter extends RecyclerView.Adapter<UpcomingAppointmentAdapter.MyViewHolder> {

    private ArrayList<AppointmentDetailsModel> appointmentDetailsList;
    private Context mCtx;
    EditText date,time;
    String time24format;


    public UpcomingAppointmentAdapter(ArrayList<AppointmentDetailsModel> appointmentDetailsList, Context mCtx) {
        this.appointmentDetailsList = appointmentDetailsList;
        this.mCtx = mCtx;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView time,type,client,daysLeft;
        public ImageView typeImage,status;
        public LinearLayout profileLayout;

        public MyViewHolder(View view) {
            super(view);
            time = (TextView)  view.findViewById(R.id.appointmentTime);
            type = (TextView)  view.findViewById(R.id.appointmentType);
            client = (TextView) view.findViewById(R.id.client);
            status = (ImageView) view.findViewById(R.id.optionsMenu);
            typeImage = (ImageView) view.findViewById(R.id.appointmentTypeImage);
            daysLeft = (TextView) view.findViewById(R.id.daysLeft);
            profileLayout = (LinearLayout) view.findViewById(R.id.profileLayout);
        }
    }


    @Override
    public UpcomingAppointmentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.upcoming_appointment_list_row, parent, false);

        return new UpcomingAppointmentAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UpcomingAppointmentAdapter.MyViewHolder holder,final int position) {

        final AppointmentDetailsModel appointment = appointmentDetailsList.get(position);
        holder.time.setText(appointment.getTime());
        holder.type.setText(appointment.getType());
        holder.daysLeft.setText(appointment.getDaysLeft());
        holder.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(mCtx,view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.appointments_menu,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("Cancel")){
                            sendResponse("canceled",position,appointment.getId());
                        }else if(item.getTitle().equals("Done")){
                            sendResponse("completed",position,appointment.getId());
                        }
                        else if(item.getTitle().equals("Reschedule")){
                            showDialog(position,appointment.getId());
                        }
                        return true;
                    }
                });
            }
        });

        holder.client.setText(appointment.getClient());
        if(appointment.getType().equalsIgnoreCase("in person")||appointment.getType().equalsIgnoreCase("walk-in")){
            //Change TypeImage
        }else{
            //Change TypeImage
        }

    }


    public void showDialog(final int position,final String id){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mCtx);
        // Get the layout inflater
        LayoutInflater linf = LayoutInflater.from(mCtx);

        final View inflator = linf.inflate(R.layout.reschedule_appointment_request, null);
        // Setting Dialog Message
        alertDialog.setMessage("Request Reschedule");
        alertDialog.setView(inflator);
        date = (EditText) inflator.findViewById(R.id.date);
        Calendar calendar = Calendar.getInstance();
        Date c = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        final String dateText = df.format(c);
        date.setText(dateText);
        time = (EditText) inflator.findViewById(R.id.time);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog

                DatePickerDialog datePickerDialog = new DatePickerDialog(mCtx,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                int month = monthOfYear+1;
                                String Date1 = dayOfMonth+"-"+month+"-"+year;
                                date.setText(Date1);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                // timw picker dialog

                TimePickerDialog timePickerDialog = new TimePickerDialog(mCtx,
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
                                time.setText(i+":"+min+" "+amorpm);
                                time24format = i+":"+i1;
                            }
                        },hour,minute, DateFormat.is24HourFormat(mCtx));
                timePickerDialog.show();
            }
        });
        alertDialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!(TextUtils.isEmpty(date.getText().toString().trim())&& TextUtils.isEmpty(time.getText().toString().trim()))){
                    sendR(date.getText().toString().trim(),time24format,id,position);
                }
            }
        });
        alertDialog.show();
    }


    public void sendR(final String date,final String time,final  String id,final int pos) {

        final ProgressDialog progressDialog = new ProgressDialog(mCtx);
        progressDialog.setMessage("Requesting Reschedule...");
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/rescheduledietitian/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject result=new JSONObject();
                        try {
                            result = new JSONObject(response);
                            if(result.getInt("res")==1){
                                Log.d("res",response);
                                progressDialog.dismiss();
                                Log.d("Success","I'm here");
                                appointmentDetailsList.remove(pos);
                                notifyDataSetChanged();
                            }
                            else{
                                progressDialog.dismiss();
                                Toast.makeText(mCtx,result.getString("msg"),Toast.LENGTH_SHORT).show();
                                Log.d("fail","I'm here");
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
                        Toast.makeText(mCtx,"Something went wrong!\nCheck your Internet connection and try again..", Toast.LENGTH_LONG).show();
                        //Toast.makeText(MedicineData.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("date",date+" "+time);
                params.put("id",id);
                return params;
            }
        };

        int MY_SOCKET_TIMEOUT_MS = 50000;
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
        requestQueue.add(stringRequest);
    }

    public void sendResponse(final String action,final int position,final String id) {
        final ProgressDialog progressDialog = new ProgressDialog(mCtx);
        progressDialog.setMessage("Storing response");
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/changeappointmentstatus/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int result;
                        progressDialog.dismiss();
                        //String msg = null;
                        try {
                            JSONObject object = new JSONObject(response);
                            //msg = object.getString("msg");
                            result= object.getInt("res");
                            if(result == 1)
                            {
                                appointmentDetailsList.remove(position);
                                notifyDataSetChanged();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(mCtx,"Response saved successfully",Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(mCtx,"Something went wrong!\nCheck your Internet connection and try again..", Toast.LENGTH_LONG).show();
                        //Toast.makeText(MedicineData.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("appointmentId",id);
                params.put("action",action);

                return params;
            }

        };

        int MY_SOCKET_TIMEOUT_MS = 50000;
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
        requestQueue.add(stringRequest);

    }


    @Override
    public int getItemCount()
    {
        return appointmentDetailsList.size();
    }



}