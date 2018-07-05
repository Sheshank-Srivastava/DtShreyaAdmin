package com.dietitianshreya.dtshreyaadmin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.dietitianshreya.dtshreyaadmin.Utils.VariablesModels;
import com.dietitianshreya.dtshreyaadmin.adapters.DietCreateAdapter;
import com.dietitianshreya.dtshreyaadmin.adapters.DietPlanAdapter;
import com.dietitianshreya.dtshreyaadmin.models.AppointmentDetailsModel;
import com.dietitianshreya.dtshreyaadmin.models.AppointmentHistoryModel;
import com.dietitianshreya.dtshreyaadmin.models.DietPlanModel;
import com.dietitianshreya.dtshreyaadmin.models.MealModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.dietitianshreya.dtshreyaadmin.Login.MyPREFERENCES;


public class DietCreateFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String clientId;
    private String date;
    RecyclerView recyclerView;
    EditText dietDate;
    ArrayList<DietPlanModel> dietList;
    JSONArray notesarray= new JSONArray();
    ArrayList<MealModel> mealList;
    DietCreateAdapter dietPlanAdapter;
    String userid;
    ArrayList<String> notesList = new ArrayList<>();
    EditText editText;
    JSONObject object= new JSONObject();
    ArrayList<MealModel> mealListem = new ArrayList<>();
    ArrayList<MealModel> mealList1 = new ArrayList<>();
    ArrayList<MealModel> mealListmm = new ArrayList<>();
    ArrayList<MealModel> mealList2 = new ArrayList<>();
    ArrayList<MealModel> mealList3 = new ArrayList<>();
    ArrayList<MealModel> mealListpd = new ArrayList<>();
    ArrayList<MealModel> mealListsup = new ArrayList<>();
    ArrayList<MealModel> mealListlateEve = new ArrayList<>();
    private OnFragmentInteractionListener mListener;


    public DietCreateFragment() {
        // Required empty public constructor
    }

    public static DietCreateFragment newInstance(String clientid, String datetitle) {
        DietCreateFragment fragment = new DietCreateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, clientid);
        args.putString(ARG_PARAM2, datetitle);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            clientId = getArguments().getString(ARG_PARAM1);
            date = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_diet_view, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.re);
        setHasOptionsMenu(true);
        dietList=new ArrayList<>();
        mealList = new ArrayList<>();
        dietPlanAdapter = new DietCreateAdapter(dietList,getActivity(),clientId);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(dietPlanAdapter);

        SharedPreferences sharedpreferences1 = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        userid= String.valueOf(sharedpreferences1.getInt("clientId",0));

        dietList.add(new DietPlanModel("Early Morning",mealListem,1));
        dietList.add(new DietPlanModel("Breakfast",mealList,2));

        dietList.add(new DietPlanModel("Mid Morning",mealListmm,3));

        dietList.add(new DietPlanModel("Lunch",mealList1,4));

        dietList.add(new DietPlanModel("Evening",mealList2,5));
        dietList.add(new DietPlanModel("Late Evening",mealListlateEve,6));

        dietList.add(new DietPlanModel("Dinner",mealList3,7));

        dietList.add(new DietPlanModel("Post Dinner",mealListpd,8));

        dietList.add(new DietPlanModel("Supplements",mealListsup,9));
        dietPlanAdapter.notifyDataSetChanged();



        return rootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.notes){
            notesList.clear();
            getNotes();

        }
        else if(id == R.id.save){
           // Toast.makeText(getActivity(),"Diet Saved",Toast.LENGTH_SHORT).show();
            try {
                createJson1();
                if(notesList.size()==0)
                {
                    Toast.makeText(getActivity(),"Please add notes first",Toast.LENGTH_LONG).show();
                }
                else {
                    RequestData();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if(id == R.id.template){
            // Toast.makeText(getActivity(),"Diet Saved",Toast.LENGTH_SHORT).show();
            try {
                final int REQUEST_CODE = 9;

                Intent i = new Intent(getActivity(), TemplateSelection.class);
                startActivityForResult(i, REQUEST_CODE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return super.onOptionsItemSelected(item);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
//        super.onActivityResult(requestCode,resultCode,data);
        if(data != null) {
            if(requestCode<9) {


                dietPlanAdapter.onActivityResult(requestCode, resultCode, data);

            }

            else if(requestCode==100)
            {
                ArrayList<String> notes1 = data.getStringArrayListExtra("notes");

                notesList = notes1;
                Log.d("mytag",notes1.get(0));
            }
            else
            {

                DietPlanModel model = dietList.get(1);
                model.setMealList(mealList);
                DietPlanModel modelem = dietList.get(0);
                modelem.setMealList(mealListem);
                DietPlanModel modelmm = dietList.get(2);
                modelmm.setMealList(mealListmm);
                DietPlanModel model1 = dietList.get(3);
                model1.setMealList(mealList1);
                DietPlanModel model2 = dietList.get(4);
                model2.setMealList(mealList2);
                DietPlanModel modelLateEve = dietList.get(5);
                modelLateEve.setMealList(mealListlateEve);
                DietPlanModel model3 = dietList.get(6);
                model3.setMealList(mealList3);
                DietPlanModel model4= dietList.get(7);
                model4.setMealList(mealListpd);
                DietPlanModel modelSup= dietList.get(8);
                modelSup.setMealList(mealListsup);
                mealListem = data.getParcelableArrayListExtra("meal1");
                mealList = data.getParcelableArrayListExtra("meal2");
                mealListmm = data.getParcelableArrayListExtra("meal3");
                mealList1 = data.getParcelableArrayListExtra("meal4");
                mealList2 = data.getParcelableArrayListExtra("meal5");
                mealListlateEve = data.getParcelableArrayListExtra("meal6");

                mealList3 = data.getParcelableArrayListExtra("meal7");
                mealListpd = data.getParcelableArrayListExtra("meal8");
                mealListsup = data.getParcelableArrayListExtra("meal9");

                dietPlanAdapter.notifyDataSetChanged();
            }
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



    public void RequestData() throws JSONException {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Uploading data...");
        progressDialog.show();
        for(int i =0;i< notesList.size();i++){
            notesarray.put(notesList.get(i));

        }
        final JSONObject notesObject = new JSONObject();
        notesObject.put("data",notesarray);
        String url = "https://shreyaapi.herokuapp.com/creatediet/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.d("response",response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity().getApplicationContext(),"Something went wrong!", Toast.LENGTH_LONG).show();
                        //Toast.makeText(MedicinData.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put(VariablesModels.userId,clientId);
                params.put(VariablesModels.clinicId,"1");
                params.put(VariablesModels.dietitianId,userid);
                params.put("dateofdiet",date);
                params.put("dietdata",object+"");
                params.put("supplements","lol");
                params.put("notes",notesObject+"");
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

    public void createJson() throws JSONException {


       int length= dietList.size();
       Log.d("dateji",date);

       for(int i=0;i<length;i++)
       {
           int meallistsize=dietList.get(i).getMealList().size();
          if(meallistsize>0)

          {
              if(i==0) {
                  JSONArray mealdata= new JSONArray();
                  for (int j = 0; j < meallistsize; j++) {


                      Log.d("lol", dietList.get(i).getMealList().get(j).getMealHead());
                      mealdata.put(dietList.get(0).getMealList().get(j).getMealHead());

                  }
                  object.put("foodone",mealdata);
              }
              else if(i==1)
              {
                  JSONArray mealdata= new JSONArray();
                  for (int j = 0; j < meallistsize; j++) {


                      Log.d("lol", dietList.get(i).getMealList().get(j).getMealHead());
                      mealdata.put(dietList.get(1).getMealList().get(j).getMealHead());
                  }
                  object.put("foodtwo",mealdata);
              }
              else if(i==2)
              {   JSONArray mealdata= new JSONArray();
                  for (int j = 0; j < meallistsize; j++) {


                      Log.d("lol", dietList.get(i).getMealList().get(j).getMealHead());
                      mealdata.put(dietList.get(2).getMealList().get(j).getMealHead());
                  }
                  object.put("foodthree",mealdata);
              }

              else if(i==3)
              {   JSONArray mealdata= new JSONArray();
                  for (int j = 0; j < meallistsize; j++) {


                      Log.d("lol", dietList.get(i).getMealList().get(j).getMealHead());
                      mealdata.put(dietList.get(3).getMealList().get(j).getMealHead());
                  }
                  object.put("foodfour",mealdata);
              }

              else if(i==4)
              {   JSONArray mealdata= new JSONArray();
                  for (int j = 0; j < meallistsize; j++) {


                      Log.d("lol", dietList.get(i).getMealList().get(j).getMealHead());
                      mealdata.put(dietList.get(4).getMealList().get(j).getMealHead());
                  }
                  object.put("foodfive",mealdata);
              }
              else if(i==5)
              {   JSONArray mealdata= new JSONArray();
                  for (int j = 0; j < meallistsize; j++) {


                      Log.d("lol", dietList.get(i).getMealList().get(j).getMealHead());
                      mealdata.put(dietList.get(4).getMealList().get(j).getMealHead());
                  }
                  object.put("foodsix",mealdata);
              }

              else if(i==6)
              {   JSONArray mealdata= new JSONArray();
                  for (int j = 0; j < meallistsize; j++) {


                      Log.d("lol", dietList.get(i).getMealList().get(j).getMealHead());
                      mealdata.put(dietList.get(5).getMealList().get(j).getMealHead());
                  }
                  object.put("foodseven",mealdata);
              }




          }
       }

Log.d("json",object+"");
    }

    public void createJson1() throws JSONException {





for(int i =0;i<dietList.size();i++)
{
    int pos= dietList.get(i).getPos();
    if (pos ==1)
    {
        int meallistsize=dietList.get(i).getMealList().size();
        JSONArray mealdata= new JSONArray();
        for(int j =0;j<meallistsize;j++)
        {
            mealdata.put(dietList.get(i).getMealList().get(j).getMealHead());
        }
if(mealdata.length()>0) {
    object.put("foodone", mealdata);
}
    }
    else if (pos ==2)
    {
        int meallistsize=dietList.get(i).getMealList().size();
        JSONArray mealdata= new JSONArray();
        for(int j =0;j<meallistsize;j++)
        {
            mealdata.put(dietList.get(i).getMealList().get(j).getMealHead());
        }
        if(mealdata.length()>0) {
            object.put("foodtwo", mealdata);
        }
    }

    else if (pos ==3)
    {
        int meallistsize=dietList.get(i).getMealList().size();
        JSONArray mealdata= new JSONArray();
        for(int j =0;j<meallistsize;j++)
        {
            mealdata.put(dietList.get(i).getMealList().get(j).getMealHead());
        }
        if(mealdata.length()>0) {
            object.put("foodthree", mealdata);
        }
    }

   else if (pos ==4)
    {
        int meallistsize=dietList.get(i).getMealList().size();
        JSONArray mealdata= new JSONArray();
        for(int j =0;j<meallistsize;j++)
        {
            mealdata.put(dietList.get(i).getMealList().get(j).getMealHead());
        }
        if(mealdata.length()>0) {
            object.put("foodfour", mealdata);
        }
    }

   else if (pos ==5)
    {
        int meallistsize=dietList.get(i).getMealList().size();
        JSONArray mealdata= new JSONArray();
        for(int j =0;j<meallistsize;j++)
        {
            mealdata.put(dietList.get(i).getMealList().get(j).getMealHead());
        }
        if(mealdata.length()>0) {
            object.put("foodfive", mealdata);
        }
    }

   else if (pos ==6)
    {
        int meallistsize=dietList.get(i).getMealList().size();
        JSONArray mealdata= new JSONArray();
        for(int j =0;j<meallistsize;j++)
        {
            mealdata.put(dietList.get(i).getMealList().get(j).getMealHead());
        }
        if(mealdata.length()>0) {
            object.put("foodsix", mealdata);
        }
    }

   else if (pos ==7)
    {
        int meallistsize=dietList.get(i).getMealList().size();
        JSONArray mealdata= new JSONArray();
        for(int j =0;j<meallistsize;j++)
        {
            mealdata.put(dietList.get(i).getMealList().get(j).getMealHead());
        }
        if(mealdata.length()>0) {
            object.put("foodseven", mealdata);
        }
    }

    else if (pos ==8)
    {
        int meallistsize=dietList.get(i).getMealList().size();
        JSONArray mealdata= new JSONArray();
        for(int j =0;j<meallistsize;j++)
        {
            mealdata.put(dietList.get(i).getMealList().get(j).getMealHead());
        }
        if(mealdata.length()>0) {
            object.put("foodeight", mealdata);
        }
    }

        }


Log.d("lol",object+"");

    }


    public  void getNotes()
    {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("fetching data...");
        progressDialog.show();



        String url = "https://shreyaapi.herokuapp.com/getdiseasenote/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.d("response",response);
                        try {

                            JSONObject notesObject = new JSONObject(response);
                            if( notesObject.getInt("res")==1){

                                JSONArray array  = notesObject.getJSONArray("response");

                                for( int i =0;i<array.length();i++)
                                {
                                    notesList.add(array.getString(i));

                                }

                                Intent intent = new Intent(getActivity(),NotesSelection.class);
                                intent.putExtra("notes",notesList);
                                startActivityForResult(intent,100);




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
                        //Toast.makeText(MedicinData.this,error.toString(),Toast.LENGTH_LONG).show();
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
