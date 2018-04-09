package com.dietitianshreya.dtshreyaadmin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dietitianshreya.dtshreyaadmin.adapters.AppointmentHistoryAdapter;
import com.dietitianshreya.dtshreyaadmin.models.AppointmentDetailsModel;
import com.dietitianshreya.dtshreyaadmin.models.AppointmentHistoryModel;

import java.util.ArrayList;


public class AppointmentFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    RecyclerView recyclerView;
    ArrayList<AppointmentHistoryModel> appointmentList;
    ArrayList<AppointmentDetailsModel> appointmentDetailsList;
    AppointmentHistoryAdapter appointmentHistoryAdapter;
    FloatingActionButton fab;
CoordinatorLayout coordinatorLayout;
    public AppointmentFragment() {
        // Required empty public constructor
    }

    public static AppointmentFragment newInstance() {
        AppointmentFragment fragment = new AppointmentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_appointment,null);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.re);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        coordinatorLayout = rootView.findViewById(R.id.appointment_coordinator);
        coordinatorLayout.setLayoutParams(new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT,CoordinatorLayout.LayoutParams.MATCH_PARENT));
        appointmentList=new ArrayList<>();
        appointmentDetailsList = new ArrayList<>();
        appointmentHistoryAdapter = new AppointmentHistoryAdapter(appointmentList,getActivity());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(appointmentHistoryAdapter);
        appointmentDetailsList.add(new AppointmentDetailsModel("9:30 A.M.","In Person","Completed","Akshit Tyagi","4 days left","12345"));
        appointmentDetailsList.add(new AppointmentDetailsModel("10:30 A.M.","In Person","Completed","Akshit Tyagi","4 days left","12345"));
        appointmentDetailsList.add(new AppointmentDetailsModel("11:30 A.M.","In Person","Completed","Akshit Tyagi","4 days left","12345"));
        appointmentList.add(new AppointmentHistoryModel("10/02/2018",appointmentDetailsList));

        ArrayList<AppointmentDetailsModel> appointmentDetailsList1 = new ArrayList<>();
        appointmentDetailsList1.add(new AppointmentDetailsModel("9:30 A.M.","In Person","Completed","Akshit Tyagi","4 days left","12345"));
        appointmentDetailsList1.add(new AppointmentDetailsModel("9:30 A.M.","In Person","Completed","Akshit Tyagi","4 days left","12345"));
        appointmentDetailsList1.add(new AppointmentDetailsModel("9:30 A.M.","In Person","Completed","Akshit Tyagi","4 days left","12345"));
        appointmentList.add(new AppointmentHistoryModel("11/02/2018",appointmentDetailsList1));

        appointmentHistoryAdapter.notifyDataSetChanged();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getActivity(),BookAppointment.class);
                startActivity(intent);
            }
        });
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
