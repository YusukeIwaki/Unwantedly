package jp.co.crowdworks.unwantedly.account;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import hugo.weaving.DebugLog;

/**
 * Created by yi01 on 2015/04/20.
 */
public class CWProvider extends ContentProvider {
    @Override
    public boolean onCreate() {
        return true;
    }

    @DebugLog
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @DebugLog
    @Override
    public String getType(Uri uri) {
        return "text/plain";
    }

    @DebugLog
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @DebugLog
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @DebugLog
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
