package jp.co.crowdworks.unwantedly.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import jp.co.crowdworks.unwantedly.R;
import jp.co.crowdworks.unwantedly.view.SlidingImageTabLayout;

public class Main2Activity extends ActionBarActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ViewPager viewPager = (ViewPager) findViewById(R.id.activity_main2_viewpager);
        MainPagerAdapter fragmentPagerAdapter = new MainPagerAdapter(
                getSupportFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);

        SlidingImageTabLayout slidingTab = (SlidingImageTabLayout) findViewById(R.id.activity_main2_slidingtab);
        slidingTab.setDistributeEvenly(true);
        slidingTab.setViewPager(viewPager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main2_toolbar);

    }
}
