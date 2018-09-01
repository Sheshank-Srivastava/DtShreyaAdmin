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


public class FoodSpecificationClient extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private OnFragmentInteractionListener mListener;
    JSONObject medicalObject = new JSONObject();
    JSONObject basicinfoobject = new JSONObject();// Basic info object
    JSONObject foodspecsObject = new JSONObject();//food specs object
    EditText dieterVeg,dieterFoodAllergies,dieterLikes,dieterDislikes;
    Button save;
    String clientId;

    public FoodSpecificationClient() {
        // Required empty public constructor
    }


    public static FoodSpecificationClient newInstance(String param1,String param2) {
        FoodSpecificationClient fragment = new FoodSpecificationClient();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        View rootView = inflater.inflate(R.layout.fragment_food_specification_client, container, false);
        dieterVeg = rootView.findViewById(R.id.vegNonVeg);
        dieterFoodAllergies = rootView.findViewById(R.id.allergy);
        dieterLikes = rootView.findViewById(R.id.likes);
        dieterDislikes = rootView.findViewById(R.id.dislikes);
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


    public void createJson() throws JSONException {
        foodspecsObject.put(VariablesModels.allergies, dieterFoodAllergies.getText().toString());
        foodspecsObject.put(VariablesModels.likes, dieterLikes.getText().toString());
        foodspecsObject.put(VariablesModels.dislikes, dieterDislikes.getText().toString());
        foodspecsObject.put(VariablesModels.vegnonveg, dieterVeg.getText().toString());
//        sendR();
    }

    public void getJson(String response) throws JSONException {
        JSONObject object = new JSONObject(response);
        if (object.getInt("res") == 1) {
            JSONObject responseObject = object.getJSONObject("response");
            JSONObject food = responseObject.getJSONObject("foodspecification");
            dieterVeg.setText(food.getString(VariablesModels.vegnonveg));
            dieterFoodAllergies.setText(food.getString(VariablesModels.allergies));
            dieterLikes.setText(food.getString(VariablesModels.likes));
            dieterDislikes.setText(food.getString(VariablesModels.dislikes));
        }
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
}
