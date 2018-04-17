package com.dietitianshreya.dtshreyaadmin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dietitianshreya.dtshreyaadmin.adapters.AppointmentsAdapter;
import com.dietitianshreya.dtshreyaadmin.models.AppointmentDetailsModel;
import com.dietitianshreya.dtshreyaadmin.models.AppointmentsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DashboardFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    RecyclerView recyclerView;
    AppointmentsAdapter appointmentsAdapter;
    ArrayList<AppointmentsModel> appointmentsList;
    TextView viewAllAppointments,viewAllMealChangeRequest,viewAllRescheduleRequest,viewAllExtensionLeads,viewAllFinalMonthUsers;

    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_dashboard, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.re);
        viewAllAppointments = (TextView) rootView.findViewById(R.id.viewAllAppointments);
        viewAllMealChangeRequest = (TextView) rootView.findViewById(R.id.viewAllMealChangeRequest);
        viewAllRescheduleRequest = (TextView) rootView.findViewById(R.id.viewAllRescheduleRequest);
        viewAllExtensionLeads = (TextView) rootView.findViewById(R.id.viewAllExtensionLeads);
        viewAllFinalMonthUsers = (TextView) rootView.findViewById(R.id.viewAllFinalMonthUsers);
        appointmentsList=new ArrayList<>();
        appointmentsAdapter = new AppointmentsAdapter(appointmentsList,getActivity());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(appointmentsAdapter);
        fetchData();
//        appointmentsList.add(new AppointmentsModel("Mr. Akshit Tyagi","9:30 A.M.","Punjabi Bagh","4 days left"));
//        appointmentsList.add(new AppointmentsModel("Mr. Paras Garg","11:30 A.M.","Punjabi Bagh","4 days left"));
//        appointmentsList.add(new AppointmentsModel("Ms. Manya Madan","12:30 P.M.","Skype call","4 days left"));
//        appointmentsAdapter.notifyDataSetChanged();
        viewAllAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),UpcomingAppointmentsActivty.class));
            }
        });
        viewAllMealChangeRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),MealChangeRequestActivty.class));
            }
        });
        viewAllRescheduleRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),RescheduleAppointments.class));
            }
        });
        viewAllExtensionLeads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),ExtensionLeadsActivity.class));
            }
        });
        viewAllFinalMonthUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),LastMonthUsers.class));
            }
        });
        return rootView;
    }

    public void fetchData() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching data...");
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/upcomingappointments/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            progressDialog.dismiss();
                            JSONObject result = new JSONObject(response);
                            if(result.getInt("res")==1) {
                                JSONArray ar = result.getJSONArray("response");
                                int count = result.getInt("count");
                                if(count >3){
                                    count=3;
                                }
                                if (ar.length() != 0){
                                    for (int i = 0; i < count; i++) {
                                        JSONObject ob = ar.getJSONObject(i);
                                        String type,time,daysleft,status,id,userid,username;
                                        type = ob.getString("type");
                                        time = ob.getString("time");
                                        int hour = Integer.parseInt(time.split(":")[0]);
                                        String ampm = "A.M.";
                                        if(hour>12){
                                            hour-=12;
                                            ampm="P.M.";
                                        }
                                        String newtime = hour + ":" + time.split(":")[1]+" "+ampm;
                                        id = String.valueOf(ob.getInt("id"));
                                        username = ob.getString("username");
                                        daysleft = ob.getString("daysleft");
                                        //change the url for upcoming appointments, add days left with every client name
                                        appointmentsList.add(new AppointmentsModel(username,newtime,type,daysleft+" days left",id));
                                    }
                                    appointmentsAdapter.notifyDataSetChanged();
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
                        Toast.makeText(getActivity().getApplicationContext(),"Something went wrong!\nCheck your Internet connection and try again..", Toast.LENGTH_LONG).show();
                        //Toast.makeText(MedicineData.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("dietitianId","1");
                return params;
            }

        };

        int MY_SOCKET_TIMEOUT_MS = 50000;
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
