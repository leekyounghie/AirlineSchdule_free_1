package com.starnamu.airlineschdule.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.starnamu.airlineschdule.comm.AirLineItems;
import com.starnamu.airlineschdule.comm.CommonConventions;
import com.starnamu.airlineschdule.comm.CustomTimeControll;
import com.starnamu.airlineschdule.slidinglayout.AirLineAdapter;
import com.starnamu.airlineschdule.slidinglayout.AirlineItem;
import com.starnamu.projcet.airlineschedule.R;

import java.util.ArrayList;

/**
 * Created by Edwin on 15/02/2015.
 */
public class DepartureAirLineFragment extends Fragment implements CommonConventions {

    public ListView DepartureAirlineListView;
    public AirLineAdapter airlineAdapter;
    ArrayList<AirlineItem> ItemList;
    ArrayList<AirlineItem> items;
    CustomTimeControll customTimeControll;
    int CustomPostion;

    public DepartureAirLineFragment() {

        AirLineItems airLineItems = AirLineItems.getNewInstance();
        this.items = airLineItems.getItems();
        customTimeControll = new CustomTimeControll();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.departureairlinefragment, container, false);
        DepartureAirlineListView = (ListView) v.findViewById(R.id.DepartureAirlineListView);
        ItemList = new ArrayList<>();
        airlineAdapter = new AirLineAdapter(getActivity());

        for (int i = 0; i < items.size(); i++) {
            AirlineItem item = items.get(i);
            if (adCheck(item.getStriItem(10))) {
                if (airlineCheck(item.getStriItem(0))) {
                    if (flightCheck(item.getStriItem(3))) {
                        ItemList.add(item);
                    }
                }
            }
        }
        airlineAdapter.setItemList(ItemList);
        DepartureAirlineListView.setAdapter(airlineAdapter);
        Log.i("ItemList.Size", Integer.toString(ItemList.size()));
        CustomPostion = customTimeControll.onCustomCompare(ItemList);
        DepartureAirlineListView.requestFocusFromTouch();
        DepartureAirlineListView.setSelection(CustomPostion);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        DepartureAirlineListView.requestFocusFromTouch();
        DepartureAirlineListView.setSelection(CustomPostion);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /*Arraylist의 지료를 원하는 형태로 걸러낸다.*/
    private boolean adCheck(String airline) {
        if (airline.equals("D")) {
            return true;
        }
        return false;
    }

    private boolean airlineCheck(String airline) {
        if (airline.equals("아시아나항공")) {
            return true;
        }
        return false;
    }
    private boolean flightCheck(String flight) {
        int flightNumber = Integer.parseInt(flight.substring(2, 5));

        if ((627<=flightNumber)&&(flightNumber<=699)) {
            return false;
        }
        return true;
    }
}