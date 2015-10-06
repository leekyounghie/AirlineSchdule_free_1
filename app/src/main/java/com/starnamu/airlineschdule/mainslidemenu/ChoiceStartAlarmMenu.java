package com.starnamu.airlineschdule.mainslidemenu;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.starnamu.airlineschdule.MainActivity;
import com.starnamu.airlineschdule.parser.AirlineParser;
import com.starnamu.projcet.airlineschedule.R;

/**
 * Created by starnamu on 2015-07-30.
 */
public class ChoiceStartAlarmMenu extends LinearLayout {

    Button startUpdate;
    Context mContext;
    Handler handler;

    public ChoiceStartAlarmMenu(Context context) {
        super(context);
        init(context);
    }

    public ChoiceStartAlarmMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        handler = new Handler();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.mainslidemenu, this, true);

        startUpdate = (Button) view.findViewById(R.id.startUpdate);
        startUpdate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new AirlineParser();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ((MainActivity) mContext).onCloseDrawer();
                    }
                });

                startUpdate.setEnabled(false);
                Handler h = new Handler();
                h.postDelayed(new splashhandler(), 3000);//3초 지연
            }

            class splashhandler implements Runnable {
                public void run() {
                    startUpdate.setEnabled(true); // 클릭 유효화
                }
            }
        });
    }
}
