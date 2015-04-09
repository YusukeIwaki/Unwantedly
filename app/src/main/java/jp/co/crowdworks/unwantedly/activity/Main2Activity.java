package jp.co.crowdworks.unwantedly.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import jp.co.crowdworks.unwantedly.api.FacebookApiManager;
import jp.co.crowdworks.unwantedly.fragment.LoginFragment;
import jp.co.crowdworks.unwantedly.fragment.MainFragment;

public class Main2Activity extends ActionBarActivity {
    private static final String TAG = Main2Activity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FacebookApiManager facebook = FacebookApiManager.getsInstance().init(this);
        if (facebook.isLoggedIn()){
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, new MainFragment())
                    .commit();
            return;
        }

        // need to login!
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, new LoginFragment())
                .commit();

        /*TODO facebook.isLoggedIn().onSuccessTask(new Continuation<Boolean, Task<Boolean>>() {
            @Override
            public Task<Boolean> then(Task<Boolean> task) throws Exception {
                Log.d(TAG, "isLoggedIn: result=" + task.getResult());
                if(task.getResult()){
                    getSupportFragmentManager().beginTransaction()
                            .add(android.R.id.content, new MainFragment())
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
                        .add(android.R.id.content, new SSOFragment())
                        .commit();
                return null;
            }
        });*/

    }

}
