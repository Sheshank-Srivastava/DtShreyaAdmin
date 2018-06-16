package com.dietitianshreya.dtshreyaadmin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
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
import com.dietitianshreya.dtshreyaadmin.Utils.VariablesModels;
import com.dietitianshreya.dtshreyaadmin.adapters.DietPlanAdapter;
import com.dietitianshreya.dtshreyaadmin.models.DietPlanModel;
import com.dietitianshreya.dtshreyaadmin.models.MealModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.dietitianshreya.dtshreyaadmin.Login.MyPREFERENCES;


public class DietViewFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String clientId;
    private String date;
    RecyclerView recyclerView;
    String userid;
    EditText dietDate;
    ArrayList<DietPlanModel> dietList;
    ArrayList<MealModel> mealList;
    DietPlanAdapter dietPlanAdapter;


    private OnFragmentInteractionListener mListener;

    public DietViewFragment() {
        // Required empty public constructor
    }

    public static DietViewFragment newInstance(String param1, String param2,ArrayList<DietPlanModel> dietList) {
        DietViewFragment fragment = new DietViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putParcelableArrayList("dietlist",dietList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            clientId = getArguments().getString(ARG_PARAM1);
            date = getArguments().getString(ARG_PARAM2);
            Log.d("date",date);
            dietList=getArguments().getParcelableArrayList("dietlist");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_diet_view, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.re);
        dietList=new ArrayList<>();
        mealList = new ArrayList<>();
        SharedPreferences sharedpreferences1 = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        userid= String.valueOf(sharedpreferences1.getInt("clientId",0));
        dietPlanAdapter = new DietPlanAdapter(dietList,getActivity());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(dietPlanAdapter);

        dietPlanAdapter.notifyDataSetChanged();
        fetchDietData();

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

    public void fetchDietData() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching data...");
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/getdatediet/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            Log.d("res",response);
                            JSONObject outerobject = new JSONObject(response);
                            if(outerobject.has("response")) {
                                JSONObject innerobject = outerobject.getJSONObject("response");
                                if (innerobject.has("foodone")) {
                                    JSONArray array = innerobject.getJSONArray("foodone");
                                    ArrayList<MealModel> mealList = new ArrayList<>();
                                    for (int i = 0; i < array.length(); i++) {
                                        String food = array.getString(i);
                                        mealList.add(new MealModel(food, "xy", true));
                                    }
                                    dietList.add(new DietPlanModel("Early Morning", mealList, 1));
                                }
                                if (innerobject.has("foodtwo")) {
                                    ArrayList<MealModel> mealList = new ArrayList<>();
                                    JSONArray array = innerobject.getJSONArray("foodtwo");
                                    for (int i = 0; i < array.length(); i++) {
                                        String food = array.getString(i);
                                        mealList.add(new MealModel(food, "xy", true));
                                    }
                                    dietList.add(new DietPlanModel("Breakfast", mealList, 2));
                                }

                                if (innerobject.has("foodthree")) {
                                    ArrayList<MealModel> mealList = new ArrayList<>();
                                    JSONArray array = innerobject.getJSONArray("foodthree");
                                    for (int i = 0; i < array.length(); i++) {
                                        String food = array.getString(i);
                                        mealList.add(new MealModel(food, "xy", true));
                                    }
                                    dietList.add(new DietPlanModel("Mid Morning", mealList, 3));
                                }

                                if (innerobject.has("foodfour")) {
                                    ArrayList<MealModel> mealList = new ArrayList<>();
                                    JSONArray array = innerobject.getJSONArray("foodfour");
                                    for (int i = 0; i < array.length(); i++) {
                                        String food = array.getString(i);
                                        mealList.add(new MealModel(food, "xy", true));
                                    }
                                    dietList.add(new DietPlanModel("Lunch", mealList, 4));
                                }

                                if (innerobject.has("foodfive")) {
                                    ArrayList<MealModel> mealList = new ArrayList<>();
                                    JSONArray array = innerobject.getJSONArray("foodfive");
                                    for (int i = 0; i < array.length(); i++) {
                                        String food = array.getString(i);
                                        mealList.add(new MealModel(food, "xy", true));
                                    }
                                    dietList.add(new DietPlanModel("Evening", mealList, 5));
                                }

                                if (innerobject.has("foodsix")) {
                                    ArrayList<MealModel> mealList = new ArrayList<>();
                                    JSONArray array = innerobject.getJSONArray("foodsix");
                                    for (int i = 0; i < array.length(); i++) {
                                        String food = array.getString(i);
                                        mealList.add(new MealModel(food, "xy", true));
                                    }
                                    dietList.add(new DietPlanModel("Dinner", mealList, 6));
                                }

                                if (innerobject.has("foodseven")) {
                                    ArrayList<MealModel> mealList = new ArrayList<>();
                                    JSONArray array = innerobject.getJSONArray("foodseven");
                                    for (int i = 0; i < array.length(); i++) {
                                        String food = array.getString(i);
                                        mealList.add(new MealModel(food, "xy", true));
                                    }
                                    dietList.add(new DietPlanModel("Post Dinner", mealList, 7));
                                }

                                if (innerobject.has("foodeight")) {
                                    ArrayList<MealModel> mealList = new ArrayList<>();
                                    JSONArray array = innerobject.getJSONArray("foodeight");
                                    for (int i = 0; i < array.length(); i++) {
                                        String food = array.getString(i);
                                        mealList.add(new MealModel(food, "Supplements", true));
                                    }
                                    dietList.add(new DietPlanModel("Supplements", mealList, 8));
                                }
                            }
                            dietPlanAdapter.notifyDataSetChanged();

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
                params.put(VariablesModels.userId,clientId);
                params.put(VariablesModels.date,date);
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
