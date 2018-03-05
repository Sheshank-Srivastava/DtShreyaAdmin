package com.dietitianshreya.dtshreyaadmin;

/**
 * Created by hp on 2/15/2018.
 */


import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerDietViewAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> dayList = new ArrayList<>();
    private final List<String> dateList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    Context mCtx;

    public ViewPagerDietViewAdapter(FragmentManager manager, Context mCtx) {
        super(manager);
        this.mCtx = mCtx;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    public void addTitles(String day,String date){
        dayList.add(day);
        dateList.add(date);
    }
    public View getTabView(int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
        View tabView = layoutInflater.inflate(R.layout.custom_tab, null, false);
        TextView text1 = (TextView) tabView.findViewById(R.id.text1);
        TextView text2 = (TextView) tabView.findViewById(R.id.text2);
        text1.setText(dayList.get(position));
        text2.setText(dateList.get(position));
        return tabView;
    }
    public View getSelectedTabView(int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
        View tabView = layoutInflater.inflate(R.layout.custom_tab, null, false);
        TextView text1 = (TextView) tabView.findViewById(R.id.text1);
        TextView text2 = (TextView) tabView.findViewById(R.id.text2);
        text1.setBackgroundColor(Color.CYAN);
        text1.setText(mFragmentTitleList.get(position));
        text2.setText(mFragmentTitleList.get(position));
        return tabView;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public int getItemPosition(Object object){
        return super.getItemPosition(object);

    }


}
