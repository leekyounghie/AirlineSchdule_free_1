package com.starnamu.airlineschdule.comm;

import android.util.Log;

import com.starnamu.airlineschdule.slidinglayout.AirlineItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by starnamu on 2015-08-26.
 */
public class CustomTimeControll {
    AirLineItems airLineItems;

    public CustomTimeControll() {
        airLineItems = AirLineItems.getNewInstance();
    }

    public int userChoiceTime(long customTime) {
        long time1 = System.currentTimeMillis();

        long time2 = time1 - customTime;
        Date date1 = new Date(time1);
        SimpleDateFormat CurTimeFormat1 = new SimpleDateFormat("HHmm");
        String strCurTime1 = CurTimeFormat1.format(date1);

        Log.i("strCurTime1", strCurTime1);

        Date date2 = new Date(time2);
        SimpleDateFormat CurTimeFormat2 = new SimpleDateFormat("HHmm");
        String strCurTime2 = CurTimeFormat2.format(date2);

        Log.i("strCurTime2", strCurTime2);

        if (time2 <= 0) {
            return Integer.parseInt(strCurTime1);
        }
        return Integer.parseInt(strCurTime2);
    }

    public int systemCurrentTime() {
        long time1 = System.currentTimeMillis();
        Date date1 = new Date(time1);
        SimpleDateFormat CurTimeFormat1 = new SimpleDateFormat("HHmm");
        String strCurTime1 = CurTimeFormat1.format(date1);

        return Integer.parseInt(strCurTime1);
    }

    public int onCustomCompare(ArrayList<AirlineItem> items) {
        int CurrentPostion = 0;
        int SystemCurrentTime = systemCurrentTime();
        String ScduleTime = null;

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getStriItem(5) != null) {
                ScduleTime = items.get(i).getStriItem(5);
                int time = Integer.parseInt(ScduleTime);
                if (time <= SystemCurrentTime) {
                    CurrentPostion++;

                }
            }
        }
        Log.i("CurrentPostion", Integer.toString(CurrentPostion));
        return CurrentPostion-5;
    }
}
