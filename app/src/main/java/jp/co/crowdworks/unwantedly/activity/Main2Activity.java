package jp.co.crowdworks.unwantedly.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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


        initializePane();
    }

    private void initializePane(){
        final SlidingPaneLayout pane = (SlidingPaneLayout) findViewById(R.id.activity_main2_sliding_pane);

        ListView menuList = (ListView) findViewById(R.id.activity_main2_slide_menu);
        menuList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[] {"hoge", "fuga", "piyo"}));
        menuList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(pane.isSlideable() && pane.isOpen()) {
                    pane.closePane();
                }
            }
        });
    }
}
