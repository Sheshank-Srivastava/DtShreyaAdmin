package com.dietitianshreya.eatrightdietadmin.adapters;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.dietitianshreya.eatrightdietadmin.models.MealChangeModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MealChangeAdapter extends RecyclerView.Adapter<MealChangeAdapter.MyViewHolder> {

    private ArrayList<MealChangeModel> clientList;
    private Context mCtx;

    public MealChangeAdapter(ArrayList<MealChangeModel> clientList, Context mCtx) {
        this.clientList = clientList;
        this.mCtx = mCtx;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView clientName,previousMeal,requestedMeal,mealType,daysLeft;
        LinearLayout accept,decline;
        LinearLayout chat,profileLayout;
        public MyViewHolder(View view) {
            super(view);
            previousMeal = (TextView)  view.findViewById(R.id.previousMeal);
            clientName = (TextView)  view.findViewById(R.id.clientName);
            requestedMeal = (TextView)  view.findViewById(R.id.requestedMeal);
            mealType = (TextView)  view.findViewById(R.id.mealType);
            accept = (LinearLayout) view.findViewById(R.id.accept);
            decline = (LinearLayout) view.findViewById(R.id.decline);
            chat = (LinearLayout) view.findViewById(R.id.chat);
            daysLeft = (TextView) view.findViewById(R.id.daysLeft);
            profileLayout = (LinearLayout) view.findViewById(R.id.profileLayout);
        }
    }

    @Override
    public MealChangeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meal_change_list_row, parent, false);
        return new MealChangeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MealChangeAdapter.MyViewHolder holder, final int position) {

        final MealChangeModel client = clientList.get(position);
        holder.clientName.setText(client.getClientName());
        holder.daysLeft.setText(client.getDaysLeft());
        holder.previousMeal.setText(client.getPreviousMeal());
        holder.mealType.setText(client.getMealType()+" "+client.getMealTime());
        holder.requestedMeal.setText(client.getRequestedMeal());
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchData(client.getMealType(),client.getDate(),"True",position,client.getClientId());
            }
        });
        holder.decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchData(client.getMealType(),client.getDate(),"False",position,client.getClientId());
            }
        });
        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mCtx, ChatActivity.class);
                i.putExtra("clientId",client.getClientId());
                mCtx.startActivity(i);
            }
        });
        holder.profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mCtx, ClientDetailActivity.class);
                i.putExtra("client",client.getClientId());
                mCtx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return clientList.size();
    }

    public void fetchData(final String type, final String dateofdiet, final String Status, final int position, final String id) {
        final ProgressDialog progressDialog = new ProgressDialog(mCtx);
        progressDialog.setMessage("Fetching data...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/acceptmealrequest/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response",response);

                        try {
                            progressDialog.dismiss();
                            JSONObject result = new JSONObject(response);
                            if(result.getInt("res")==1) {

                                clientList.remove(position);
                                notifyDataSetChanged();

                                }else{

                                Toast.makeText(mCtx,"There is some problem",Toast.LENGTH_LONG).show();
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
                params.put("userId",id);
                params.put("meal",type);
                params.put("dateofdiet",dateofdiet);
                params.put("status",Status);
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