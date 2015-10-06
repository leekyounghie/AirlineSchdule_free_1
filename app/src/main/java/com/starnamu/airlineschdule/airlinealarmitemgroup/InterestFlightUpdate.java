package com.starnamu.airlineschdule.airlinealarmitemgroup;

import android.util.Log;

import com.starnamu.airlineschdule.database.AlarmDBControll;
import com.starnamu.airlineschdule.slidinglayout.AirlineItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by star_namu on 2015-09-17.
 */
public class InterestFlightUpdate {

    ArrayList<AirlineItem> Items;
    ArrayList<AirlineItem> AlarmItems;
    ArrayList<AirlineItem> NewAlarmItems;
    AlarmDBControll alarmDBControll;


    public InterestFlightUpdate(ArrayList<AirlineItem> items) {
        this.Items = items;
        alarmDBControll = new AlarmDBControll();
        AlarmItems = alarmDBControll.selectData(null);
        NewAlarmItems = new ArrayList<>();

        getChangedDB();
    }

    public void getChangedDB() {
        String str;
       /* for(int i=0; i<AlarmItems.size(); i++){
            str = AlarmItems.get(i).getStriItem(1);
            Log.i("Items",str);
        }*/

        for (int i = 0; i < Items.size(); i++) {
            for (int j = 0; j < AlarmItems.size(); j++) {
                if (Items.get(i).getStriItem(3).equals(AlarmItems.get(j).getStriItem(3))) {
                    NewAlarmItems.add(Items.get(i));
                }
            }
        }

        Collections.sort(NewAlarmItems, new Comparator<AirlineItem>() {
            public int compare(AirlineItem obj1, AirlineItem obj2) {
                // TODO Auto-generated method stub
                return obj1.getStriItem(5).compareToIgnoreCase(obj2.getStriItem(5));
            }
        });
        Log.i("InterestFlightUpdate", "AlarmDB가 업그레이드 되었습니다.");
        alarmDBControll.allRemoveData("AlarmTableName");
        alarmDBControll.setDataTable(NewAlarmItems);
    }
}

