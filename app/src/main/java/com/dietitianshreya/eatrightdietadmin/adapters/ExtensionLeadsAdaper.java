package com.dietitianshreya.eatrightdietadmin.adapters;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.dietitianshreya.eatrightdietadmin.models.ExtensionLeadsModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.dietitianshreya.eatrightdietadmin.Login.MyPREFERENCES;


public class ExtensionLeadsAdaper extends RecyclerView.Adapter<ExtensionLeadsAdaper.MyViewHolder> {

    private ArrayList<ExtensionLeadsModel> leadsList;
    private Context mCtx;
EditText days,amount;

    public ExtensionLeadsAdaper(ArrayList<ExtensionLeadsModel> leadsList, Context mCtx) {
        this.leadsList = leadsList;
        this.mCtx = mCtx;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView clientName,phone,joinedOn,daysPassed,plan,daysLeft,endDate;
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
            endDate = (TextView) view.findViewById(R.id.endDate);
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
        holder.endDate.setText(lead.getEndDate());

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
                i.putExtra("userId",lead.getClientId());
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
        days = (EditText) inflator.findViewById(R.id.days);
        amount = (EditText) inflator.findViewById(R.id.amount);
        alertDialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!(TextUtils.isEmpty(days.getText().toString().trim())&& TextUtils.isEmpty(amount.getText().toString().trim()))){

                    SharedPreferences sharedpreferences1 = mCtx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    String userid= String.valueOf(sharedpreferences1.getInt("clientId",0));
                    ExtensionLeadsModel ex = leadsList.get(position);
                    sendR(days.getText().toString().trim(),id,amount.getText().toString().trim(),ex.getClinicId(),ex.getProgramId(),userid,position);
                }
            }
        });
        alertDialog.show();
    }

    public void sendR(final String days, final  String userId, final String amount,final String clinicId,final String programId,final String dietitianId,final int position) {

        final ProgressDialog progressDialog = new ProgressDialog(mCtx);
        progressDialog.setMessage("Requesting Extension...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/confirmextension/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject result=new JSONObject();
                        Log.d("extension response",response);
                        try {

                            progressDialog.dismiss();
                            if(result.getInt("res")==1) {
                                Toast.makeText(mCtx, "Extension confirmed", Toast.LENGTH_SHORT).show();
                                leadsList.remove(position);
                                notifyDataSetChanged();
                            }else {
                                Toast.makeText(mCtx, "Some error occured! Try again later.", Toast.LENGTH_SHORT).show();
                            }
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
                params.put("userId",userId);
                params.put("programId",programId);
                params.put("dietitianId",dietitianId);
                params.put("clinicId",clinicId);
                params.put("days",days);
                params.put("amount",amount);
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