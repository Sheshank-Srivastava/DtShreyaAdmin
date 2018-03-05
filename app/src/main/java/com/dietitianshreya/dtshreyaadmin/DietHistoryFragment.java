package com.dietitianshreya.dtshreyaadmin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;


public class DietHistoryFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private String clientId;
    DatePicker datePicker;
    Button button;
    FloatingActionButton fab;
    private OnFragmentInteractionListener mListener;

    public DietHistoryFragment() {
        // Required empty public constructor
    }

    public static DietHistoryFragment newInstance(String param1) {
        DietHistoryFragment fragment = new DietHistoryFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_diet_history, container, false);
        datePicker = (DatePicker) rootView.findViewById(R.id.datePicker);
        button = (Button) rootView.findViewById(R.id.viewDiet);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),CreateDiet.class);
                i.putExtra("clientId",clientId);
                startActivity(i);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Date = "";
                Date += datePicker.getDayOfMonth()+"/";
                Date += (datePicker.getMonth()+1)+"/";
                Date += datePicker.getYear();

                Intent i = new Intent(getActivity(),ViewDietActivity.class);
                i.putExtra("date",Date);
                i.putExtra("day",datePicker.getDayOfMonth()+"");
                i.putExtra("month",datePicker.getMonth()+"");
                i.putExtra("year",datePicker.getYear()+"");
                i.putExtra("clientId",clientId);
                getActivity().startActivity(i);
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
