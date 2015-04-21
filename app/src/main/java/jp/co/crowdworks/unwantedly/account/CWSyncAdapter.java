package jp.co.crowdworks.unwantedly.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import hugo.weaving.DebugLog;

public class CWSyncAdapter extends AbstractThreadedSyncAdapter{
    private static final String TAG = CWSyncAdapter.class.getName();
    // Define a variable to contain a content resolver instance
    ContentResolver mContentResolver;

    AccountManager mAccountManager;
    /**
     * Set up the sync adapter
     */
    public CWSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver();
        mAccountManager = AccountManager.get(context);
    }

    @DebugLog
    @Override
    public void onPerformSync(Account account,
                              Bundle extras,
                              String authority,
                              ContentProviderClient provider,
                              SyncResult syncResult) {

        Log.d(TAG, "onPerformSync[account="+account+", extras="+extras+", authority="+authority+"]");

        String password = mAccountManager.getPassword(account);
        if(password==null){
            syncResult.stats.numAuthExceptions++;
            return;
        }

        ContentValues values = new ContentValues();
        values.put("id",123);
        values.put("name","cw123");
        try {
            provider.insert(Uri.parse("content://" + authority + "/people"), values);
        }
        catch(Exception e){}

    }
}
