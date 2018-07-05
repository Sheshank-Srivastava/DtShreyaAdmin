package com.dietitianshreya.eatrightdietadmin;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.dietitianshreya.eatrightdietadmin.adapters.DietPlanAdapter;
import com.dietitianshreya.eatrightdietadmin.models.DietPlanModel;
import com.dietitianshreya.eatrightdietadmin.models.MealModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class DietHistoryFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    String formattedDate;
    private String clientId;
    DatePicker datePicker;
    Button button;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    EditText dietDate;
    ArrayList<DietPlanModel> dietList;
    ArrayList<MealModel> mealList;
    DietPlanAdapter dietPlanAdapter;
    TextView dateSelection;
    int month,date,year;
    Intent intent;


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

            setHasOptionsMenu(true);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_diet_history, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.re);
        dateSelection = (TextView) rootView.findViewById(R.id.dateSelection);
        dietList=new ArrayList<>();
        mealList = new ArrayList<>();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        formattedDate = df.format(c);
        dietPlanAdapter = new DietPlanAdapter(dietList,getActivity());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(dietPlanAdapter);
        dateSelection.setText(formattedDate);

        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),CreateDiet.class);
                i.putExtra("clientId",clientId);
                startActivity(i);
            }
        });
        dateSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(),ViewDietActivity.class);
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                month = monthOfYear;
                                date = dayOfMonth;
                                String Date = dayOfMonth+"/";
                                Date += (monthOfYear+1)+"/";
                                Date += year;
                                intent.putExtra("date",Date);
                                intent.putExtra("clientId",clientId);
                                intent.putExtra("day",date+"");
                                intent.putExtra("month",month+"");
                                intent.putExtra("year",year+"");
                                startActivity(intent);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String Date = "";
//                Date += datePicker.getDayOfMonth()+"/";
//                Date += (datePicker.getMonth()+1)+"/";
//                Date += datePicker.getYear();
//
//                Intent i = new Intent(getActivity(),ViewDietActivity.class);
//                i.putExtra("date",Date);
//                i.putExtra("day",datePicker.getDayOfMonth()+"");
//                i.putExtra("month",datePicker.getMonth()+"");
//                i.putExtra("year",datePicker.getYear()+"");
//                i.putExtra("clientId",clientId);
//                getActivity().startActivity(i);
//            }
//        });
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

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.diet_history_menu,menu);
//    }
}
