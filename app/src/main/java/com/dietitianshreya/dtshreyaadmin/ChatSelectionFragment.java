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

import com.dietitianshreya.dtshreyaadmin.adapters.ClientListAdapter;
import com.dietitianshreya.dtshreyaadmin.models.ClientListModel;

import java.util.ArrayList;


public class ChatSelectionFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    RecyclerView recyclerView;
    ArrayList<ClientListModel> clientList;
    ClientListAdapter clientListAdapter;

    public ChatSelectionFragment() {
        // Required empty public constructor
    }


    public static ChatSelectionFragment newInstance(String param1, String param2) {
        ChatSelectionFragment fragment = new ChatSelectionFragment();
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
        View rootView =  inflater.inflate(R.layout.fragment_chat_selection, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.re);
        clientList=new ArrayList<>();
        clientListAdapter = new ClientListAdapter(clientList,getActivity());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(clientListAdapter);
        clientList.add(new ClientListModel("Akshit Tyagi","Id : 345rde3","4 days left"));
        clientList.add(new ClientListModel("Paras Garg","Id : 345rde3","4 days left"));
        clientList.add(new ClientListModel("Balkeerat","Id : 345rde3","4 days left"));
        clientList.add(new ClientListModel("Manya ","Id : 345rde3","4 days left"));
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
