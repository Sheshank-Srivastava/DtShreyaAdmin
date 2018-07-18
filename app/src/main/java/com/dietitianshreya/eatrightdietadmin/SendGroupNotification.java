package com.dietitianshreya.eatrightdietadmin;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.dietitianshreya.eatrightdietadmin.Login.MyPREFERENCES;
import static com.dietitianshreya.eatrightdietadmin.Utils.MyFirebaseInstanceIdService.SharedPrefToken;

public class SendGroupNotification extends AppCompatActivity {

    EditText notificationbody,notificationTitle;
    TextView label;
    RadioButton all,payment,finalMonth;
    Button send;
    String tag = "0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_group_notification);
        getSupportActionBar().setTitle("Send Notification");
        notificationbody = (EditText) findViewById(R.id.body);
        notificationTitle = (EditText) findViewById(R.id.title);
        label = (TextView) findViewById(R.id.textLabel);
        all = (RadioButton) findViewById(R.id.all);
        payment = (RadioButton) findViewById(R.id.paymentPendingUsers);
        finalMonth = (RadioButton) findViewById(R.id.finalMonth);
        send = (Button) findViewById(R.id.send);
        notificationbody.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                label.setText(charSequence.length()+"/1024");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(Tools.validateNormalText(notificationTitle))){
                    Toast.makeText(SendGroupNotification.this,"Title of the notification can't be empty",Toast.LENGTH_SHORT).show();
                }else{
                    if(Tools.validateNormalText(notificationbody)){
                        if(all.isChecked()){
                            tag="1";
                        }else if(finalMonth.isChecked()){
                            tag="2";
                        }else if(payment.isChecked()){
                            tag="3";
                        }

                        if(tag.equals("0")){
                            Toast.makeText(SendGroupNotification.this,"Please select the audience",Toast.LENGTH_SHORT).show();
                        }else{
                            SharedPreferences sharedpreferences1 = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                            String userid= String.valueOf(sharedpreferences1.getInt("clientId",0));
                            sendR(userid,notificationTitle.getText().toString().trim(),notificationbody.getText().toString().trim());
                        }
                    }else{
                        Toast.makeText(SendGroupNotification.this,"Notification body can't be empty",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void sendR(final String userid,final String title,final String body) {

        String url = "https://shreyaapi.herokuapp.com/notify/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject result=new JSONObject();
                        try {
                            result = new JSONObject(response);
                            if(result.getInt("res")==1){
                                Log.d("res",response);
                                Toast.makeText(SendGroupNotification.this,"Notification Sent!",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(SendGroupNotification.this,"Sending failed! Try again later.",Toast.LENGTH_SHORT).show();
//                                Toast.makeText(getApplicationContext(),result.getString("msg"),Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),"Something went wrong!\nCheck your Internet connection and try again..", Toast.LENGTH_LONG).show();
                        //Toast.makeText(MedicineData.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("dietitianId",userid);
                params.put("title",title);
                params.put("body",body);
                params.put("tag",tag);

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
