package com.dietitianshreya.dtshreyaadmin.adapters;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.dietitianshreya.dtshreyaadmin.ChatActivity;
import com.dietitianshreya.dtshreyaadmin.ClientDetailActivity;
import com.dietitianshreya.dtshreyaadmin.R;
import com.dietitianshreya.dtshreyaadmin.Utils.VariablesModels;
import com.dietitianshreya.dtshreyaadmin.models.ExtensionLeadsModel;
import com.dietitianshreya.dtshreyaadmin.models.RescheduleAppointmentModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ExtensionLeadsAdaper extends RecyclerView.Adapter<ExtensionLeadsAdaper.MyViewHolder> {

    private ArrayList<ExtensionLeadsModel> leadsList;
    private Context mCtx;
EditText date,time;

    public ExtensionLeadsAdaper(ArrayList<ExtensionLeadsModel> leadsList, Context mCtx) {
        this.leadsList = leadsList;
        this.mCtx = mCtx;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView clientName,phone,joinedOn,daysPassed,plan,daysLeft;
        LinearLayout extend,remove;
        LinearLayout chat,profileLayout;
        public MyViewHolder(View view) {
            super(view);
            clientName = (TextView)  view.findViewById(R.id.clientName);
            phone = (TextView)  view.findViewById(R.id.phone);
            joinedOn = (TextView)  view.findViewById(R.id.joinedOn);
            daysPassed = (TextView)  view.findViewById(R.id.daysPassed);
            plan = (TextView)  view.findViewById(R.id.plan);
            extend = (LinearLayout)  view.findViewById(R.id.extend);
            remove = (LinearLayout)  view.findViewById(R.id.remove);
            chat = (LinearLayout) view.findViewById(R.id.chat);
            daysLeft = (TextView) view.findViewById(R.id.daysLeft);
            profileLayout = (LinearLayout) view.findViewById(R.id.profileLayout);
        }
    }


    @Override
    public ExtensionLeadsAdaper.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.extension_leads_list_row, parent, false);

        return new ExtensionLeadsAdaper.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExtensionLeadsAdaper.MyViewHolder holder, final int position) {

        final ExtensionLeadsModel lead = leadsList.get(position);
        holder.clientName.setText(lead.getClientName());
        holder.daysLeft.setText(lead.getDaysLeft());
        holder.phone.setText(lead.getPhone());
        holder.joinedOn.setText(lead.getJoinedOn());
        holder.daysPassed.setText(lead.getDaysPassed());
        holder.plan.setText(lead.getPlan());
        

        holder.extend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(position,lead.getClientId());

            }
        });
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mCtx,"Removed!",Toast.LENGTH_SHORT).show();
                leadsList.remove(position);
                sendRemoveRequest(lead.getClientId());
                notifyDataSetChanged();
            }
        });
        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mCtx, ChatActivity.class);
                i.putExtra("clientId",lead.getClientId());
                mCtx.startActivity(i);
            }
        });
        holder.profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mCtx, ClientDetailActivity.class);
                i.putExtra("client",lead.getClientId());
                mCtx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return leadsList.size();
    }

    public void showDialog(final int position,final String id){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mCtx);
        // Get the layout inflater
        LayoutInflater linf = LayoutInflater.from(mCtx);

        final View inflator = linf.inflate(R.layout.reschedule_appointment_request, null);
        // Setting Dialog Message
        alertDialog.setMessage("Requesting");
        alertDialog.setView(inflator);
        date = (EditText) inflator.findViewById(R.id.date);
        Calendar calendar = Calendar.getInstance();
        Date c = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        final String dateText = df.format(c);
        date.setText(dateText);
        time = (EditText) inflator.findViewById(R.id.time);
        time.setVisibility(View.INVISIBLE);
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

        alertDialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!(TextUtils.isEmpty(date.getText().toString().trim())&& TextUtils.isEmpty(time.getText().toString().trim()))){
                    sendR(date.getText().toString().trim(),id,position);
                }
            }
        });
        alertDialog.show();
    }

    public void sendR(final String date, final  String id, final int pos) {

        final ProgressDialog progressDialog = new ProgressDialog(mCtx);
        progressDialog.setMessage("Requesting .");
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/confirmextension/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject result=new JSONObject();
                        try {

                            progressDialog.dismiss();

                        } catch (Exception e) {
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
                params.put("enddate",date+"");
                params.put("userId",id);
                params.put("pId",id);
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

    public void sendRemoveRequest(final String userid) {

        final ProgressDialog progressDialog = new ProgressDialog(mCtx);
//        progressDialog.setMessage("Requesting .");
//        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/cancelextension/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Cancel response",response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.dismiss();
                        Toast.makeText(mCtx,"Something went wrong!\nCheck your Internet connection and try again..", Toast.LENGTH_LONG).show();
                        //Toast.makeText(MedicineData.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("userId",userid);
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
}