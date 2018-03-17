package com.dietitianshreya.dtshreyaadmin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.dietitianshreya.dtshreyaadmin.adapters.DietCreateAdapter;
import com.dietitianshreya.dtshreyaadmin.adapters.DietPlanAdapter;
import com.dietitianshreya.dtshreyaadmin.models.DietPlanModel;
import com.dietitianshreya.dtshreyaadmin.models.MealModel;

import java.util.ArrayList;


public class DietCreateFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String clientId;
    private String date;
    RecyclerView recyclerView;
    EditText dietDate;
    ArrayList<DietPlanModel> dietList;
    ArrayList<MealModel> mealList;
    DietCreateAdapter dietPlanAdapter;


    private OnFragmentInteractionListener mListener;

    public DietCreateFragment() {
        // Required empty public constructor
    }

    public static DietCreateFragment newInstance(String param1, String param2) {
        DietCreateFragment fragment = new DietCreateFragment();
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
            clientId = getArguments().getString(ARG_PARAM1);
            date = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_diet_view, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.re);

        dietList=new ArrayList<>();
        mealList = new ArrayList<>();
        dietPlanAdapter = new DietCreateAdapter(dietList,getActivity());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(dietPlanAdapter);
        ArrayList<MealModel> mealListem = new ArrayList<>();
        dietList.add(new DietPlanModel("Early Morning",mealListem));
        dietList.add(new DietPlanModel("Breakfast",mealList));
        ArrayList<MealModel> mealListmm = new ArrayList<>();
        dietList.add(new DietPlanModel("Mid Morning",mealListmm));
        ArrayList<MealModel> mealList1 = new ArrayList<>();
        dietList.add(new DietPlanModel("Lunch",mealList1));
        ArrayList<MealModel> mealList2 = new ArrayList<>();
        dietList.add(new DietPlanModel("Evening",mealList2));
        ArrayList<MealModel> mealList3 = new ArrayList<>();
        dietList.add(new DietPlanModel("Dinner",mealList3));
        ArrayList<MealModel> mealListpd = new ArrayList<>();
        dietList.add(new DietPlanModel("Post Dinner",mealList1));
        ArrayList<MealModel> mealListsup = new ArrayList<>();
        dietList.add(new DietPlanModel("Supplements",mealListsup));
        dietPlanAdapter.notifyDataSetChanged();



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
    public void onActivityResult(int requestCode, int resultCode, Intent data){
//        super.onActivityResult(requestCode,resultCode,data);
        if(data != null) {
            Log.d("value", data.getStringArrayListExtra("name").size() + "");
            dietPlanAdapter.onActivityResult(requestCode, resultCode, data);
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
