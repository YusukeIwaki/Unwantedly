package jp.co.crowdworks.unwantedly.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import jp.co.crowdworks.unwantedly.fragment.MainPagerAdapter;
import jp.co.crowdworks.unwantedly.R;

public class SlidingImageTabLayout extends SlidingTabLayout{

    public SlidingImageTabLayout(Context context){
        this(context, null);
    }

    public SlidingImageTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingImageTabLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setCustomTabView(R.layout.tab_title);
    }

    @Override
    protected void populateTabItemView(PagerAdapter adapter, View tabView, int position) {
        TextView txt = (TextView) tabView.findViewById(R.id.tab_title_text);
        ImageView icon = (ImageView) tabView.findViewById(R.id.tab_title_icon);
        Resources res = getContext().getResources();

        if(adapter instanceof MainPagerAdapter) {
            icon.setVisibility(View.VISIBLE);
            icon.setImageDrawable(((MainPagerAdapter) adapter).getIcon(res, position));
        }
        else {
            icon.setVisibility(View.GONE);
        }

        txt.setText(adapter.getPageTitle(position));
        txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, TAB_VIEW_TEXT_SIZE_SP);
        txt.setTypeface(Typeface.DEFAULT_BOLD);

    }
}
