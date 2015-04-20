package jp.co.crowdworks.unwantedly.account;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class CWAuthService extends Service{
    private CWAuthenticator mAuthenticator;

    @Override
    public void onCreate(){
        mAuthenticator = new CWAuthenticator(this);
    }


    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        if(mAuthenticator!=null) {
            return mAuthenticator.getIBinder();
        }

        return null;
    }
}
