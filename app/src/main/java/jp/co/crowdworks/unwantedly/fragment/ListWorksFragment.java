package jp.co.crowdworks.unwantedly.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import jp.co.crowdworks.unwantedly.R;


public class ListWorksFragment extends Fragment {
    public ListWorksFragment(){}

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_listworks, container, false);

        ListView list = (ListView) v.findViewById(R.id.listworks_list);

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

        return v;
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
