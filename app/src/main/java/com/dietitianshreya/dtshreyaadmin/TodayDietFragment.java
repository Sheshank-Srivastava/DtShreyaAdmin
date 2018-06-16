package com.dietitianshreya.dtshreyaadmin;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.dietitianshreya.dtshreyaadmin.Utils.VariablesModels;
import com.dietitianshreya.dtshreyaadmin.adapters.DietPlanAdapter;
import com.dietitianshreya.dtshreyaadmin.models.DietPlanModel;
import com.dietitianshreya.dtshreyaadmin.models.MealModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class TodayDietFragment extends Fragment {

    private static final String ARG_PARAM2 = "param2";
    String formattedDate;
    private static String clientId;
    DatePicker datePicker;
    Button button;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    EditText dietDate;
    ArrayList<DietPlanModel> dietList;
    ArrayList<MealModel> mealList;
    DietPlanAdapter dietPlanAdapter;
    TextView dateSelection;
    int month,date,year;
    Intent intent;

    private OnFragmentInteractionListener mListener;

    public TodayDietFragment() {
        // Required empty public constructor
    }

    public static TodayDietFragment newInstance(String param1,ArrayList<DietPlanModel>param2) {
        TodayDietFragment fragment = new TodayDietFragment();
        Bundle args = new Bundle();
        clientId = param1;
        args.putString("client", param1);
        args.putParcelableArrayList(ARG_PARAM2,param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           // clientId = getArguments().getString("client");
        Log.d("client",clientId);
            dietList=getArguments().getParcelableArrayList(ARG_PARAM2);

            setHasOptionsMenu(true);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_diet_history, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.re);
        dateSelection = (TextView) rootView.findViewById(R.id.dateSelection);
        dietList=new ArrayList<>();
        mealList = new ArrayList<>();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        formattedDate = df.format(c);
        Log.d("lol",dietList+"");
        dietPlanAdapter = new DietPlanAdapter(dietList,getActivity());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(dietPlanAdapter);
        dateSelection.setText(formattedDate);

        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),CreateDiet.class);
                i.putExtra("clientId",clientId);
                startActivity(i);
            }
        });
        dateSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(),ViewDietActivity.class);
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
                                month = monthOfYear;
                                date = dayOfMonth;
                                String Date = dayOfMonth+"/";
                                Date += (monthOfYear+1)+"/";
                                Date += year;
                                intent.putExtra("date",Date);
                                intent.putExtra("clientId",clientId);
                                intent.putExtra("day",date+"");
                                intent.putExtra("month",month+"");
                                intent.putExtra("year",year+"");
                                startActivity(intent);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        fetchDietData();
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String Date = "";
//                Date += datePicker.getDayOfMonth()+"/";
//                Date += (datePicker.getMonth()+1)+"/";
//                Date += datePicker.getYear();
//
//                Intent i = new Intent(getActivity(),ViewDietActivity.class);
//                i.putExtra("date",Date);
//                i.putExtra("day",datePicker.getDayOfMonth()+"");
//                i.putExtra("month",datePicker.getMonth()+"");
//                i.putExtra("year",datePicker.getYear()+"");
//                i.putExtra("clientId",clientId);
//                getActivity().startActivity(i);
//            }
//        });
        return rootView;
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.diet_history_menu,menu);
//    }


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
                                    JSONArray array = innerobject.getJSONArray("sixfood");
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
                params.put(VariablesModels.date,formattedDate);
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
