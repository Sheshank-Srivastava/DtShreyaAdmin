package com.dietitianshreya.dtshreyaadmin;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.dietitianshreya.dtshreyaadmin.adapters.ClinicalNotesAdapter;

import java.util.ArrayList;

public class ClinicalNotesFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private String clientId;

    private OnFragmentInteractionListener mListener;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    ArrayList<String> notes;
    ClinicalNotesAdapter clinicalNotesAdapter;
    EditText editText;

    public ClinicalNotesFragment() {
        // Required empty public constructor
    }

    public static ClinicalNotesFragment newInstance(String param1) {
        ClinicalNotesFragment fragment = new ClinicalNotesFragment();
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
        View rootView =  inflater.inflate(R.layout.fragment_clinical_notes, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.re);
        notes = new ArrayList<>();
        clinicalNotesAdapter = new ClinicalNotesAdapter(notes,getContext());
        notes.add("Allergic to arbi");
        notes.add("Allergic to arbi");
        notes.add("Allergic to arbi");
        notes.add("Allergic to arbi");
        notes.add("Allergic to arbi");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(clinicalNotesAdapter);
        clinicalNotesAdapter.notifyDataSetChanged();
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                // Get the layout inflater
                LayoutInflater linf = LayoutInflater.from(getActivity());
                final View inflator = linf.inflate(R.layout.custom_dialog, null);
                alertDialog.setTitle("Add a note...");
                alertDialog.setView(inflator);
                editText = (EditText) inflator.findViewById(R.id.quant);
                editText.setHint("Type here the note");
                TextView text = (TextView) inflator.findViewById(R.id.text);
                TextView dot = (TextView) inflator.findViewById(R.id.dot);
                text.setVisibility(View.GONE);
                dot.setVisibility(View.GONE);
                alertDialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!(TextUtils.isEmpty(editText.getText().toString().trim()))){
                            notes.add(editText.getText().toString().trim());
                            clinicalNotesAdapter.notifyDataSetChanged();
                        }
                    }
                });
                alertDialog.show();

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
