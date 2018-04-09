package com.dietitianshreya.dtshreyaadmin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.dietitianshreya.dtshreyaadmin.adapters.CompositionAdapter;
import com.dietitianshreya.dtshreyaadmin.models.AllClientListOthersModel;
import com.dietitianshreya.dtshreyaadmin.models.AllClientsCompoundModel;
import com.dietitianshreya.dtshreyaadmin.models.ChooseMealModel;
import com.dietitianshreya.dtshreyaadmin.models.CompositionModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class BodyCompositionFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private String clientId;

    private OnFragmentInteractionListener mListener;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    ArrayList<CompositionModel> compositionList;
    CompositionAdapter compositionAdapter;
    EditText date,fat,water,muscle,bone,weight;

    public BodyCompositionFragment() {
        // Required empty public constructor
    }

    public static BodyCompositionFragment newInstance(String param1) {
        BodyCompositionFragment fragment = new BodyCompositionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            clientId = getArguments().getString(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_body_composition, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.re);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        compositionList = new ArrayList<>();
        compositionAdapter = new CompositionAdapter(compositionList,getContext());
        recyclerView.setAdapter(compositionAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        fetchBCAData();
//        compositionList.add(new CompositionModel("10/02/2018","80 kg","20","50","30","70"));
//        compositionList.add(new CompositionModel("10/02/2018","80 kg","20","50","30","70"));
//        compositionList.add(new CompositionModel("10/02/2018","80 kg","20","50","30","70"));
//        compositionList.add(new CompositionModel("10/02/2018","80 kg","20","50","30","70"));
//        compositionList.add(new CompositionModel("10/02/2018","80 kg","20","50","30","70"));
//        compositionList.add(new CompositionModel("10/02/2018","80 kg","20","50","30","70"));
//        compositionAdapter.notifyDataSetChanged();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                // Get the layout inflater
                LayoutInflater linf = LayoutInflater.from(getActivity());
                final View inflator = linf.inflate(R.layout.add_composition_data, null);
                // Setting Dialog Message
                alertDialog.setMessage("Add Composition Data");
                alertDialog.setView(inflator);
                date = (EditText) inflator.findViewById(R.id.date);
                Calendar calendar = Calendar.getInstance();
                Date c = calendar.getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                final String dateText = df.format(c);
                date.setText(dateText);
                date.setEnabled(false);
                weight = (EditText) inflator.findViewById(R.id.weight);
                fat = (EditText) inflator.findViewById(R.id.fat);
                water = (EditText) inflator.findViewById(R.id.water);
                muscle = (EditText) inflator.findViewById(R.id.muscle);
                bone = (EditText) inflator.findViewById(R.id.bone);

                alertDialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!(TextUtils.isEmpty(date.getText().toString().trim()))){
                            String datetxt = date.getText().toString().trim();
                            String weighttxt = weight.getText().toString().trim();
                            String fattxt = fat.getText().toString().trim();
                            String watertxt = water.getText().toString().trim();
                            String muscletxt = muscle.getText().toString().trim();
                            String bonetxt = bone.getText().toString().trim();
                            addBCAData(datetxt,weighttxt,fattxt,watertxt,muscletxt,bonetxt);
//                            compositionList.add(new CompositionModel(datetxt,weighttxt,fattxt,watertxt,muscletxt,bonetxt));
//                            compositionAdapter.notifyDataSetChanged();
                        }
                    }
                });
                alertDialog.show();
            }
        });
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

    public void addBCAData(final String date,final String weight,final String fat,final String water,final String muscle,final String bone) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Updating data");
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/addbcadata/";
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
                                fetchBCAData();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getActivity().getApplicationContext(),"Saved successfully",Toast.LENGTH_SHORT).show();
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
                    params.put("clientId","17");
                    params.put("clinicId","3");
                    params.put("date",date);
                    params.put("weight",weight);
                    params.put("water",water);
                    params.put("fat",fat);
                    params.put("bone",bone);
                    params.put("muscle",muscle);

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

    public void fetchBCAData() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching data...");
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/getbcadata/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            progressDialog.dismiss();
                            JSONObject result = new JSONObject(response);
                            if(result.getInt("res")==1) {
                                JSONArray ar = result.getJSONArray("data");
                                if (ar.length() != 0){
                                    for (int i = 0; i < ar.length(); i++) {
                                        JSONObject ob = ar.getJSONObject(i);
                                        String date,weight,water,bone,muscle,fat;
                                        date = ob.getString("date");
                                        weight = ob.getString("weight");
                                        fat = ob.getString("fat");
                                        water = ob.getString("water");
                                        bone = ob.getString("bone");
                                        muscle = ob.getString("muscle");
                                        compositionList.add(new CompositionModel(date,weight,fat,water,muscle,bone));
                                    }
                                    compositionAdapter.notifyDataSetChanged();
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
                params.put("clientId","17");
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
}
