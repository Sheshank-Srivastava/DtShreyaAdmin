package com.dietitianshreya.eatrightdietadmin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dietitianshreya.eatrightdietadmin.adapters.ReviewsAdapter;
import com.dietitianshreya.eatrightdietadmin.models.ReviewsModel;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.dietitianshreya.eatrightdietadmin.Login.MyPREFERENCES;

public class DietitianProfileReviews extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ReviewsModel> list;
    ReviewsAdapter adapter;
    TextView name,avgRating;
    CircularImageView image;
    RatingBar ratingBar;
    String userid,username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietitian_profile_reviews);
        getSupportActionBar().setTitle("Profile");
        SharedPreferences sharedpreferences =getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        userid = String.valueOf(sharedpreferences.getInt("clientId",0));
        username = sharedpreferences.getString("user_name","Null");
        recyclerView = (RecyclerView)findViewById(R.id.re);
        name = (TextView) findViewById(R.id.name);
        avgRating = (TextView) findViewById(R.id.avgRating);
        image = (CircularImageView) findViewById(R.id.image);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setIsIndicator(true);
        list=new ArrayList<>();
        adapter = new ReviewsAdapter(list,this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        name.setText(username);
        getReview();
//        list.add(new ReviewsModel(3.5f,"Akshit tyagi(12345)","dfjhgfjdgsjfhhsdcbdcbdhfgdhfgkfdgshfdshkfbhfbehfvsfhskhfdvsjfbshfsdvhcvdsbfhdsvfhdgsfvgshfvsafvdsghkvjfdsfvsdhkfvdshfasfhsdvaf"));
//        list.add(new ReviewsModel(3.5f,"Akshit tyagi(12345)","dfjhgfjdgsjfhhsdcbdcbdhfgdhfgkfdgshfdshkfbhfbehfvsfhskhfdvsjfbshfsdvhcvdsbfhdsvfhdgsfvgshfvsafvdsghkvjfdsfvsdhkfvdshfasfhsdvaf"));
//        list.add(new ReviewsModel(3.5f,"Akshit tyagi(12345)","dfjhgfjdgsjfhhsdcbdcbdhfgdhfgkfdgshfdshkfbhfbehfvsfhskhfdvsjfbshfsdvhcvdsbfhdsvfhdgsfvgshfvsafvdsghkvjfdsfvsdhkfvdshfasfhsdvaf"));
//        list.add(new ReviewsModel(3.5f,"Akshit tyagi(12345)","dfjhgfjdgsjfhhsdcbdcbdhfgdhfgkfdgshfdshkfbhfbehfvsfhskhfdvsjfbshfsdvhcvdsbfhdsvfhdgsfvgshfvsafvdsghkvjfdsfvsdhkfvdshfasfhsdvaf"));
//        list.add(new ReviewsModel(3.5f,"Akshit tyagi(12345)","dfjhgfjdgsjfhhsdcbdcbdhfgdhfgkfdgshfdshkfbhfbehfvsfhskhfdvsjfbshfsdvhcvdsbfhdsvfhdgsfvgshfvsafvdsghkvjfdsfvsdhkfvdshfasfhsdvaf"));
//        list.add(new ReviewsModel(3.5f,"Akshit tyagi(12345)","dfjhgfjdgsjfhhsdcbdcbdhfgdhfgkfdgshfdshkfbhfbehfvsfhskhfdvsjfbshfsdvhcvdsbfhdsvfhdgsfvgshfvsafvdsghkvjfdsfvsdhkfvdshfasfhsdvaf"));
//        list.add(new ReviewsModel(3.5f,"Akshit tyagi(12345)","dfjhgfjdgsjfhhsdcbdcbdhfgdhfgkfdgshfdshkfbhfbehfvsfhskhfdvsjfbshfsdvhcvdsbfhdsvfhdgsfvgshfvsafvdsghkvjfdsfvsdhkfvdshfasfhsdvaf"));
//        list.add(new ReviewsModel(3.5f,"Akshit tyagi(12345)","dfjhgfjdgsjfhhsdcbdcbdhfgdhfgkfdgshfdshkfbhfbehfvsfhskhfdvsjfbshfsdvhcvdsbfhdsvfhdgsfvgshfvsafvdsghkvjfdsfvsdhkfvdshfasfhsdvaf"));
//        list.add(new ReviewsModel(3.5f,"Akshit tyagi(12345)","dfjhgfjdgsjfhhsdcbdcbdhfgdhfgkfdgshfdshkfbhfbehfvsfhskhfdvsjfbshfsdvhcvdsbfhdsvfhdgsfvgshfvsafvdsghkvjfdsfvsdhkfvdshfasfhsdvaf"));
//        list.add(new ReviewsModel(3.5f,"Akshit tyagi(12345)","dfjhgfjdgsjfhhsdcbdcbdhfgdhfgkfdgshfdshkfbhfbehfvsfhskhfdvsjfbshfsdvhcvdsbfhdsvfhdgsfvgshfvsafvdsghkvjfdsfvsdhkfvdshfasfhsdvaf"));
//        list.add(new ReviewsModel(3.5f,"Akshit tyagi(12345)","dfjhgfjdgsjfhhsdcbdcbdhfgdhfgkfdgshfdshkfbhfbehfvsfhskhfdvsjfbshfsdvhcvdsbfhdsvfhdgsfvgshfvsafvdsghkvjfdsfvsdhkfvdshfasfhsdvaf"));
//        list.add(new ReviewsModel(3.5f,"Akshit tyagi(12345)","dfjhgfjdgsjfhhsdcbdcbdhfgdhfgkfdgshfdshkfbhfbehfvsfhskhfdvsjfbshfsdvhcvdsbfhdsvfhdgsfvgshfvsafvdsghkvjfdsfvsdhkfvdshfasfhsdvaf"));
//        list.add(new ReviewsModel(3.5f,"Akshit tyagi(12345)","dfjhgfjdgsjfhhsdcbdcbdhfgdhfgkfdgshfdshkfbhfbehfvsfhskhfdvsjfbshfsdvhcvdsbfhdsvfhdgsfvgshfvsafvdsghkvjfdsfvsdhkfvdshfasfhsdvaf"));
//        list.add(new ReviewsModel(3.5f,"Akshit tyagi(12345)","dfjhgfjdgsjfhhsdcbdcbdhfgdhfgkfdgshfdshkfbhfbehfvsfhskhfdvsjfbshfsdvhcvdsbfhdsvfhdgsfvgshfvsafvdsghkvjfdsfvsdhkfvdshfasfhsdvaf"));
//        list.add(new ReviewsModel(3.5f,"Akshit tyagi(12345)","dfjhgfjdgsjfhhsdcbdcbdhfgdhfgkfdgshfdshkfbhfbehfvsfhskhfdvsjfbshfsdvhcvdsbfhdsvfhdgsfvgshfvsafvdsghkvjfdsfvsdhkfvdshfasfhsdvaf"));
//        adapter.notifyDataSetChanged();

    }


    public void getReview() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Reviews...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/getreview/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            progressDialog.dismiss();
                            JSONObject result = new JSONObject(response);
                            if(result.getInt("res")==1) {
                                Log.d("res",response);
                                JSONArray ar = result.getJSONArray("response");
                                for(int i=0;i<ar.length();i++){
                                    JSONObject ob = ar.getJSONObject(i);
                                    String id = String.valueOf(ob.getInt("id"));
                                    String name = ob.getString("name");
                                    String review = ob.getString("review");
                                    Float star = Float.parseFloat(ob.getString("star"));
                                    list.add(new ReviewsModel(star,name+"("+id+")",review));
                                }
                                adapter.notifyDataSetChanged();
                                avgRating.setText(String.valueOf(result.getInt("average")));
                                ratingBar.setRating(Float.parseFloat(String.valueOf(result.getInt("average"))));

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
                        Toast.makeText(getApplicationContext(),"Something went wrong!\nCheck your Internet connection and try again..", Toast.LENGTH_LONG).show();
                        //Toast.makeText(MedicineData.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("id",userid);
                return params;
            }

        };

        int MY_SOCKET_TIMEOUT_MS = 50000;
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
