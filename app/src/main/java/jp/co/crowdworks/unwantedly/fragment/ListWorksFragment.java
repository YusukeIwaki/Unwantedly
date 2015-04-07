package jp.co.crowdworks.unwantedly.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import jp.co.crowdworks.unwantedly.R;


public class ListWorksFragment extends Fragment {
    public ListWorksFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_listworks, container, false);

        LinearLayout list = (LinearLayout) v.findViewById(R.id.listworks_list);

        for (int i=0; i<30; i++) {
            View item = createWorkItemView(inflater, "TITLE " + i, "description " + i);
            list.addView(item);
        }

        return v;
    }

    private View createWorkItemView(LayoutInflater inflater, String title, String description){
        View item = inflater.inflate(R.layout.listitem_work, null, false);
        TextView titleView = (TextView) item.findViewById(R.id.listitem_work_title);
        TextView descriptionView = (TextView) item.findViewById(R.id.listitem_work_description);

        titleView.setText(title);
        descriptionView.setText(description);
        return item;
    }
}
