package jp.co.crowdworks.unwantedly.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import jp.co.crowdworks.unwantedly.R;
import jp.co.crowdworks.unwantedly.api.FacebookApiManager;
import jp.co.crowdworks.unwantedly.fragment.LoginFragment;
import jp.co.crowdworks.unwantedly.fragment.MainFragment;
import jp.co.crowdworks.unwantedly.fragment.OnBackPressedListener;

public class Main2Activity extends ActionBarActivity {
    private static final String TAG = Main2Activity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Check if Fragment is already created.
        final FragmentManager fm = getSupportFragmentManager();
        if(fm != null && fm.findFragmentById(R.id.content)!=null) {
            Log.d(TAG, "already exists. NO Op.");
            return;
        }

        //FragmentManager.enableDebugLogging(true);

        FacebookApiManager facebook = FacebookApiManager.getsInstance().init(this);
        if (facebook.isLoggedIn()){
            fm.beginTransaction()
                    .add(android.R.id.content, new MainFragment())
                    .commit();
            return;
        }

        // need to login!
        fm.beginTransaction()
                .add(R.id.content, new LoginFragment())
                .commit();

        /*TODO facebook.isLoggedIn().onSuccessTask(new Continuation<Boolean, Task<Boolean>>() {
            @Override
            public Task<Boolean> then(Task<Boolean> task) throws Exception {
                Log.d(TAG, "isLoggedIn: result=" + task.getResult());
                if(task.getResult()){
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.content, new MainFragment())
                            .commit();
                    return null;
                }
                return task;
            }
        }).continueWithTask(new Continuation<Boolean, Task<Boolean>>() {
            @Override
            public Task<Boolean> then(Task<Boolean> task) throws Exception {
                Log.d(TAG, "isLoggedIn: result=" + task.getResult());
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.content, new SSOFragment())
                        .commit();
                return null;
            }
        });*/

    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getSupportFragmentManager();
        boolean handled = false;

        if(fm != null) {
            Fragment f = fm.findFragmentById(R.id.content);
            if(f instanceof OnBackPressedListener) {
                handled = ((OnBackPressedListener) f).onBackPressed();
            }
        }
        if(handled) return;

        super.onBackPressed();
    }
}
