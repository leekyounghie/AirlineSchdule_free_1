package com.starnamu.airlineschdule;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.starnamu.airlineschdule.airlinealarmitemgroup.AlarmFragment;
import com.starnamu.airlineschdule.fragment.ArrivalAirlineFragment;
import com.starnamu.airlineschdule.fragment.DepartureAirLineFragment;
import com.starnamu.airlineschdule.fragment.FligthInfoFragment;
import com.starnamu.airlineschdule.fragment.OALArrivalAirlineFragment;
import com.starnamu.airlineschdule.fragment.OALDepartureAirLineFragment;
import com.starnamu.airlineschdule.slidinglayout.AirlineItem;

import java.util.ArrayList;

/**
 * Created by star_namu on 2015-10-05.
 */
public class ViewPagerAdapter_1 extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    ArrayList<AirlineItem> items;
    FragmentManager fm;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter_1(FragmentManager fm, CharSequence mTitles[]) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mTitles.length;
        this.fm = fm;
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                AlarmFragment alarmFragment = new AlarmFragment();
                fm.beginTransaction().add(alarmFragment, "alarm");
                return alarmFragment;

            case 1:
                ArrivalAirlineFragment arrivalAirlineFragment = new ArrivalAirlineFragment();
                fm.beginTransaction().add(arrivalAirlineFragment, "arr");
                Log.i("Arr", "Strar");
                return arrivalAirlineFragment;

            case 2:
                DepartureAirLineFragment departureAirLineFragment = new DepartureAirLineFragment();
                fm.beginTransaction().add(departureAirLineFragment, "dep");
                Log.i("dep", "Strar");
                return departureAirLineFragment;

            case 3:
                OALArrivalAirlineFragment oalArrivalAirlineFragment = new OALArrivalAirlineFragment();
                fm.beginTransaction().add(oalArrivalAirlineFragment, "oalarr");
                Log.i("oalarr", "Strar");
                return oalArrivalAirlineFragment;

            case 4:
                OALDepartureAirLineFragment oalDepartureAirLineFragment = new OALDepartureAirLineFragment();
                fm.beginTransaction().add(oalDepartureAirLineFragment, "oaldep");
                Log.i("oaldep", "Strar");
                return oalDepartureAirLineFragment;

            case 5:
                FligthInfoFragment fligthInfoFragment = new FligthInfoFragment();
                fm.beginTransaction().add(fligthInfoFragment, "info");
                return fligthInfoFragment;


        }
        return null;
    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(
            int position) {
        return Titles[position];
    }

    @Override
    public int getCount() {
        return NumbOfTabs;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public int getItemPosition(Object item) {
        return POSITION_NONE;
    }
}