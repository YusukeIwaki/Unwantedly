package jp.co.crowdworks.unwantedly.fragment.tab;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.ResourceCursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import jp.co.crowdworks.unwantedly.R;

public class ContentListFragment extends ListFragment {
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        getLoaderManager().initLoader(12345,null, mCursorLoader);
    }

    private class BookmarkTestItem{
        public String title;
        public String url;
    }

    private LoaderManager.LoaderCallbacks<Cursor> mCursorLoader = new LoaderManager.LoaderCallbacks<Cursor>(){

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            if(id==12345) {
                Uri uri = Browser.BOOKMARKS_URI;
                return new CursorLoader(getActivity(), uri, new String[]{
                        Browser.BookmarkColumns._ID
                        , Browser.BookmarkColumns.TITLE
                        , Browser.BookmarkColumns.URL
                }, null, null, null);
            }
            else return null;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            ResourceCursorAdapter adapter = new ResourceCursorAdapter(getActivity(), R.layout.listitem_bookmarktest, data,0) {
                @Override
                public void bindView(View view, Context context, Cursor cursor) {
                    final String title = cursor.getString(1);
                    final String url = cursor.getString(2);
                    TextView titleText = (TextView) view.findViewById(R.id.bookmarktest_title);
                    TextView urltext = (TextView) view.findViewById(R.id.bookmarktest_url);

                    //nullチェック（assert）をしたほうが安全
                    titleText.setText(title);
                    urltext.setText(url);
                }
            };
            setListAdapter(adapter);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            Toast.makeText(getActivity(),"あかーん",Toast.LENGTH_SHORT).show();
        }
    };
}
