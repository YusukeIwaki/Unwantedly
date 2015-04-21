package jp.co.crowdworks.unwantedly.activity;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bolts.Continuation;
import bolts.Task;
import hugo.weaving.DebugLog;
import jp.co.crowdworks.unwantedly.R;
import jp.co.crowdworks.unwantedly.account.AuthUtil;

public class AuthenticationActivity extends AccountAuthenticatorActivity {
    private static final String TAG=AuthenticationActivity.class.getName();

    private Account mAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(AuthUtil.hasAccount(this)){
            Toast.makeText(this,"Account already exists",Toast.LENGTH_SHORT).show();
            setAccountAuthenticatorResult(null);
            setResult(RESULT_CANCELED);
            finish();
            return;
        }

        Intent intent = getIntent();
        final AuthInfo info = new AuthInfo();

        if(intent!=null) {
            info.username = intent.getStringExtra("username");
            info.password = intent.getStringExtra("password");
        }
        login(info).onSuccess(new Continuation<String, Object>() {
            @Override
            public Object then(Task<String> task) throws Exception {
                Account newAccount = AuthUtil.newAccount(info.username);
                AccountManager am = AccountManager.get(AuthenticationActivity.this);
                if (am.addAccountExplicitly(newAccount, info.password, null)) {
                    ContentResolver.setSyncAutomatically(newAccount, AuthUtil.AUTHORITY, true);
                    ContentResolver.addPeriodicSync(newAccount, AuthUtil.AUTHORITY, new Bundle(), 60);//every minite.

                    final Intent intent = new Intent();
                    intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, info.username);
                    intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, AuthUtil.ACCOUNT_TYPE);
                    setAccountAuthenticatorResult(intent.getExtras());
                    setResult(RESULT_OK, intent);
                } else {
                    setAccountAuthenticatorResult(null);
                    setResult(RESULT_CANCELED);
                }
                finish();
                return null;
            }
        }).continueWith(new Continuation<Object, Object>() {
            @Override
            public Object then(Task<Object> task) throws Exception {
                if (task.isFaulted()) {
                    setContentView(R.layout.activity_authentication);
                    initializeView();
                }
                return null;
            }
        });
    }

    @DebugLog
    private void initializeView() {
        Button ok = (Button)findViewById(R.id.ok_button);
        ok.setOnClickListener(new Button.OnClickListener() {
            @DebugLog
            @Override
            public void onClick(View v) {
                final AuthInfo info = new AuthInfo();
                info.username = getTextById(R.id.username_edit);
                info.password = getTextById(R.id.password_edit);
                login(info).onSuccess(new Continuation<String, Object>() {
                    @Override
                    public Object then(Task<String> task) throws Exception {
                        String token = task.getResult();
                        if (token != null) {
                            Account newAccount = AuthUtil.newAccount(info.username);
                            AccountManager am = AccountManager.get(AuthenticationActivity.this);
                            if(am.addAccountExplicitly(newAccount, info.password, null)){
                                ContentResolver.setSyncAutomatically(newAccount, AuthUtil.AUTHORITY, true);
                                ContentResolver.addPeriodicSync(newAccount, AuthUtil.AUTHORITY, new Bundle(), 60);//every minite.

                                final Intent intent = new Intent();
                                intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, info.username);
                                intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, AuthUtil.ACCOUNT_TYPE);
                                setAccountAuthenticatorResult(intent.getExtras());
                                setResult(RESULT_OK, intent);
                            }
                            else{
                                setAccountAuthenticatorResult(null);
                                setResult(RESULT_CANCELED);
                            }
                            finish();
                        }
                        return null;
                    }
                }).continueWith(new Continuation<Object, Object>() {
                    @Override
                    public Object then(Task<Object> task) throws Exception {
                        if (task.isFaulted()) {
                            Toast.makeText(AuthenticationActivity.this, "AUTH error!", Toast.LENGTH_SHORT).show();
                        }
                        return null;
                    }
                });
            }
        });
    }

    private String getTextById(int id){
        EditText txt = (EditText)findViewById(id);
        return txt.getText().toString();
    }

    public static class AuthInfo{
        public String username;
        public String password;
    }

    public Task<String> login(final AuthInfo info){
        final Task<String>.TaskCompletionSource task = Task.<String> create();

        if(info==null || info.username==null || info.password==null){
            task.setError(new Exception("AuthError"));
            return task.getTask();
        }

        (new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                return AuthUtil.authenticate(info.username, info.password);
            }

            @Override
            protected void onPostExecute(final String authToken) {
                // On a successful authentication, call back into the Activity to
                // communicate the authToken (or null for an error).
                Log.d(TAG,"authToken="+authToken);
                if(authToken!=null) {
                    task.setResult(authToken);
                }
                else{
                    task.setError(new Exception("auth failure"));
                }
            }

            @Override
            protected void onCancelled() {
                // If the action was canceled (by the user clicking the cancel
                // button in the progress dialog), then call back into the
                // activity to let it know.
                task.setError(new Exception("canceled"));
            }
        }).execute();

        return task.getTask();
    }
}
