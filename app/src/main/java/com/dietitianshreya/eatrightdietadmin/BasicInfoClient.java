package com.dietitianshreya.eatrightdietadmin;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dietitianshreya.eatrightdietadmin.Utils.VariablesModels;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class BasicInfoClient extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private OnFragmentInteractionListener mListener;
    EditText dieterName,dieterPhone,dieterEmail,dieterReferredby,dieterDob,dieterWhatsapp,dieterAddress,dieterOccupation,
            dieterGender,dieterHeight,dieterWeight,dieterAnniversary,
            dieterweightmanage;
    Button save;
    String clientId;
    String beforeText;
    JSONObject medicalObject = new JSONObject();
    JSONObject basicinfoobject = new JSONObject();// Basic info object
    JSONObject foodspecsObject = new JSONObject();//food specs object

    public BasicInfoClient() {
        // Required empty public constructor
    }

    public static BasicInfoClient newInstance(String param1,String clientId) {
        BasicInfoClient fragment = new BasicInfoClient();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2,clientId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            clientId = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_basic_info_client, container, false);
        dieterName = rootView.findViewById(R.id.clientName);
        dieterPhone = rootView.findViewById(R.id.clientPhone);
        dieterEmail = rootView.findViewById(R.id.clientEmail);
        dieterGender = rootView.findViewById(R.id.clientGender);
        dieterReferredby = rootView.findViewById(R.id.clientReferred);
        dieterHeight = rootView.findViewById(R.id.clientHeight);
        dieterWeight = rootView.findViewById(R.id.clientWeight);
        dieterDob = rootView.findViewById(R.id.clientdob);
        dieterAddress = rootView.findViewById(R.id.clientAddress);
        dieterOccupation = rootView.findViewById(R.id.clientOccupation);
        dieterWhatsapp = rootView.findViewById(R.id.clientEndDate);
        dieterweightmanage= rootView.findViewById(R.id.clientweightmanage);
        dieterAnniversary = rootView.findViewById(R.id.clientAnn);
        save = rootView.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveProfiledata();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            getJson(mParam1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rootView;
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

    public void saveProfiledata() throws JSONException{
        createJson();
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Updating data...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/saveprofiledata/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {

                            Log.d("response",response);

                            progressDialog.dismiss();
                            Toast.makeText(getActivity(),"Data saved!",Toast.LENGTH_SHORT).show();




                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity().getApplicationContext(),"Something went wrong!", Toast.LENGTH_LONG).show();
                        //Toast.makeText(MedicineData.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put(VariablesModels.userId,clientId);
                params.put(VariablesModels.foodspecification,foodspecsObject+"");
                params.put(VariablesModels.medicalhistory,medicalObject+"");
                params.put(VariablesModels.basicinfo,basicinfoobject+"");

                return params;
            }

        };

        int MY_SOCKET_TIMEOUT_MS = 50000;
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);



    }

    public void createJson() throws JSONException {
        basicinfoobject.put(VariablesModels.username, dieterName.getText().toString());
        basicinfoobject.put(VariablesModels.weight, dieterWeight.getText().toString());
        basicinfoobject.put(VariablesModels.referredby, dieterReferredby.getText().toString());
        basicinfoobject.put(VariablesModels.dob, dieterDob.getText().toString());
        basicinfoobject.put(VariablesModels.gender, dieterGender.getText().toString());
        basicinfoobject.put(VariablesModels.anniversary, dieterAnniversary.getText().toString());
        basicinfoobject.put(VariablesModels.height, dieterHeight.getText().toString());
        basicinfoobject.put(VariablesModels.contact, dieterPhone.getText().toString());
        basicinfoobject.put(VariablesModels.address, dieterAddress.getText().toString());
        basicinfoobject.put(VariablesModels.whatsapp, dieterWhatsapp.getText().toString());
        basicinfoobject.put(VariablesModels.email, dieterEmail.getText().toString());
        basicinfoobject.put(VariablesModels.occupation, dieterOccupation.getText().toString());
        basicinfoobject.put(VariablesModels.weightmanage,dieterweightmanage.getText().toString());
//        sendR();
    }

    public void getJson(String response) throws JSONException {
        JSONObject object = new JSONObject(response);
        if (object.getInt("res") == 1) {
            JSONObject responseObject = object.getJSONObject("response");
            JSONObject basicinfo = responseObject.getJSONObject("basicinfo");
            dieterName.setText(basicinfo.getString(VariablesModels.username));
            dieterDob.setText(basicinfo.getString(VariablesModels.dob));
            dieterAnniversary.setText(basicinfo.getString(VariablesModels.anniversary));
            dieterPhone.setText(basicinfo.getString(VariablesModels.contact));
            dieterWhatsapp.setText(basicinfo.getString(VariablesModels.whatsapp));
            dieterOccupation.setText(basicinfo.getString(VariablesModels.occupation));
            dieterAddress.setText(basicinfo.getString(VariablesModels.address));
            dieterEmail.setText(basicinfo.getString(VariablesModels.email));
            dieterReferredby.setText(basicinfo.getString(VariablesModels.referredby));
            dieterWeight.setText(basicinfo.getString(VariablesModels.weight));
            dieterHeight.setText(basicinfo.getString(VariablesModels.height));
            dieterGender.setText(basicinfo.getString(VariablesModels.gender));
            //Change after api changes
            dieterweightmanage.setText(basicinfo.getString(VariablesModels.weightmanage));
        }
    }
}
