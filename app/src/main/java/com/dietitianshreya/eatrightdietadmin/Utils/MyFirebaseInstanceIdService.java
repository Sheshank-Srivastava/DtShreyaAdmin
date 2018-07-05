package com.dietitianshreya.eatrightdietadmin.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;




public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    public static final String SharedPrefToken = "TOKEN";
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("MY TAG", "Refreshed token: " + refreshedToken);


        SharedPreferences sharedpreferences = getSharedPreferences(SharedPrefToken, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("token", refreshedToken);
        editor.putInt("change",1);
        editor.commit();
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(refreshedToken);
    }
}
