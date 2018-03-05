package com.dietitianshreya.dtshreyaadmin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dietitianshreya.dtshreyaadmin.adapters.AllClientsCompoundAdapter;
import com.dietitianshreya.dtshreyaadmin.models.AllClientListOthersModel;
import com.dietitianshreya.dtshreyaadmin.models.AllClientsCompoundModel;

import java.util.ArrayList;


public class AllClientsDetails extends Fragment {

    private OnFragmentInteractionListener mListener;
    RecyclerView recyclerView;
    ArrayList<AllClientsCompoundModel> clientList;
    ArrayList<AllClientListOthersModel> clientListDetails;
    AllClientsCompoundAdapter clientListAdapter;

    public AllClientsDetails() {
        // Required empty public constructor
    }

    public static AllClientsDetails newInstance() {
        AllClientsDetails fragment = new AllClientsDetails();
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
        View rootView =  inflater.inflate(R.layout.fragment_all_clients_details, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.re);
        clientList=new ArrayList<>();
        clientListDetails=new ArrayList<>();
        clientListAdapter = new AllClientsCompoundAdapter(clientList,getActivity());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(clientListAdapter);

        clientListDetails.add(new AllClientListOthersModel("Akshit Tyagi","Id : 345rde3","0"));
        clientListDetails.add(new AllClientListOthersModel("Paras Garg","Id : 345rde3","0"));
        clientListDetails.add(new AllClientListOthersModel("Balkeerat","Id : 345rde3","0"));
        clientListDetails.add(new AllClientListOthersModel("Manya ","Id : 345rde3","0"));
        clientList.add(new AllClientsCompoundModel("Pending Diet Creation",clientListDetails));

        ArrayList<AllClientListOthersModel> clientDetailsList1 = new ArrayList<>();
        clientDetailsList1.add(new AllClientListOthersModel("Akshit Tyagi","Id : 345rde3","4"));
        clientDetailsList1.add(new AllClientListOthersModel("Akshit Tyagi","Id : 345rde3","4"));
        clientDetailsList1.add(new AllClientListOthersModel("Akshit Tyagi","Id : 345rde3","4"));
        clientDetailsList1.add(new AllClientListOthersModel("Akshit Tyagi","Id : 345rde3","4"));
        clientDetailsList1.add(new AllClientListOthersModel("Akshit Tyagi","Id : 345rde3","4"));
        clientList.add(new AllClientsCompoundModel("Other Clients",clientDetailsList1));
        clientListAdapter.notifyDataSetChanged();


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
