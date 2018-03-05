package com.dietitianshreya.dtshreyaadmin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dietitianshreya.dtshreyaadmin.adapters.AppointmentsAdapter;
import com.dietitianshreya.dtshreyaadmin.models.AppointmentsModel;

import java.util.ArrayList;


public class DashboardFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    RecyclerView recyclerView;
    AppointmentsAdapter appointmentsAdapter;
    ArrayList<AppointmentsModel> appointmentsList;

    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();
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
        View rootView =  inflater.inflate(R.layout.fragment_dashboard, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.re);
        appointmentsList=new ArrayList<>();
        appointmentsAdapter = new AppointmentsAdapter(appointmentsList,getActivity());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(appointmentsAdapter);
        appointmentsList.add(new AppointmentsModel("Mr. Akshit Tyagi","9:30 A.M.","Punjabi Bagh"));
        appointmentsList.add(new AppointmentsModel("Mr. Paras Garg","11:30 A.M.","Punjabi Bagh"));
        appointmentsList.add(new AppointmentsModel("Ms. Manya Madan","12:30 P.M.","Skype call"));
        appointmentsAdapter.notifyDataSetChanged();

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
