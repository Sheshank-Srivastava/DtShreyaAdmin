package com.dietitianshreya.dtshreyaadmin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dietitianshreya.dtshreyaadmin.adapters.AppointmentHistoryAdapter;
import com.dietitianshreya.dtshreyaadmin.models.AppointmentDetailsModel;
import com.dietitianshreya.dtshreyaadmin.models.AppointmentHistoryModel;
import com.dietitianshreya.dtshreyaadmin.models.AppointmentsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AppointmentFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    RecyclerView recyclerView;
    ArrayList<AppointmentHistoryModel> appointmentList;
    ArrayList<AppointmentDetailsModel> appointmentDetailsList;
    AppointmentHistoryAdapter appointmentHistoryAdapter;
    FloatingActionButton fab;
CoordinatorLayout coordinatorLayout;
    public AppointmentFragment() {
        // Required empty public constructor
    }

    public static AppointmentFragment newInstance() {
        AppointmentFragment fragment = new AppointmentFragment();
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
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_appointment,null);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.re);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        coordinatorLayout = rootView.findViewById(R.id.appointment_coordinator);
        coordinatorLayout.setLayoutParams(new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT,CoordinatorLayout.LayoutParams.MATCH_PARENT));
        appointmentList=new ArrayList<>();
        appointmentDetailsList = new ArrayList<>();
        appointmentHistoryAdapter = new AppointmentHistoryAdapter(appointmentList,getActivity());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(appointmentHistoryAdapter);
        fetchData();
//        appointmentDetailsList.add(new AppointmentDetailsModel("9:30 A.M.","In Person","Completed","Akshit Tyagi","4 days left","12345"));
//        appointmentDetailsList.add(new AppointmentDetailsModel("10:30 A.M.","In Person","Completed","Akshit Tyagi","4 days left","12345"));
//        appointmentDetailsList.add(new AppointmentDetailsModel("11:30 A.M.","In Person","Completed","Akshit Tyagi","4 days left","12345"));
//        appointmentList.add(new AppointmentHistoryModel("10/02/2018",appointmentDetailsList));
//
//        ArrayList<AppointmentDetailsModel> appointmentDetailsList1 = new ArrayList<>();
//        appointmentDetailsList1.add(new AppointmentDetailsModel("9:30 A.M.","In Person","Completed","Akshit Tyagi","4 days left","12345"));
//        appointmentDetailsList1.add(new AppointmentDetailsModel("9:30 A.M.","In Person","Completed","Akshit Tyagi","4 days left","12345"));
//        appointmentDetailsList1.add(new AppointmentDetailsModel("9:30 A.M.","In Person","Completed","Akshit Tyagi","4 days left","12345"));
//        appointmentList.add(new AppointmentHistoryModel("11/02/2018",appointmentDetailsList1));
//
//        appointmentHistoryAdapter.notifyDataSetChanged();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //handle book appointment here
                startActivity(new Intent(getActivity(),BookAppointment.class));
            }
        });
        return rootView;
    }

    public void fetchData() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching data...");
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/allappointments/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            progressDialog.dismiss();
                            JSONObject result = new JSONObject(response);
                            Log.d("REs",response);
                            if(result.getInt("res")==1) {
                                JSONObject res = result.getJSONObject("response");
                                int count = result.getInt("count");
                                if (count != 0){
                                    for (int i = 1; i <= count; i++) {
                                        JSONArray ar = res.getJSONArray(i+"");
                                        String Date = "";
                                        ArrayList<AppointmentDetailsModel> appointmentDetailsList1 = new ArrayList<>();
                                        for (int j = 0; j < ar.length(); j++) {
                                            JSONObject ob = ar.getJSONObject(j);
                                            String type, time, daysleft, status, id, userid, username;
                                            type = ob.getString("type");
                                            time = ob.getString("time");
                                            int hour = Integer.parseInt(time.split(":")[0]);
                                            String ampm = "A.M.";
                                            if (hour > 12) {
                                                hour -= 12;
                                                ampm = "P.M.";
                                            }
                                            String newtime = hour + ":" + time.split(":")[1] + " " + ampm;
                                            id = String.valueOf(ob.getInt("id"));
                                            status = ob.getString("status");
                                            username = ob.getString("username");
                                            daysleft = ob.getString("daysleft");
                                            userid = String.valueOf(ob.getInt("userid"));
                                            Date = ob.getString("date");
                                            //change the url for upcoming appointments, add days left with every client name
                                            appointmentDetailsList1.add(new AppointmentDetailsModel(newtime,type,status,username,daysleft + " days left",id));
                                        }
                                        appointmentList.add(new AppointmentHistoryModel(Date,appointmentDetailsList1));
                                    }
                                    appointmentHistoryAdapter.notifyDataSetChanged();
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
