package jp.co.crowdworks.unwantedly.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import hugo.weaving.DebugLog;
import jp.co.crowdworks.unwantedly.R;


public class ListWorksFragment extends Fragment {
    private static final String TAG = ListWorksFragment.class.getName();

    public ListWorksFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_listworks, container, false);

        initializeListView(v, inflater);
        initializeSwipeRefreshView(v, inflater);

        return v;
    }

    private void initializeSwipeRefreshView(View parent, LayoutInflater inflater) {
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) parent.findViewById(R.id.listworks_swipe_refresh);
        // 色設定
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        // Listenerをセット
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },5000);
            }
        });
    }

    private void initializeListView(View parent, final LayoutInflater inflater){
        ListView list = (ListView) parent.findViewById(R.id.listworks_list);

        ArrayList<WorkItem> items = new ArrayList<WorkItem>();

        Context context = getActivity();
        Resources res = context.getResources();
        for (int i=0; i<30; i++) {
            items.add(new WorkItem( "TITLE " + i, "description " + i, res.getDrawable(R.drawable.ic_work_dammy)));
        }
        items.get(0).full=true;
        items.get(0).icon = res.getDrawable(R.drawable.img_top_dammy);
        ArrayAdapter<WorkItem> adapter = new ArrayAdapter<WorkItem>(context, R.layout.listitem_work, R.id.listitem_work_title, items){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                WorkItem item = getItem(position);

                View itemView = inflater.inflate(
                        (item.full)?
                                R.layout.listitem_work_full : R.layout.listitem_work
                        , parent, false);

                ImageView icon = (ImageView) itemView.findViewById(R.id.listitem_work_icon);
                icon.setImageDrawable(item.icon);

                TextView title = (TextView) itemView.findViewById(R.id.listitem_work_title);
                TextView description = (TextView) itemView.findViewById(R.id.listitem_work_description);

                title.setText(item.title);
                description.setText(item.description);

                return itemView;
            }
        };
        list.setAdapter(adapter);
    }

    private static class WorkItem {
        boolean full;
        String title;
        String description;
        Drawable icon;
        public WorkItem(String title, String description, Drawable icon){
            this.title = title;
            this.description = description;
            this.icon = icon;
        }
    }

}
