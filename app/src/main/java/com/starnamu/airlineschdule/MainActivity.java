package com.starnamu.airlineschdule;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.starnamu.airlineschdule.comm.CommonConventions;
import com.starnamu.airlineschdule.gcm.GcmModul;
import com.starnamu.airlineschdule.mainslidemenu.ChoiceStartAlarmMenu;
import com.starnamu.airlineschdule.parser.AirlineParser;
import com.starnamu.projcet.airlineschedule.R;

public class MainActivity extends ActionBarActivity implements CommonConventions {

    Toolbar toolbar;
    public ViewPager pager;
    public ViewPagerAdapter_1 adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"관심 Flight", "도착편", "출발편", "OAL 도착", "OAL 출발", "정보"};
    public DrawerLayout dlDrawer;
    ActionBarDrawerToggle dtToggle;
    private static Context mainContext;
    FrameLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainContext = this;
        new AirlineParser();
        new GcmModul(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        startMetrialView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public static Context getMainActivityContext() {
        return mainContext;
    }

    public void startMetrialView() {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        dlDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        dtToggle = new ActionBarDrawerToggle(this, dlDrawer, R.string.app_name, R.string.hello_world);
        dlDrawer.setDrawerListener(dtToggle);
        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new ViewPagerAdapter_1(getSupportFragmentManager(), Titles);
        pager.setAdapter(adapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {

            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });
        tabs.setViewPager(pager);
        ChoiceStartAlarmMenu choiceStartAlarmMenu = new ChoiceStartAlarmMenu(this);
        drawer = (FrameLayout) findViewById(R.id.drawer);
        drawer.addView(choiceStartAlarmMenu);
//        dlDrawer.closeDrawer(dtToggle);
        dlDrawer.closeDrawer(drawer);
    }

    public void onCloseDrawer() {
        dlDrawer.closeDrawer(drawer);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onStop() {
        super.onStop();
        /**프로세스 완전 종료 방법*/
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        dtToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        dtToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (dtToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}