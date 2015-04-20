package jp.co.crowdworks.unwantedly.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Log;

import hugo.weaving.DebugLog;

/**
 * Created by yi01 on 2015/04/20.
 */
public class AuthUtil {
    private static final String TAG = AuthUtil.class.getName();
    public static final String ACCOUNT_TYPE = "jp.co.crowdworks.unwantedly";

    public static Account newAccount(String username) {
        Account newAccount = new Account(username, ACCOUNT_TYPE);
        return newAccount;
    }

    @DebugLog
    public static String authenticate(String username, String password){
        if(username==null || password==null){
            return null;
        }

        try{
            Thread.sleep(3000);

        } catch(Exception e){}

        Log.d(TAG, "info=" + username + " / " + password);
        if(username.equals("userA") && password.equals("test")) {
            return "hogeToken";
        }
        else return null;

    }

    public static boolean hasAccount(Context context) {
        final AccountManager am = AccountManager.get(context);
        Account[] accounts = am.getAccountsByType(AuthUtil.ACCOUNT_TYPE);
        return (accounts!=null && accounts.length>0);
    }

}
