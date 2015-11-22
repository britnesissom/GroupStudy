package ee461l.groupstudy;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by britne on 11/20/15.
 */
public class AppStartUp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, BuildConfig.PARSE_APP_ID, BuildConfig.PARSE_CLIENT_ID);

    }
}
