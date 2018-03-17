package com.dietitianshreya.dtshreyaadmin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dietitianshreya.dtshreyaadmin.adapters.ClientAppointmentAdapter;
import com.dietitianshreya.dtshreyaadmin.models.ClientAppointmentModel;

import java.util.ArrayList;


public class ClientAppointmentFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private String clientId;
    RecyclerView recyclerView;
    ArrayList<ClientAppointmentModel> appointmentList;
    ClientAppointmentAdapter appointmentAdapter;
    private OnFragmentInteractionListener mListener;
    FloatingActionButton fab;

    public ClientAppointmentFragment() {
        // Required empty public constructor
    }

    public static ClientAppointmentFragment newInstance(String param1) {
        ClientAppointmentFragment fragment = new ClientAppointmentFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_client_appointment, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.re);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        appointmentList = new ArrayList<>();
        appointmentAdapter = new ClientAppointmentAdapter(appointmentList,getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(appointmentAdapter);
        appointmentList.add(new ClientAppointmentModel("02/02/2018","9:30 A.M.","In Person","Complete","Dt. Shreya"));
        appointmentList.add(new ClientAppointmentModel("02/02/2018","9:30 A.M.","In Person","Complete","Dt. Shreya"));
        appointmentList.add(new ClientAppointmentModel("02/02/2018","9:30 A.M.","In Person","Complete","Dt. Shreya"));
        appointmentList.add(new ClientAppointmentModel("02/02/2018","9:30 A.M.","In Person","Complete","Dt. Shreya"));
        appointmentAdapter.notifyDataSetChanged();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Handle Book appointment here
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
