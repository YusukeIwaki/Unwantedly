package jp.co.crowdworks.unwantedly;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import jp.co.crowdworks.unwantedly.fragment.ListWorksFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {

    public MainPagerAdapter(FragmentManager m){
        super(m);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0: return new ListWorksFragment();
            case 1: return new ListWorksFragment();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "WORKS";
            case 1: return "???";
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
