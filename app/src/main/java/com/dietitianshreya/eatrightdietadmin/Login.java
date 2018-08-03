package com.dietitianshreya.eatrightdietadmin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dietitianshreya.eatrightdietadmin.Utils.Util;
import com.dietitianshreya.eatrightdietadmin.Utils.VariablesModels;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    Button signup,signin;
    EditText input_edit_useranme,input_edit_password;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    ProgressDialog progressDialog;
    private FirebaseAuth mFirebaseAuth;
    int flag_firebase=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        input_edit_useranme = (EditText) findViewById(R.id.input_edit_email);
        input_edit_password = (EditText) findViewById(R.id.input_edit_password);
        signin = (Button) findViewById(R.id.loginBtn);
        mFirebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Logging in...");

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;

                if (Tools.validateNormalText(input_edit_useranme) && Tools.validateNormalText(input_edit_password)) {
                    sendR(input_edit_useranme.getText().toString().trim(), input_edit_password.getText().toString().trim());
                } else {
                    Toast.makeText(getApplicationContext(), "Empty fields not allowed", Toast.LENGTH_SHORT).show();
                }

            }

        });
    }


    public void sendR(final String email, final String pass) {

        String url = "https://shreyaapi.herokuapp.com/dietitianlogin/";
        final ProgressDialog progressDialog = new ProgressDialog(Login.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Logging in...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject result1 = new JSONObject();
                        try {
                            result1 = new JSONObject(response);
                            if (result1.getInt("res") == 1&&result1.getString("loginstatus").equals("Y")) {
                                Log.d("res", response);
                                JSONObject result = result1.getJSONObject("response");
                                progressDialog.dismiss();
                                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putInt("clientId", result.getInt(VariablesModels.dietitianId));
                                editor.putString("user_name", result.getString(VariablesModels.user_name));
                                editor.putString("email", email);
                                editor.putString("image",result.getString("url"));
                                editor.putString("clinicId",result.getString("clinicId"));
                                //Send a request to double check if the user is confirmed or not
                                //if the user is confirmed, send directly to dashboard else send to not confirmed

//                                editor.putInt("confirmFlag",1);
                                editor.commit();
//                                if(mFirebaseAuth.getCurrentUser()==null)
                                registerinFirebase(email , pass);

                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), result1.getString("msg"), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getApplicationContext(), "Something went wrong!\nCheck your Internet connection and try again..", Toast.LENGTH_LONG).show();
                        //Toast.makeText(MedicineData.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", pass);

                return params;
            }

        };

        int MY_SOCKET_TIMEOUT_MS = 50000;
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    public void registerinFirebase(final String email, final String pass) {


        mFirebaseAuth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("lol", "createUserWithEmail:success");
                            Util.initToast(getApplicationContext(),"done");
                            flag_firebase=1;
                            finish();
                            startActivity(new Intent(Login.this,MainActivity.class));


                        }
                        else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(Login.this, "User with this email already exist.", Toast.LENGTH_SHORT).show();
                                Log.d("manya",flag_firebase+"");

                                finish();
                                startActivity(new Intent(Login.this,MainActivity.class));

                            }
                            else {
                                // If sign in fails, display a message to the user.
                                Log.w("lol", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(Login.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }

                    }
                });

    }
}

