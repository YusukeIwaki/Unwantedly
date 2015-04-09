package jp.co.crowdworks.unwantedly.api;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import bolts.Task;

/**
 * Created by yi01 on 2015/04/09.
 */
public class FacebookApiManager {
    private static final String TAG = FacebookApiManager.class.getName();

    private FacebookApiManager(){}
    public static FacebookApiManager sInstance = new FacebookApiManager();
    public static FacebookApiManager getsInstance(){
        return sInstance;
    }

    private AccessTokenTracker mAccessTokenTracker;

    public FacebookApiManager init(Context context){
        FacebookSdk.sdkInitialize(context.getApplicationContext());
        return this;
    }

    public boolean isLoggedIn(){
        return isValidToken(AccessToken.getCurrentAccessToken());
    }

    /*TODO public Task<Boolean> isLoggedIn(){
        Log.d(TAG, "isLoggedIn>");
        final Task<Boolean>.TaskCompletionSource task = Task.<Boolean>create();
        AccessToken fbToken = AccessToken.getCurrentAccessToken();
        if(isValidToken(fbToken)) {
            Log.d(TAG, "isLoggedIn: result=true");
            task.setResult(true);
            return task.getTask();
        }

        if(mAccessTokenTracker == null) {
            mAccessTokenTracker = new AccessTokenTracker() {
                @Override
                protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                    if(task.getTask().isCompleted()) {
                        mAccessTokenTracker.stopTracking();
                        return;
                    }
                    if (isValidToken(newAccessToken)) {
                        Log.d(TAG, "isLoggedIn: result=true");
                        task.setResult(true);
                    } else {
                        Log.d(TAG, "isLoggedIn: result=false");
                        task.setResult(false);
                    }
                    mAccessTokenTracker.stopTracking();
                }
            };
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mAccessTokenTracker != null){
                    mAccessTokenTracker.startTracking();
                }
                task.setResult(false);
            }
        }, 5000);
        return task.getTask();
    }*/

    private boolean isValidToken(AccessToken fbToken){
        return (fbToken != null && !fbToken.isExpired());
    }

    public void logout(){
        LoginManager.getInstance().logOut();
    }
}
