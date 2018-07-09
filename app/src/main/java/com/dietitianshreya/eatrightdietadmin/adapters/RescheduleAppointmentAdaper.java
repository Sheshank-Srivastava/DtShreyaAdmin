package com.dietitianshreya.eatrightdietadmin.adapters;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dietitianshreya.eatrightdietadmin.ChatActivity;
import com.dietitianshreya.eatrightdietadmin.ClientDetailActivity;
import com.dietitianshreya.eatrightdietadmin.R;
import com.dietitianshreya.eatrightdietadmin.models.RescheduleAppointmentModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class RescheduleAppointmentAdaper extends RecyclerView.Adapter<RescheduleAppointmentAdaper.MyViewHolder> {

    private ArrayList<RescheduleAppointmentModel> appointmentList;
    private Context mCtx;


    public RescheduleAppointmentAdaper(ArrayList<RescheduleAppointmentModel> appointmentList, Context mCtx) {
        this.appointmentList = appointmentList;
        this.mCtx = mCtx;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView clientName,previousDateTime,requestedDateTime,daysLeft;
        LinearLayout accept,decline;
        LinearLayout chat,profileLayout;
        public MyViewHolder(View view) {
            super(view);
            clientName = (TextView)  view.findViewById(R.id.clientName);
            previousDateTime = (TextView)  view.findViewById(R.id.previousDateTime);
            requestedDateTime = (TextView)  view.findViewById(R.id.requestedDateTime);
            accept = (LinearLayout)  view.findViewById(R.id.accept);
            decline = (LinearLayout)  view.findViewById(R.id.decline);
            chat = (LinearLayout) view.findViewById(R.id.chat);
            daysLeft = (TextView) view.findViewById(R.id.daysLeft);
            profileLayout = (LinearLayout) view.findViewById(R.id.profileLayout);
        }
    }


    @Override
    public RescheduleAppointmentAdaper.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reschedule_appointment_list_row, parent, false);

        return new RescheduleAppointmentAdaper.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RescheduleAppointmentAdaper.MyViewHolder holder, final int position) {

        final RescheduleAppointmentModel appointment = appointmentList.get(position);
        holder.clientName.setText(appointment.getClientName());
        holder.previousDateTime.setText(appointment.getAppointmentOn());
        holder.daysLeft.setText(appointment.getDaysLeft());
        holder.requestedDateTime.setText(appointment.getRequestedOn());
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mCtx,"Accepted!",Toast.LENGTH_SHORT).show();
                sendResponse("accept",position,appointment.getId());
            }
        });
        holder.decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mCtx,"Declined!",Toast.LENGTH_SHORT).show();
                sendResponse("decline",position,appointment.getId());
            }
        });
        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mCtx, ChatActivity.class);
                i.putExtra("userId",appointment.getClientId());
                mCtx.startActivity(i);
            }
        });
        holder.profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mCtx, ClientDetailActivity.class);
                i.putExtra("client",appointment.getClientId());
                mCtx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return appointmentList.size();
    }

    public void sendResponse(final String action,final int position,final String id) {
        final ProgressDialog progressDialog = new ProgressDialog(mCtx);
        progressDialog.setMessage("Storing response");
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/reschedulerequestaction/";
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
                                appointmentList.remove(position);
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

}