package jp.co.crowdworks.unwantedly.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import jp.co.crowdworks.unwantedly.R;
import jp.co.crowdworks.unwantedly.view.SlidingImageTabLayout;

public class Main2Activity extends ActionBarActivity {

    ActionBarDrawerToggle mDrawerToggle;

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

        initializeToolbar();

        initializeDrawer();
    }

    private void initializeToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main2_toolbar);
        setSupportActionBar(toolbar);
    }

    private void initializeDrawer() {
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main2_drawer);
        final ListView menuList = (ListView) findViewById(R.id.activity_main2_slide_menu);
        menuList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{"hoge", "fuga", "piyo"}));
        menuList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawer.closeDrawer(menuList);
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, R.string.app_name, R.string.app_name){
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawer.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration config){
        super.onConfigurationChanged(config);
        mDrawerToggle.onConfigurationChanged(config);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}
