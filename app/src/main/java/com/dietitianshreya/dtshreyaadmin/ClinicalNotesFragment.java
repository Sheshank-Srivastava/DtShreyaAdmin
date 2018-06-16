package com.dietitianshreya.dtshreyaadmin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dietitianshreya.dtshreyaadmin.adapters.ClinicalNotesAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClinicalNotesFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private String clientId;

    private OnFragmentInteractionListener mListener;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    ArrayList<String> notes;
    ClinicalNotesAdapter clinicalNotesAdapter;
    EditText editText;

    public ClinicalNotesFragment() {
        // Required empty public constructor
    }

    public static ClinicalNotesFragment newInstance(String param1) {
        ClinicalNotesFragment fragment = new ClinicalNotesFragment();
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
        View rootView =  inflater.inflate(R.layout.fragment_clinical_notes, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.re);
        notes = new ArrayList<>();
        notes = getArguments().getStringArrayList("notes");//Get pass data with its key value
        clinicalNotesAdapter = new ClinicalNotesAdapter(notes,getContext());



              /*  notes.add("You can have regular milk tea twice a day");




                notes.add("You can swap the diet as per your convinience");
                notes.add("Try to avoid OIL");
                notes.add("Curd Lassi atc can be yaken only when written in the diet");
                notes.add("Please avoid Table Salt. Try having oregano"); */
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(clinicalNotesAdapter);
        clinicalNotesAdapter.notifyDataSetChanged();
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                // Get the layout inflater
                LayoutInflater linf = LayoutInflater.from(getActivity());
                final View inflator = linf.inflate(R.layout.custom_dialog, null);
                alertDialog.setTitle("Add a note...");
                alertDialog.setView(inflator);
                editText = (EditText) inflator.findViewById(R.id.quant);
                editText.setHint("Type here the note");
                TextView text = (TextView) inflator.findViewById(R.id.text);
                TextView dot = (TextView) inflator.findViewById(R.id.dot);
                text.setVisibility(View.GONE);
                dot.setVisibility(View.GONE);
                alertDialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!(TextUtils.isEmpty(editText.getText().toString().trim()))){
                            notes.add(editText.getText().toString().trim());
                            UpdateNotesData(editText.getText().toString());

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


    public void UpdateNotesData(final String notes) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Updating data...");
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/addnote/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    progressDialog.dismiss();
                        try {

                            JSONObject object = new JSONObject(response);
                            String msg = object.getString("msg");
                            int res= object.getInt("res");

                            if(res==1)
                            {
                                Toast.makeText(getActivity(),"data saved",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getActivity().getApplicationContext(),"Something went wrong!", Toast.LENGTH_LONG).show();
                        //Toast.makeText(MedicineData.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("userId",clientId);
                params.put("note",notes);
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
