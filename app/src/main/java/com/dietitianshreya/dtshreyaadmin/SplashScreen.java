package com.dietitianshreya.dtshreyaadmin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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



public class SplashScreen extends AppCompatActivity {

    String MyPREFERENCES = "MyPrefs" ;
    int clientId;
    private static int SPLASH_TIME_OUT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        clientId = sharedpreferences.getInt("clientId",0);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if(clientId==0){
                    //new User, send to login screen
                    finish();
                    startActivity(new Intent(SplashScreen.this,Login.class));
                    //do not forget to call api in signin to check confirmation
                }else{
                    //user is confirmed and already logged in
                    //Send to dashboard directly
                    finish();
                    startActivity(new Intent(SplashScreen.this,MainActivity.class));
                }

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);


    }



}
