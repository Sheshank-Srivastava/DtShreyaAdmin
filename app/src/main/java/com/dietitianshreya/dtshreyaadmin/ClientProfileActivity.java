package com.dietitianshreya.dtshreyaadmin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.dietitianshreya.dtshreyaadmin.adapters.AllClientListOthersAdapter;
import com.dietitianshreya.dtshreyaadmin.adapters.DiseaseAdapter;
import com.dietitianshreya.dtshreyaadmin.models.AllClientListOthersModel;
import com.dietitianshreya.dtshreyaadmin.models.DiseaseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientProfileActivity extends AppCompatActivity {
EditText dieterName,dieterPhone,dieterEmail,dieterReferredby,dieterDob,dieterWhatsapp,dieterAddress,dieterOccupation,
        dieterGender,dieterHeight,dieterWeight,dieterAnniversary,
        dieterBloodPressure,dieterweightmanage,
        dieterIrregularPeriods,dieterDiabetes,dieterHypothyroid,dieterUricAcid,
        dieterCholestrol,dieterHb,dieterVitd3,dieterCalcium,dieterHba1c,dietervitB12
        ,dieterSgpt,dieterBloodGroup,dieterBloodThinner,dieterLactation,dieterMedication,
    dieterAnyOtherDisease,dieterTechnique,dieterLastLeastWeight,dieterVeg,dieterFoodAllergies,
        dieterLikes,dieterDislikes;
    int searchFlag = 0;
    JSONObject object = new JSONObject();
    DiseaseAdapter diseaseAdapter;
    ArrayList<DiseaseModel> diseaseList;
    String diseasetext;
    String clientId;
    JSONObject medicalObject = new JSONObject();
    JSONObject basicinfoobject = new JSONObject();// Basic info object
    JSONObject foodspecsObject = new JSONObject();//food specs object


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile);

        Bundle extras = getIntent().getExtras();

        clientId = extras.getString(VariablesModels.userId);
        Log.d("clientid",clientId);
        dieterName = findViewById(R.id.clientName);
        dieterPhone = findViewById(R.id.clientPhone);
        dieterEmail = findViewById(R.id.clientEmail);
        dieterGender = findViewById(R.id.clientGender);
        dieterReferredby = findViewById(R.id.clientReferred);
        dieterHeight = findViewById(R.id.clientHeight);
        dieterWeight = findViewById(R.id.clientWeight);
        dieterDob = findViewById(R.id.clientdob);
        dieterAddress = findViewById(R.id.clientAddress);
        dieterOccupation = findViewById(R.id.clientOccupation);
        dieterWhatsapp = findViewById(R.id.clientEndDate);
        dieterBloodPressure = findViewById(R.id.bp);
        dieterAnniversary = findViewById(R.id.clientAnn);
        dieterIrregularPeriods = findViewById(R.id.forFemale);
        dieterDiabetes = findViewById(R.id.diabetes);
        dieterHypothyroid = findViewById(R.id.hypothyroidism);
        dieterUricAcid = findViewById(R.id.uricAcid);
        dieterCholestrol = findViewById(R.id.cholesterol);
        dieterHb = findViewById(R.id.hb);
        dieterVitd3 = findViewById(R.id.vitD3);
        dieterCalcium = findViewById(R.id.calcium);
        dieterHba1c = findViewById(R.id.hbA1c);
        dietervitB12 = findViewById(R.id.vitB12);
        dieterSgpt = findViewById(R.id.sgot);
        dieterBloodGroup = findViewById(R.id.bloodGroup);
        dieterBloodThinner = findViewById(R.id.anyBloodThinner);
        dieterLactation = findViewById(R.id.lactation);
        dieterMedication = findViewById(R.id.medication);
        dieterAnyOtherDisease = findViewById(R.id.otherDisease);
        dieterTechnique = findViewById(R.id.weightManagement);
        dieterLastLeastWeight = findViewById(R.id.lastLeastweight);
        dieterVeg = findViewById(R.id.vegNonVeg);
        dieterFoodAllergies = findViewById(R.id.allergy);
        dieterLikes = findViewById(R.id.likes);
        dieterDislikes = findViewById(R.id.dislikes);
        dieterweightmanage= findViewById(R.id.clientweightmanage);
        getProfiledata();
        dieterAnyOtherDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diseaseList = new ArrayList<>();
                searchFlag=0;


                getDiseases();


            }
        });
    }


    public void createJson() throws JSONException {

        object.put(VariablesModels.userId,"20"); //outer object


            foodspecsObject.put(VariablesModels.allergies, dieterFoodAllergies.getText().toString());
            foodspecsObject.put(VariablesModels.likes, dieterLikes.getText().toString());
            foodspecsObject.put(VariablesModels.dislikes, dieterDislikes.getText().toString());
            foodspecsObject.put(VariablesModels.vegnonveg, dieterVeg.getText().toString());


            basicinfoobject.put(VariablesModels.username, dieterName.getText().toString());
            basicinfoobject.put(VariablesModels.weight, dieterWeight.getText().toString());
            basicinfoobject.put(VariablesModels.referredby, dieterReferredby.getText().toString());
            basicinfoobject.put(VariablesModels.dob, dieterDob.getText().toString());
            basicinfoobject.put(VariablesModels.gender, dieterGender.getText().toString());
            basicinfoobject.put(VariablesModels.anniversary, dieterAnniversary.getText().toString());
            basicinfoobject.put(VariablesModels.height, dieterHeight.getText().toString());
            basicinfoobject.put(VariablesModels.contact, dieterPhone.getText().toString());
            basicinfoobject.put(VariablesModels.address, dieterAddress.getText().toString());
            basicinfoobject.put(VariablesModels.whatsapp, dieterWhatsapp.getText().toString());
            basicinfoobject.put(VariablesModels.email, dieterEmail.getText().toString());
            basicinfoobject.put(VariablesModels.occupation, dieterOccupation.getText().toString());


            medicalObject.put(VariablesModels.thinnerblood, dieterBloodThinner.getText().toString());
           medicalObject.put(VariablesModels.weightmanage, dieterweightmanage.getText().toString());
            medicalObject.put(VariablesModels.medication, dieterMedication.getText().toString());
            medicalObject.put(VariablesModels.cholestrol, dieterCholestrol.getText().toString());
            medicalObject.put(VariablesModels.uricacid, dieterUricAcid.getText().toString());

            JSONArray array = new JSONArray();
            String disease_text = dieterAnyOtherDisease.getText().toString();
            String[] list = disease_text.split("/");
            for(int i=0;i<list.length;i++)
            {
                array.put(list[i]);
            }
            medicalObject.put(VariablesModels.anydisease, array);//new activity
            medicalObject.put(VariablesModels.lastleastweight, dieterLastLeastWeight.getText().toString());
            medicalObject.put(VariablesModels.hba1c, dieterHba1c.getText().toString());
            medicalObject.put(VariablesModels.sgot, dieterSgpt.getText().toString());
            medicalObject.put(VariablesModels.calcium, dieterCalcium.getText().toString());
//            medicalObject.put(VariablesModels.hypothyo, dieterHypothyroid.getText().toString());
//            medicalObject.put(VariablesModels.bp, dieterBloodPressure.getText().toString());
         ////   medicalObject.put(VariablesModels.periods, dieterIrregularPeriods.getText().toString());
          //  medicalObject.put(VariablesModels.lactation, dieterLactation.getText().toString());
            medicalObject.put(VariablesModels.hb, dieterHb.getText().toString());
            medicalObject.put(VariablesModels.vitamind3, dieterVitd3.getText().toString());
            medicalObject.put(VariablesModels.bloodgroup, dieterBloodGroup.getText().toString());
            medicalObject.put(VariablesModels.vitb12, dietervitB12.getText().toString());
          //  medicalObject.put(VariablesModels.diabetes, dieterDiabetes.getText().toString());


            //################# outer object###############
            object.put(VariablesModels.foodspecification, foodspecsObject);
            object.put(VariablesModels.basicinfo, basicinfoobject);
            object.put(VariablesModels.medicalhistory, medicalObject);



    }




    private void dialogDis() {
        final Dialog dialog = new Dialog(this);
        Log.d("lol",diseaseList.size()+"");
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_clients);
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        final ArrayList<DiseaseModel> filteredList = new ArrayList<DiseaseModel>();
        final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        diseaseAdapter= new DiseaseAdapter(diseaseList,this);
       // Log.d("lol",diseaseList.get(1).getDiseaseName());

        recyclerView.setAdapter(diseaseAdapter);
        diseaseAdapter.notifyDataSetChanged();
        EditText search = (EditText) dialog.findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("before text changed",charSequence+"");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("on text changed",charSequence+"");
                if(!(TextUtils.isEmpty(charSequence))) {
                    searchFlag=1;
                    filteredList.clear();
                    DiseaseAdapter filteredAdapter = new DiseaseAdapter(filteredList, ClientProfileActivity.this);
                    for (DiseaseModel row : diseaseList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        String charString = charSequence + "";
                        if (row.getDiseaseName().toLowerCase().contains(charString.toLowerCase()) ) {
                            filteredList.add(row);
                        }
                    }
                    recyclerView.setAdapter(filteredAdapter);
                    filteredAdapter.notifyDataSetChanged();
                }else{
                    searchFlag=0;
                    recyclerView.setAdapter(diseaseAdapter);
                    diseaseAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("after text changed",editable.toString());
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) throws NoSuchFieldException, IllegalAccessException {
                if(searchFlag==0) {
                    DiseaseModel client = diseaseList.get(position);
                    //test_client.setText(client.getDiseaseName());
                    if(diseasetext==null)
                    {
                        diseasetext= client.getDiseaseName();
                        dieterAnyOtherDisease.setText(diseasetext);

                    }
                    else {
                        diseasetext = diseasetext + "/" + client.getDiseaseName();
                        dieterAnyOtherDisease.setText(diseasetext);
                    }
                    //id = client.getClientId();
                }else{
                    DiseaseModel client = filteredList.get(position);
                    if(diseasetext==null)
                    {
                        diseasetext= client.getDiseaseName();
                        dieterAnyOtherDisease.setText(diseasetext);
                    }
                    else {
                        diseasetext = diseasetext + "/" + client.getDiseaseName();
                        dieterAnyOtherDisease.setText(diseasetext);
                    }
                    // test_client.setText(client.getDiseaseName());
                   // id = client.getClientId();
                }
                dialog.dismiss();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }


    public void getDiseases()
    {



        String url = "https://shreyaapi.herokuapp.com/getdiseases/";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            Log.d("res",response);
                            JSONObject object = new JSONObject(response);
                            String msg = object.getString("msg");
                            int res= object.getInt("res");
                            if(res==1) {
                                JSONArray array = object.getJSONArray(VariablesModels.response);
                                for(int i =0;i<array.length();i++)
                                {
                                    diseaseList.add(new DiseaseModel(array.getString(i)));
                                }
                                Log.d("lolwa",diseaseList.size()+"");
                            }


                            dialogDis();




                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),"There is some Error",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),"Something went wrong!", Toast.LENGTH_LONG).show();
                        //Toast.makeText(MedicineData.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){


        };

        int MY_SOCKET_TIMEOUT_MS = 50000;
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);



    }



    public void getProfiledata()  {

        final ProgressDialog progressDialog = new ProgressDialog(ClientProfileActivity.this);
        progressDialog.setMessage("Updating data...");
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/getprofiledata/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {

                          Log.d("response",response);

                            progressDialog.dismiss();

                            getJson(response);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Something went wrong!", Toast.LENGTH_LONG).show();
                        //Toast.makeText(MedicineData.this,error.toString(),Toast.LENGTH_LONG).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);



    }


    public void getJson(String response) throws JSONException {
        JSONObject object = new JSONObject(response);
        if (object.getInt("res") == 1) {
            JSONObject responseObject = object.getJSONObject("response");
            JSONObject basicinfo = responseObject.getJSONObject("basicinfo");
            dieterName.setText(basicinfo.getString(VariablesModels.username));
            dieterDob.setText(basicinfo.getString(VariablesModels.dob));
            dieterAnniversary.setText(basicinfo.getString(VariablesModels.anniversary));
            dieterPhone.setText(basicinfo.getString(VariablesModels.contact));
            dieterWhatsapp.setText(basicinfo.getString(VariablesModels.whatsapp));
            dieterOccupation.setText(basicinfo.getString(VariablesModels.occupation));
            dieterAddress.setText(basicinfo.getString(VariablesModels.address));
            dieterEmail.setText(basicinfo.getString(VariablesModels.email));
            dieterReferredby.setText(basicinfo.getString(VariablesModels.referredby));
            dieterWeight.setText(basicinfo.getString(VariablesModels.weight));
            dieterHeight.setText(basicinfo.getString(VariablesModels.height));
            dieterGender.setText(basicinfo.getString(VariablesModels.gender));

            JSONObject food = responseObject.getJSONObject("foodspecification");
            dieterVeg.setText(food.getString(VariablesModels.vegnonveg));
            dieterFoodAllergies.setText(food.getString(VariablesModels.allergies));
            dieterLikes.setText(food.getString(VariablesModels.likes));
            dieterDislikes.setText(food.getString(VariablesModels.dislikes));


            JSONObject medical = responseObject.getJSONObject("medicalhistory");
//            dieterBloodPressure.setText(medical.getString(VariablesModels.bp));
          //  dieterIrregularPeriods.setText(medical.getString(VariablesModels.periods));
          //  dieterDiabetes.setText(medical.getString(VariablesModels.diabetes));
         //   dieterHypothyroid.setText(medical.getString(VariablesModels.hypothyo));
            dieterUricAcid.setText(medical.getString(VariablesModels.uricacid));
            dieterCholestrol.setText(medical.getString(VariablesModels.cholestrol));
            dieterHb.setText(medical.getString(VariablesModels.hb));
            dieterVitd3.setText(medical.getString(VariablesModels.vitamind3));
            dieterCalcium.setText(medical.getString(VariablesModels.calcium));
            dieterHba1c.setText(medical.getString(VariablesModels.hba1c));
            dietervitB12.setText(medical.getString(VariablesModels.vitb12));
            dieterSgpt.setText(medical.getString(VariablesModels.sgot));
            dieterBloodGroup.setText(medical.getString(VariablesModels.bloodgroup));
            dieterBloodThinner.setText(medical.getString(VariablesModels.thinnerblood));
         //   dieterLactation.setText(medical.getString(VariablesModels.lactation));
            dieterMedication.setText(medical.getString(VariablesModels.medication));
            dieterweightmanage.setText(medical.getString(VariablesModels.weightmanage));

            JSONArray disease = medical.getJSONArray(VariablesModels.anydisease);
            String dis = null;
            for (int i = 0; i < disease.length(); i++) {
                if (i == 0) {
                    dis = disease.getString(0);

                } else {
                    dis = dis + " + " + disease.getString(i);
                }

            }
            dieterAnyOtherDisease.setText(dis);

           // dieterweightmanage.setText(medical.getString(VariablesModels.weightmanage));
            dieterLastLeastWeight.setText(medical.getString(VariablesModels.lastleastweight));



        }
    }


    public void getSaveProfiledata() throws JSONException {
        createJson();
        final ProgressDialog progressDialog = new ProgressDialog(ClientProfileActivity.this);
        progressDialog.setMessage("Updating data...");
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/saveprofiledata/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {

                            Log.d("response",response);

                            progressDialog.dismiss();




                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Something went wrong!", Toast.LENGTH_LONG).show();
                        //Toast.makeText(MedicineData.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put(VariablesModels.userId,clientId);
                params.put(VariablesModels.foodspecification,foodspecsObject+"");
                params.put(VariablesModels.medicalhistory,medicalObject+"");
                params.put(VariablesModels.basicinfo,basicinfoobject+"");

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.saveprofile,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

       if(id == R.id.save){
           try {
               getSaveProfiledata();
           } catch (JSONException e) {
               e.printStackTrace();
           }
       }
        return super.onOptionsItemSelected(item);
    }




}
