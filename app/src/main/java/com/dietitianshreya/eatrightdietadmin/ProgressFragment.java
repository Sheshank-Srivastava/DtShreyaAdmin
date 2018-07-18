package com.dietitianshreya.eatrightdietadmin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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

import static com.dietitianshreya.eatrightdietadmin.Login.MyPREFERENCES;


public class ProgressFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private ProgressFragment.OnFragmentInteractionListener mListener;
int level,badge,bells,kgs;
ImageView badge1,badge2,badge3,badge4,badge5,level1,level2,level3,level4,level5;
TextView numberOfBells,rank,badgesEarn,kgslost;
    TextView badgeText1,badgeText2,badgeText3,badgeText4,badgeText5;
    String clientId;
    String userid;

    public ProgressFragment() {
        // Required empty public constructor
    }



    public static ProgressFragment newInstance(String param1)
    {
        ProgressFragment fragmentprogress= new ProgressFragment();
        Bundle args= new Bundle();
        args.putString(ARG_PARAM1,param1);
        fragmentprogress.setArguments(args);
        return fragmentprogress;
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
        View view= inflater.inflate(R.layout.fragment_progress_fragments, container, false);

        SharedPreferences sharedpreferences1 = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        userid= String.valueOf(sharedpreferences1.getInt("clientId",0));
        badge1= view.findViewById(R.id.badge1);
        badge2= view.findViewById(R.id.badge2);
        badge3 = view.findViewById(R.id.badge3);
        badge4= view.findViewById(R.id.badge4);
        badge5= view.findViewById(R.id.badge5);
        level1=view.findViewById(R.id.rank1);
        level2=view.findViewById(R.id.rank2);
        level3=view.findViewById(R.id.rank3);
        level4=view.findViewById(R.id.rank4);
        badgeText1 = view.findViewById(R.id.badgeText1);
        badgeText2 = view.findViewById(R.id.badgeText2);
        badgeText3 = view.findViewById(R.id.badgeText3);
        badgeText4 = view.findViewById(R.id.badgeText4);
        badgeText5 = view.findViewById(R.id.badgeText5);
        numberOfBells = view.findViewById(R.id.noOfBells);
        badgesEarn=view.findViewById(R.id.badgesEarned);
        kgslost=view.findViewById(R.id.kgsLost);
        rank = view.findViewById(R.id.currentRank);
        bells=getArguments().getInt("bells");
        badge = getArguments().getInt("badge");
        level = getArguments().getInt("level");
        kgs = getArguments().getInt("kgs");
        clientId = getArguments().getString(VariablesModels.userId);
        fetchProgressData();
        numberOfBells.setText(bells+" bells rung");
        kgslost.setText(kgs+ " lost");
        if(badge==1)
        {
            level1.setAlpha(1.0f);
            level2.setAlpha(0.3f);
            level3.setAlpha(0.3f);
            level4.setAlpha(0.3f);
            badge1.setAlpha(1.0f);
            badge2.setAlpha(0.3f);
            badge3.setAlpha(0.3f);
            badge4.setAlpha(0.3f);
            badge5.setAlpha(0.3f);
            badgeText1.setAlpha(1.0f);
            badgeText2.setAlpha(0.3f);
            badgeText3.setAlpha(0.3f);
            badgeText4.setAlpha(0.3f);
            badgeText5.setAlpha(0.3f);
            badgesEarn.setText("1");
            rank.setText("  NEWBIE ");

        }
        else if(badge==2)
        {
            level1.setAlpha(1.0f);
            level2.setAlpha(1.0f);
            level3.setAlpha(0.3f);
            level4.setAlpha(0.3f);
            badge1.setAlpha(1.0f);
            badge2.setAlpha(1.0f);
            badge3.setAlpha(0.3f);
            badge4.setAlpha(0.3f);
            badge5.setAlpha(0.3f);
            badgeText1.setAlpha(1.0f);
            badgeText2.setAlpha(1.0f);
            badgeText3.setAlpha(0.3f);
            badgeText4.setAlpha(0.3f);
            badgeText5.setAlpha(0.3f);
            badgesEarn.setText("2");
            rank.setText("  SENIOR ");
        }

        else if(badge==3)
        {
            level1.setAlpha(1.0f);
            level2.setAlpha(1.0f);
            level3.setAlpha(1.0f);
            level4.setAlpha(0.3f);
            badge1.setAlpha(1.0f);
            badge2.setAlpha(1.0f);
            badge3.setAlpha(1.0f);
            badge4.setAlpha(0.3f);
            badge5.setAlpha(0.3f);
            badgeText1.setAlpha(1.0f);
            badgeText2.setAlpha(1.0f);
            badgeText3.setAlpha(1.0f);
            badgeText4.setAlpha(0.3f);
            badgeText5.setAlpha(0.3f);
            badgesEarn.setText("3");
            rank.setText("  PRO ");
        }
        else if( badge==4)
        {
            level1.setAlpha(1.0f);
            level2.setAlpha(1.0f);
            level3.setAlpha(1.0f);
            level4.setAlpha(0.3f);
            badge1.setAlpha(1.0f);
            badge2.setAlpha(1.0f);
            badge3.setAlpha(1.0f);
            badge4.setAlpha(1.0f);
            badge5.setAlpha(0.3f);
            badgeText1.setAlpha(1.0f);
            badgeText2.setAlpha(1.0f);
            badgeText3.setAlpha(1.0f);
            badgeText4.setAlpha(1.0f);
            badgeText5.setAlpha(0.3f);
            badgesEarn.setText("4");
            rank.setText("  PRO ");
        }
        else if( badge==5)
        {

            level1.setAlpha(1.0f);
            level2.setAlpha(1.0f);
            level3.setAlpha(1.0f);
            level4.setAlpha(1.0f);
            badge1.setAlpha(1.0f);
            badge2.setAlpha(1.0f);
            badge3.setAlpha(1.0f);
            badge4.setAlpha(1.0f);
            badgeText1.setAlpha(1.0f);
            badgeText2.setAlpha(1.0f);
            badgeText3.setAlpha(1.0f);
            badgeText4.setAlpha(1.0f);
            badgeText5.setAlpha(1.0f);
            badgesEarn.setText("5");
            rank.setText("  SUPER PRO   ");
        }


        badge2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog(2);

            }
        });

        badge3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog(3);

            }
        });

        badge4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog(4);

            }
        });

        badge5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog(5);

            }
        });






        return view;
    }


    public void onButtonPressed(Uri uri) {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }






    public void upgradeLevel(final int badge)
    {

        String url = "https://shreyaapi.herokuapp.com/progressAdd/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject object = new JSONObject(response);
                            String msg = object.getString(VariablesModels.msg);
                            int res= object.getInt(VariablesModels.res);

                            if(res==1){
                                Toast.makeText(getActivity(), "done", Toast.LENGTH_SHORT).show();
                                fetchProgressData();

                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(),"Something went wrong!", Toast.LENGTH_LONG).show();
                        //Toast.makeText(MedicineData.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put(VariablesModels.userId,clientId+"");
                params.put(VariablesModels.badge,badge+"");
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

    public void showdialog(final int number)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirm ");
        builder.setMessage("Are you sure?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               upgradeLevel(number);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity().getApplicationContext(), "You've changed your mind ", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        builder.show();
    }


    public void fetchProgressData() {
        final ProgressDialog progressDialog= new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching Data...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/getprogress/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {

                            Log.d("res",response);
                            JSONObject object = new JSONObject(response);
                            String msg = object.getString("msg");
                            int res= object.getInt("res");
                            JSONObject innerobject = object.getJSONObject(VariablesModels.response);

                            bells= innerobject.getInt("bells");
                            badge= innerobject.getInt("badge");
                            level = innerobject.getInt("level");
                            kgs=innerobject.getInt("kgslost");



                            numberOfBells.setText(bells+" bells rung");
                            kgslost.setText(kgs+ " lost");
                            if(badge==1)
                            {   level1.setAlpha(1.0f);
                                level2.setAlpha(0.3f);
                                level3.setAlpha(0.3f);
                                level4.setAlpha(0.3f);
                                badge1.setAlpha(1.0f);
                                badge2.setAlpha(0.3f);
                                badge3.setAlpha(0.3f);
                                badge4.setAlpha(0.3f);
                                badge5.setAlpha(0.3f);
                                badgesEarn.setText("1");
                                rank.setText("  NEWBIE ");

                            }
                            else if(badge==2)
                            {

                                level1.setAlpha(1.0f);
                                level2.setAlpha(1.0f);
                                level3.setAlpha(0.3f);
                                level4.setAlpha(0.3f);
                                badge1.setAlpha(1.0f);
                                badge2.setAlpha(1.0f);
                                badge3.setAlpha(0.3f);
                                badge4.setAlpha(0.3f);
                                badge5.setAlpha(0.3f);
                                badgesEarn.setText("2");
                                rank.setText("  SENIOR ");
                            }

                            else if(badge==3)
                            {

                                level1.setAlpha(1.0f);
                                level2.setAlpha(1.0f);
                                level3.setAlpha(1.0f);
                                level4.setAlpha(0.3f);
                                badge1.setAlpha(1.0f);
                                badge2.setAlpha(1.0f);
                                badge3.setAlpha(1.0f);
                                badge4.setAlpha(0.3f);
                                badge5.setAlpha(0.3f);
                                badgesEarn.setText("3");
                                rank.setText("  PRO ");
                            }
                            else if( badge==4)
                            {
                                level1.setAlpha(1.0f);
                                level2.setAlpha(1.0f);
                                level3.setAlpha(1.0f);
                                level4.setAlpha(0.3f);
                                badge1.setAlpha(1.0f);
                                badge2.setAlpha(1.0f);
                                badge3.setAlpha(1.0f);
                                badge4.setAlpha(1.0f);
                                badge5.setAlpha(0.3f);

                                badgesEarn.setText("4");
                                rank.setText("  PRO ");
                            }
                            else if( badge==5)
                            {
                                level1.setAlpha(1.0f);
                                level2.setAlpha(1.0f);
                                level3.setAlpha(1.0f);
                                level4.setAlpha(1.0f);
                                badge1.setAlpha(1.0f);
                                badge2.setAlpha(1.0f);
                                badge3.setAlpha(1.0f);
                                badge4.setAlpha(1.0f);
                                badge5.setAlpha(1.0f);

                                badgesEarn.setText("5");
                                rank.setText("  SUPER PRO   ");
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
                        Toast.makeText(getActivity(),"Something went wrong!", Toast.LENGTH_LONG).show();
                        //Toast.makeText(MedicineData.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put(VariablesModels.userId,clientId+"");
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
