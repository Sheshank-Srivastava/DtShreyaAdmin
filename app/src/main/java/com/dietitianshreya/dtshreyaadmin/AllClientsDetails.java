package com.dietitianshreya.dtshreyaadmin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dietitianshreya.dtshreyaadmin.Utils.VariablesModels;
import com.dietitianshreya.dtshreyaadmin.adapters.AllClientsCompoundAdapter;
import com.dietitianshreya.dtshreyaadmin.models.AllClientListOthersModel;
import com.dietitianshreya.dtshreyaadmin.models.AllClientsCompoundModel;
import com.dietitianshreya.dtshreyaadmin.models.ClientAppointmentModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.dietitianshreya.dtshreyaadmin.Login.MyPREFERENCES;


public class AllClientsDetails extends Fragment {

    private OnFragmentInteractionListener mListener;
    RecyclerView recyclerView;
    ArrayList<AllClientsCompoundModel> clientList;

    ArrayList<AllClientListOthersModel> pendingdiet,otherclients;
    AllClientsCompoundAdapter clientListAdapter;
    String userid;

    public AllClientsDetails() {
        // Required empty public constructor
    }

    public static AllClientsDetails newInstance() {
        AllClientsDetails fragment = new AllClientsDetails();
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
        View rootView =  inflater.inflate(R.layout.fragment_all_clients_details, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.re);
        clientList=new ArrayList<>();
        SharedPreferences sharedpreferences1 = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        userid= String.valueOf(sharedpreferences1.getInt("clientId",0));
        clientListAdapter = new AllClientsCompoundAdapter(clientList,getActivity());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(clientListAdapter);
        pendingdiet = new ArrayList<>();
        otherclients = new ArrayList<>();
        sendR();
//
//        clientListDetails.add(new AllClientListOthersModel("Akshit Tyagi","Id : 345rde3","0","4 days left"));
//        clientListDetails.add(new AllClientListOthersModel("Paras Garg","Id : 345rde3","0","4 days left"));
//        clientListDetails.add(new AllClientListOthersModel("Balkeerat","Id : 345rde3","0","4 days left"));
//        clientListDetails.add(new AllClientListOthersModel("Manya ","Id : 345rde3","0","4 days left"));
//        clientList.add(new AllClientsCompoundModel("Pending Diet Creation",clientListDetails));
//
//        ArrayList<AllClientListOthersModel> clientDetailsList1 = new ArrayList<>();
//        clientDetailsList1.add(new AllClientListOthersModel("Akshit Tyagi","Id : 345rde3","4","4 days left"));
//        clientDetailsList1.add(new AllClientListOthersModel("Akshit Tyagi","Id : 345rde3","4","4 days left"));
//        clientDetailsList1.add(new AllClientListOthersModel("Akshit Tyagi","Id : 345rde3","4","4 days left"));
//        clientDetailsList1.add(new AllClientListOthersModel("Akshit Tyagi","Id : 345rde3","4","4 days left"));
//        clientDetailsList1.add(new AllClientListOthersModel("Akshit Tyagi","Id : 345rde3","4","4 days left"));
//        clientList.add(new AllClientsCompoundModel("Other Clients",clientDetailsList1));
//        clientListAdapter.notifyDataSetChanged();


        return rootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void sendR() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching All Clients...");
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/getclients/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            progressDialog.dismiss();
                            JSONObject result = new JSONObject(response);
                            Log.d("mytag",response);
                            if(result.getInt("res")==1) {
                                JSONArray ar = result.getJSONArray("response");
                                if (ar.length() != 0){
                                    for (int i = 0; i < ar.length(); i++) {
                                        JSONObject ob = ar.getJSONObject(i);
                                        String name,id,daysleft,createIn;
                                        int creatinInt;
                                        name = ob.getString(VariablesModels.user_name);
                                        id = ob.getInt(VariablesModels.userId)+"";
                                        daysleft = ob.getInt("daysleft")+" days left";
                                        creatinInt = ob.getInt("createIn");
                                        if(creatinInt<=0){
                                            pendingdiet.add(new AllClientListOthersModel(name,id,String.valueOf(creatinInt),daysleft));
                                        }else{
                                            otherclients.add(new AllClientListOthersModel(name,id,String.valueOf(creatinInt),daysleft));
                                        }
                                    }
                                    if(pendingdiet.size()>0) {
                                        clientList.add(new AllClientsCompoundModel("Pending Diet Creation", pendingdiet));
                                    }
                                    if(otherclients.size()>0) {
                                        clientList.add(new AllClientsCompoundModel("Other Clients", otherclients));
                                    }
                                    clientListAdapter.notifyDataSetChanged();
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
                params.put("dietitianId",userid);
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
