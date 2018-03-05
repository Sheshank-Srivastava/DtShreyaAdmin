package com.dietitianshreya.dtshreyaadmin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class BasicInfoFragment extends Fragment {
    private static final String ARG_PARAM1 = "id";

    private String clientId;
    TextView name,phone,email,gender,age,height,weight,disease,plan,endDate;
    String nameTxt,phoneTxt,emailTxt,genderTxt,ageTxt,heightTxt,weightTxt,diseaseTxt,planTxt,endDateTxt;
    FloatingActionButton fab;
    private OnFragmentInteractionListener mListener;

    public BasicInfoFragment() {
        // Required empty public constructor
    }

    public static BasicInfoFragment newInstance(String param1) {
        BasicInfoFragment fragment = new BasicInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
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
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_basic_info, container, false);
        name = (TextView) rootView.findViewById(R.id.clientName);
        phone = (TextView) rootView.findViewById(R.id.clientPhone);
        email = (TextView) rootView.findViewById(R.id.clientEmail);
        gender = (TextView) rootView.findViewById(R.id.clientGender);
        age = (TextView) rootView.findViewById(R.id.clientAge);
        height = (TextView) rootView.findViewById(R.id.clientHeight);
        weight = (TextView) rootView.findViewById(R.id.clientWeight);
        disease = (TextView) rootView.findViewById(R.id.clientDisease);
        plan = (TextView) rootView.findViewById(R.id.clientPlan);
        endDate = (TextView) rootView.findViewById(R.id.clientEndDate);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),ChatActivity.class);
                i.putExtra("clientId",clientId);
                getActivity().startActivity(i);
            }
        });
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        nameTxt = "Akshit Tyagi";
        phoneTxt = "9990105832";
        emailTxt = "a.tyagi20596@gmail.com";
        genderTxt = "Male";
        ageTxt = "21";
        heightTxt = "5.5";
        weightTxt = "65";
        diseaseTxt = "none";
        planTxt = "6 months";
        endDateTxt = "20/05/2018";
        //Setting the data to TextView
        name.setText(nameTxt);
        phone.setText(phoneTxt);
        email.setText(emailTxt);
        gender.setText(genderTxt);
        age.setText(ageTxt);
        height.setText(heightTxt);
        weight.setText(weightTxt);
        disease.setText(diseaseTxt);
        plan.setText(planTxt);
        endDate.setText(endDateTxt);
        activity.getSupportActionBar().setTitle(nameTxt);
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
}
