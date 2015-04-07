package jp.co.crowdworks.unwantedly;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.activity_main_viewpager);
        MainPagerAdapter fragmentPagerAdapter = new MainPagerAdapter(
                getSupportFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);

        PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.activity_main_pagertabstrip);
        pagerTabStrip.setDrawFullUnderline(true);
    }
}
