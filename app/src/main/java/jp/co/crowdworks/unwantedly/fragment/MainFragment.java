package jp.co.crowdworks.unwantedly.fragment;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.facebook.appevents.AppEventsLogger;

import hugo.weaving.DebugLog;
import jp.co.crowdworks.unwantedly.R;
import jp.co.crowdworks.unwantedly.api.FacebookApiManager;
import jp.co.crowdworks.unwantedly.view.SlidingImageTabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements OnBackPressedListener {
    private static final String TAG = MainFragment.class.getName();

    ActionBarDrawerToggle mDrawerToggle;

    public MainFragment() {
    }

    @DebugLog
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        ViewPager viewPager = (ViewPager) root.findViewById(R.id.fragment_main_viewpager);
        MainPagerAdapter fragmentPagerAdapter = new MainPagerAdapter(getFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);

        SlidingImageTabLayout slidingTab = (SlidingImageTabLayout) root.findViewById(R.id.fragment_main_slidingtab);
        slidingTab.setDistributeEvenly(true);
        slidingTab.setViewPager(viewPager);

        initializeToolbar(root);
        initializeDrawer(root);

        return root;
    }

    private void initializeToolbar(View root) {
        Toolbar toolbar = (Toolbar) root.findViewById(R.id.fragment_main_toolbar);
        ((ActionBarActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "toolbar clicked");
                final View root = v.getRootView();
                final DrawerLayout drawer = (DrawerLayout) root.findViewById(R.id.fragment_main_drawer);
                final ListView menuList = (ListView) root.findViewById(R.id.fragment_main_slide_menu);

                if (drawer==null || menuList==null) return;
                if(!drawer.isEnabled()
                //TODO: || !drawer.isActivated()
                        || drawer.isDrawerOpen(menuList)) return;

                drawer.openDrawer(menuList);
            }
        });
    }

    private void initializeDrawer(View root) {
        final DrawerLayout drawer = (DrawerLayout) root.findViewById(R.id.fragment_main_drawer);
        final ListView menuList = (ListView) root.findViewById(R.id.fragment_main_slide_menu);
        ActionBarActivity activity = getActionBarActivity();

        menuList.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, new String[]{"hoge", "fuga", "piyo"}));
        menuList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawer.closeDrawer(menuList);
                FacebookApiManager.getsInstance().logout();

                getFragmentManager().beginTransaction()
                        .replace(R.id.content, new LoginFragment())
                        .commit();
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(
                activity, drawer, R.string.app_name, R.string.app_name){
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawer.setDrawerListener(mDrawerToggle);

        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        //drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    private ActionBarActivity getActionBarActivity(){
        return (ActionBarActivity) getActivity();
    }


    @DebugLog
    @Override
    public void onResume(){
        super.onResume();
        AppEventsLogger.activateApp(getActivity(), getResources().getString(R.string.facebook_app_id));
    }

    @DebugLog
    @Override
    public void onPause(){
        super.onPause();
        AppEventsLogger.deactivateApp(getActivity(), getResources().getString(R.string.facebook_app_id));
    }

    @DebugLog
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        if(mDrawerToggle != null) mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration config){
        super.onConfigurationChanged(config);
        if(mDrawerToggle != null) mDrawerToggle.onConfigurationChanged(config);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle != null && mDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }


    @DebugLog
    @Override
    public boolean onBackPressed() {
        final ActionBarActivity root = getActionBarActivity();
        final DrawerLayout drawer = (DrawerLayout) root.findViewById(R.id.fragment_main_drawer);
        final ListView menuList = (ListView) root.findViewById(R.id.fragment_main_slide_menu);

        if (drawer==null || menuList==null) return false;
        if(!drawer.isEnabled()
                //TODO: || !drawer.isActivated()
                || !drawer.isDrawerOpen(menuList)) return false;

        drawer.closeDrawer(menuList);
        return true;
    }
}
