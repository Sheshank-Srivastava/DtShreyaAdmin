package com.dietitianshreya.dtshreyaadmin;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dietitianshreya.dtshreyaadmin.adapters.CompositionAdapter;
import com.dietitianshreya.dtshreyaadmin.models.ChooseMealModel;
import com.dietitianshreya.dtshreyaadmin.models.CompositionModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class BodyCompositionFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private String clientId;

    private OnFragmentInteractionListener mListener;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    ArrayList<CompositionModel> compositionList;
    CompositionAdapter compositionAdapter;
    EditText date,fat,water,muscle,bone,weight;

    public BodyCompositionFragment() {
        // Required empty public constructor
    }

    public static BodyCompositionFragment newInstance(String param1) {
        BodyCompositionFragment fragment = new BodyCompositionFragment();
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
        View rootView =  inflater.inflate(R.layout.fragment_body_composition, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.re);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        compositionList = new ArrayList<>();
        compositionAdapter = new CompositionAdapter(compositionList,getContext());
        recyclerView.setAdapter(compositionAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        compositionList.add(new CompositionModel("10/02/2018","80 kg","20","50","30","70"));
        compositionList.add(new CompositionModel("10/02/2018","80 kg","20","50","30","70"));
        compositionList.add(new CompositionModel("10/02/2018","80 kg","20","50","30","70"));
        compositionList.add(new CompositionModel("10/02/2018","80 kg","20","50","30","70"));
        compositionList.add(new CompositionModel("10/02/2018","80 kg","20","50","30","70"));
        compositionList.add(new CompositionModel("10/02/2018","80 kg","20","50","30","70"));
        compositionAdapter.notifyDataSetChanged();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                // Get the layout inflater
                LayoutInflater linf = LayoutInflater.from(getActivity());
                final View inflator = linf.inflate(R.layout.add_composition_data, null);
                // Setting Dialog Message
                alertDialog.setMessage("Add Composition Data");
                alertDialog.setView(inflator);
                date = (EditText) inflator.findViewById(R.id.date);
                Calendar calendar = Calendar.getInstance();
                Date c = calendar.getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                final String dateText = df.format(c);
                date.setText(dateText);
                weight = (EditText) inflator.findViewById(R.id.weight);
                fat = (EditText) inflator.findViewById(R.id.fat);
                water = (EditText) inflator.findViewById(R.id.water);
                muscle = (EditText) inflator.findViewById(R.id.muscle);
                bone = (EditText) inflator.findViewById(R.id.bone);
                alertDialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!(TextUtils.isEmpty(date.getText().toString().trim()))){
                            String datetxt = date.getText().toString().trim();
                            String weighttxt = weight.getText().toString().trim();
                            String fattxt = fat.getText().toString().trim();
                            String watertxt = water.getText().toString().trim();
                            String muscletxt = muscle.getText().toString().trim();
                            String bonetxt = bone.getText().toString().trim();
                            compositionList.add(new CompositionModel(datetxt,weighttxt,fattxt,watertxt,muscletxt,bonetxt));
                            compositionAdapter.notifyDataSetChanged();
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
