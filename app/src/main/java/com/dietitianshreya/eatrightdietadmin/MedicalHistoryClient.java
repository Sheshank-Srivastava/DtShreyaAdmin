package com.dietitianshreya.eatrightdietadmin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
import com.dietitianshreya.eatrightdietadmin.Utils.VariablesModels;
import com.dietitianshreya.eatrightdietadmin.adapters.DiseaseAdapter;
import com.dietitianshreya.eatrightdietadmin.models.DiseaseChip;
import com.dietitianshreya.eatrightdietadmin.models.DiseaseModel;
import com.dietitianshreya.eatrightdietadmin.models.FoodChip;
import com.pchmn.materialchips.ChipsInput;
import com.pchmn.materialchips.model.ChipInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MedicalHistoryClient extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private OnFragmentInteractionListener mListener;
    Button save;
    EditText dieterName,dieterPhone,dieterEmail,dieterReferredby,dieterDob,dieterWhatsapp,dieterAddress,dieterOccupation,
            dieterGender,dieterHeight,dieterWeight,dieterAnniversary,
            dieterBloodPressure,dieterweightmanage,
            dieterIrregularPeriods,dieterDiabetes,dieterHypothyroid,dieterUricAcid,
            dieterHb,dieterVitd3,dieterCalcium,dieterHba1c,dietervitB12
            ,dieterSgpt,dieterBloodGroup,dieterBloodThinner,dieterLactation,dieterMedication,
            dieterTechnique,dieterLastLeastWeight,dieterVeg,dieterFoodAllergies,
            dieterLikes,dieterDislikes;
    int searchFlag = 0;
    JSONObject object = new JSONObject();
    DiseaseAdapter diseaseAdapter;
    ArrayList<DiseaseModel> diseaseList;
    String diseasetext;
    String clientId;
    String beforeText;
    JSONObject medicalObject = new JSONObject();
    JSONObject basicinfoobject = new JSONObject();// Basic info object
    JSONObject foodspecsObject = new JSONObject();//food specs object
    private ChipsInput mChipsInput;
    private List<FoodChip> items = new ArrayList<>();
    private List<ChipInterface> items_added = new ArrayList<>();
    TextView addDisease;

    public MedicalHistoryClient() {
        // Required empty public constructor
    }

    public static MedicalHistoryClient newInstance(String param1,String param2) {
        MedicalHistoryClient fragment = new MedicalHistoryClient();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            clientId = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_medical_history_client, container, false);
        dieterBloodPressure = rootView.findViewById(R.id.bp);
        dieterIrregularPeriods = rootView.findViewById(R.id.forFemale);
        dieterDiabetes = rootView.findViewById(R.id.diabetes);
        dieterHypothyroid = rootView.findViewById(R.id.hypothyroidism);
        dieterUricAcid = rootView.findViewById(R.id.uricAcid);
        dieterHb = rootView.findViewById(R.id.hb);
        dieterVitd3 = rootView.findViewById(R.id.vitD3);
        dieterCalcium = rootView.findViewById(R.id.calcium);
        dieterHba1c = rootView.findViewById(R.id.hbA1c);
        dietervitB12 = rootView.findViewById(R.id.vitB12);
        dieterSgpt = rootView.findViewById(R.id.sgot);
        dieterBloodGroup = rootView.findViewById(R.id.bloodGroup);
        dieterBloodThinner = rootView.findViewById(R.id.anyBloodThinner);
        dieterLactation = rootView.findViewById(R.id.lactation);
        dieterMedication = rootView.findViewById(R.id.medication);
//        dieterAnyOtherDisease = findViewById(R.id.otherDisease);
        dieterTechnique = rootView.findViewById(R.id.weightManagement);
        dieterLastLeastWeight = rootView.findViewById(R.id.lastLeastweight);
        mChipsInput = (ChipsInput) rootView.findViewById(R.id.chips_input);
        addDisease = (TextView) rootView.findViewById(R.id.addDisease);

        String udata = "Add Disease ";
        SpannableString content = new SpannableString(udata);
        content.setSpan(new UnderlineSpan(),0,udata.length(),0);
        addDisease.setText(content);
        diseaseList = new ArrayList<>();
        searchFlag=0;
        getDiseases();

        save = rootView.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveProfiledata();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        addDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDis();
            }
        });
        mChipsInput.setFilterableList(new ArrayList<ChipInterface>());
//        mChipsInput.getEditText().setEnabled(false);
        mChipsInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mChipsInput.getEditText().setText("");
                Toast.makeText(getActivity().getApplicationContext(),"Click add disease to enter!",Toast.LENGTH_SHORT).show();
            }
        });
        mChipsInput.addChipsListener(new ChipsInput.ChipsListener() {
            @Override
            public void onChipAdded(ChipInterface chip, int newSize) {
                items_added.add(chip);
            }

            @Override
            public void onChipRemoved(ChipInterface chip, int newSize) {
                items_added.remove(chip);
            }

            @Override
            public void onTextChanged(CharSequence text) {
                //Log.e(TAG, "text changed: " + text.toString());
            }
        });
        try {
            getJson(mParam1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    private void dialogDis() {
        final Dialog dialog = new Dialog(getActivity());
//        Log.d("lol",diseaseList.size()+"");
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_clients);
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        final ArrayList<DiseaseModel> filteredList = new ArrayList<DiseaseModel>();
        final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        diseaseAdapter= new DiseaseAdapter(diseaseList,getActivity());
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
                    DiseaseAdapter filteredAdapter = new DiseaseAdapter(filteredList, getActivity());
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
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) throws NoSuchFieldException, IllegalAccessException {
                if(searchFlag==0) {
                    DiseaseModel client = diseaseList.get(position);
                    String distxt= client.getDiseaseName();
                    if(distxt.length()>=18){
                        distxt=distxt.substring(0,15);
                        distxt+="..";
                    }
                    DiseaseChip chip = new DiseaseChip(distxt,client.getDiseaseName());
                    mChipsInput.addChip(chip);
                }else{
                    DiseaseModel client = filteredList.get(position);
                    String distxt= client.getDiseaseName();
                    if(distxt.length()>=18){
                        distxt=distxt.substring(0,15);
                        distxt+="..";
                    }
                    DiseaseChip chip = new DiseaseChip(distxt,client.getDiseaseName());
                    mChipsInput.addChip(chip);

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
                            diseaseList.clear();
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







                        } catch (JSONException e) {
                            Toast.makeText(getActivity().getApplicationContext(),"There is some Error",Toast.LENGTH_LONG).show();
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


        };

        int MY_SOCKET_TIMEOUT_MS = 50000;
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);



    }


    public void createJson() throws JSONException {
        medicalObject.put(VariablesModels.thinnerblood, dieterBloodThinner.getText().toString());
        medicalObject.put(VariablesModels.medication, dieterMedication.getText().toString());
        medicalObject.put(VariablesModels.uricacid, dieterUricAcid.getText().toString());

        JSONArray array = new JSONArray();
//            String disease_text = dieterAnyOtherDisease.getText().toString();
//            String[] list = disease_text.split("/");
        Log.d("Items added size",items_added.size()+"");
        for(int i=0;i<items_added.size();i++)
        {
            array.put(items_added.get(i).getInfo());

        }
        medicalObject.put(VariablesModels.anydisease, array);//new activity
        Log.d("Array",array+"");
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
//        sendR();
    }

    public void getJson(String response) throws JSONException {
        JSONObject object = new JSONObject(response);
        if (object.getInt("res") == 1) {
            JSONObject responseObject = object.getJSONObject("response");
            JSONObject medical = responseObject.getJSONObject("medicalhistory");
//            dieterBloodPressure.setText(medical.getString(VariablesModels.bp));
            //  dieterIrregularPeriods.setText(medical.getString(VariablesModels.periods));
            //  dieterDiabetes.setText(medical.getString(VariablesModels.diabetes));
            //   dieterHypothyroid.setText(medical.getString(VariablesModels.hypothyo));
            dieterUricAcid.setText(medical.getString(VariablesModels.uricacid));
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

            JSONArray disease = medical.getJSONArray(VariablesModels.anydisease);
            String dis = null;
            for (int i = 0; i < disease.length(); i++) {

                String distxt= disease.getString(i);
                if(distxt.length()>=18){
                    distxt=distxt.substring(0,15);
                    distxt+="..";
                }
                Log.d("Adding disease",disease.getString(i));
                DiseaseChip chip = new DiseaseChip(distxt,disease.getString(i));
                mChipsInput.addChip(chip);


            }

            // dieterweightmanage.setText(medical.getString(VariablesModels.weightmanage));
            dieterLastLeastWeight.setText(medical.getString(VariablesModels.lastleastweight));


            //Change after api changes
//            dieterweightmanage.setText(basicinfo.getString(VariablesModels.weightmanage));
        }


    }

    public void saveProfiledata() throws JSONException{
        createJson();
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Updating data...");
        progressDialog.setCancelable(false);
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

                            Toast.makeText(getActivity(),"Data saved!",Toast.LENGTH_SHORT).show();



                        } catch (Exception e) {
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

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);



    }
}
