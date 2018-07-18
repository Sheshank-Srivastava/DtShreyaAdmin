package com.dietitianshreya.eatrightdietadmin;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import android.widget.DatePicker;
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
import com.dietitianshreya.eatrightdietadmin.adapters.CompositionAdapter;
import com.dietitianshreya.eatrightdietadmin.models.CompositionModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.dietitianshreya.eatrightdietadmin.Login.MyPREFERENCES;


public class BodyCompositionFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private String clientId;

    private OnFragmentInteractionListener mListener;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    ArrayList<CompositionModel> compositionList;
    CompositionAdapter compositionAdapter;
    EditText date,sfat,vfat,bmi,muscle,bodyage,weight,height,resMeta;
    String userid,clinicId;

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
            clientId = getArguments().getString(VariablesModels.userId);
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
        compositionList=getArguments().getParcelableArrayList("bca");

        compositionAdapter = new CompositionAdapter(compositionList,getContext());
        recyclerView.setAdapter(compositionAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        SharedPreferences sharedpreferences1 = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        userid= String.valueOf(sharedpreferences1.getInt("clientId",0));
        clinicId= sharedpreferences1.getString("clinicId","0");
        Log.d("clinicId",clinicId);
        Log.d("dietitianId",userid);
        Log.d("clientId",clientId);
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
                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar c = Calendar.getInstance();
                        int mYear = c.get(Calendar.YEAR); // current year
                        int mMonth = c.get(Calendar.MONTH); // current month
                        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                        // date picker dialog

                        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        // set day of month , month and year value in the edit text
                                        int month = monthOfYear+1;
                                        String smonth = month+"";
                                        if(month<10)
                                        {
                                            smonth = "0"+smonth;
                                        }
                                        String Date = dayOfMonth+"-"+smonth+"-"+year;
                                        date.setText(Date);

                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                    }
                });
//                date.setEnabled(false);
                date.setFocusable(false);
                weight = (EditText) inflator.findViewById(R.id.weight);
                height = (EditText) inflator.findViewById(R.id.height);
                sfat = (EditText) inflator.findViewById(R.id.sfat);
                vfat = (EditText) inflator.findViewById(R.id.vfat);
                bmi = (EditText) inflator.findViewById(R.id.bmi);
                muscle = (EditText) inflator.findViewById(R.id.muscle);
                bodyage = (EditText) inflator.findViewById(R.id.bodyage);
                resMeta = (EditText) inflator.findViewById(R.id.resMeta);

                alertDialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!(TextUtils.isEmpty(date.getText().toString().trim())&&TextUtils.isEmpty(weight.getText().toString().trim()))){
                            String datetxt = date.getText().toString().trim();
                            String weighttxt = weight.getText().toString().trim();
                            String heighttxt = height.getText().toString().trim();
                            String sfattxt = sfat.getText().toString().trim();
                            String vfattxt = vfat.getText().toString().trim();
                            String bmitxt = bmi.getText().toString().trim();
                            String muscletxt = muscle.getText().toString().trim();
                            String bodyagetxt = bodyage.getText().toString().trim();
                            String resmetatxt = resMeta.getText().toString().trim();
                            addBCAData(datetxt,weighttxt,heighttxt,sfattxt,vfattxt,bmitxt,muscletxt,bodyagetxt,resmetatxt);
//                            compositionList.add(new CompositionModel(datetxt,weighttxt,fattxt,watertxt,muscletxt,bonetxt));
//                            compositionAdapter.notifyDataSetChanged();
                        }else{
                            Toast.makeText(getActivity().getApplicationContext(),"Date and weight are required fields",Toast.LENGTH_SHORT).show();
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

    public void addBCAData(final String date,final String weight,final String height,final String sfat,final String vfat,final String bmi,final String muscle,final String bodyage,final String resMeta) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Updating data...");
        progressDialog.setCancelable(false);
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
                    params.put(VariablesModels.dietitianId,userid);
                    params.put("userId",clientId+"");
                    params.put("clinicId",clinicId);
                    params.put("date",date);
                    params.put("weight",weight);
                    params.put("height",height);
                    params.put("sfat",sfat);
                    params.put("vfat",vfat);
                    params.put("muscle",muscle);
                params.put("bmi",bmi);
                params.put("bodyage",bodyage);
                params.put("restingmeta",resMeta);

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
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/getbcadata/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            progressDialog.dismiss();
                            JSONObject result = new JSONObject(response);
                            Log.d("Result bca",response);
                            if(result.getInt("res")==1) {
                                compositionList.clear();
                                JSONArray ar = result.getJSONArray(VariablesModels.response);
                                if (ar.length() != 0){
                                    for (int i = 0; i < ar.length(); i++) {
                                        JSONObject ob = ar.getJSONObject(i);
                                        String date,weight,height,bmi,muscle,sfat,vfat,bodyage,restmeta;
                                        date = ob.getString("date");
                                        weight = ob.getString("weight");
                                        height = ob.getString("height");
                                        bmi = ob.getString("bmi");
                                        sfat = ob.getString("sfat");
                                        vfat = ob.getString("vfat");
                                        bodyage = ob.getString("bodyage");
                                        restmeta = ob.getString("restingmeta");
                                        muscle = ob.getString("muscle");
                                        compositionList.add(new CompositionModel(date,weight+" Kg",sfat,vfat,bmi,muscle,bodyage,restmeta,height));
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
                params.put(VariablesModels.userId,clientId);
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
