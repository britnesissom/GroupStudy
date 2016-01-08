package ee461l.groupstudy;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import ee461l.groupstudy.models.Group;
import ee461l.groupstudy.models.Message;
import ee461l.groupstudy.models.Task;

/**
 * Created by britne on 11/20/15.
 */
public class AppStartUp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Group.class);
        ParseObject.registerSubclass(Message.class);
        ParseObject.registerSubclass(Task.class);
        Parse.initialize(this, BuildConfig.PARSE_APP_ID, BuildConfig.PARSE_CLIENT_ID);
    }
}
