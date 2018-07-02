package com.dietitianshreya.eatrightdietadmin;


import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;


public class TestAppointmentFragment extends Fragment{

    FrameLayout frameLayout;

    TextView appoitmentRow, bookingRow;
    LinearLayout linear;
    TableLayout SegmentTable;



    public static TestAppointmentFragment newInstance() {
        TestAppointmentFragment fragment = new TestAppointmentFragment();


        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.test_appointment_fragment, container, false);
        appoitmentRow = rootView.findViewById(R.id.seg_one);
                linear= rootView.findViewById(R.id.linear_container);
                SegmentTable = rootView.findViewById(R.id.segment_table);
         frameLayout = (FrameLayout) rootView.findViewById(R.id.frame_appointments);
        frameLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1));
        linear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        SegmentTable.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,6));


        bookingRow = rootView.findViewById(R.id.seg_two);

        Fragment fragment = new AppointmentFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        transaction.add(R.id.frame_appointments, fragment ).commit();

        appoitmentRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new AppointmentFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                if(getChildFragmentManager().findFragmentById(R.id.frame_appointments ) != null) {
                    transaction.remove(getChildFragmentManager().findFragmentById(R.id.frame_appointments));
                }
                transaction.add(R.id.frame_appointments, fragment ).commit();
            }
        });


        bookingRow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {





                Fragment fragment1 = new DietCreateFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
if(getChildFragmentManager().findFragmentById(R.id.frame_appointments ) != null) {
    transaction.remove(getChildFragmentManager().findFragmentById(R.id.frame_appointments));
}
                transaction.add(R.id.frame_appointments, fragment1 ).commit();



            }
        });

        return rootView;
    }

}
