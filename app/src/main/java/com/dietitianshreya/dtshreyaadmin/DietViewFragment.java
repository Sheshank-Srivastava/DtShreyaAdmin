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
import android.widget.EditText;

import com.dietitianshreya.dtshreyaadmin.adapters.DietPlanAdapter;
import com.dietitianshreya.dtshreyaadmin.models.DietPlanModel;
import com.dietitianshreya.dtshreyaadmin.models.MealModel;

import java.util.ArrayList;


public class DietViewFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String clientId;
    private String date;
    RecyclerView recyclerView;
    EditText dietDate;
    ArrayList<DietPlanModel> dietList;
    ArrayList<MealModel> mealList;
    DietPlanAdapter dietPlanAdapter;

    private OnFragmentInteractionListener mListener;

    public DietViewFragment() {
        // Required empty public constructor
    }

    public static DietViewFragment newInstance(String param1, String param2) {
        DietViewFragment fragment = new DietViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param1);
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
        dietPlanAdapter = new DietPlanAdapter(dietList,getActivity());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(dietPlanAdapter);
        mealList.add(new MealModel("Corn Flakes","1 Bowl",false));
        mealList.add(new MealModel("Cow's Milk","1 Glass",false));
        dietList.add(new DietPlanModel("Breakfast",mealList));

        ArrayList<MealModel> mealList1 = new ArrayList<>();
        mealList1.add(new MealModel("Dal","1 bowl",false));
        mealList1.add(new MealModel("Roti","3 full",false));
        mealList1.add(new MealModel("Rice","Half bowl",false));
        dietList.add(new DietPlanModel("Lunch",mealList1));

        ArrayList<MealModel> mealList2 = new ArrayList<>();
        mealList2.add(new MealModel("Chips","1 packet",false));
        mealList2.add(new MealModel("Coke","300 ML",false));
        mealList2.add(new MealModel("Chocolate","20g",false));
        dietList.add(new DietPlanModel("Snack",mealList2));
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
