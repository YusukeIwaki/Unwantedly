package jp.co.crowdworks.unwantedly.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import jp.co.crowdworks.unwantedly.R;
import jp.co.crowdworks.unwantedly.log.DebugLog;

public class LoginFragment extends Fragment {

    private static final String TAG = LoginFragment.class.getName();

    private CallbackManager mFBCallbackManager;

    public LoginFragment(){
    }

    @DebugLog
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        initializeFacebookButton(v, inflater);
        return v;
    }

    private void showMainPage() {
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new MainFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    private void initializeFacebookButton(View parent, LayoutInflater inflater) {
        final LoginButton loginButton = (LoginButton) parent.findViewById(R.id.facebook_login_button);
        loginButton.setReadPermissions("user_friends");
        loginButton.setFragment(this);

        mFBCallbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(mFBCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess");
                //AccessToken token = loginResult.getAccessToken();

                showMainPage();
            }

            @Override
            public void onCancel() {
                Log.w(TAG, "onCancel");
            }

            @Override
            public void onError(FacebookException e) {
                Log.e(TAG, "onError", e);
            }
        });
    }

    @DebugLog
    @Override
    public void onActivityResult(int req, int res, Intent data){
        super.onActivityResult(req, res, data);
        if (mFBCallbackManager != null) mFBCallbackManager.onActivityResult(req, res, data);
    }

    @DebugLog
    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }

}
