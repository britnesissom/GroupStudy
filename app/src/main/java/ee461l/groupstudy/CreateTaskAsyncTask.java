package ee461l.groupstudy;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.Contacts;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.ArrayList;

import ee461l.groupstudyendpoints.groupstudyEndpoint.GroupstudyEndpoint;
import ee461l.groupstudyendpoints.groupstudyEndpoint.model.GroupWrapperEntity;
import ee461l.groupstudyendpoints.groupstudyEndpoint.model.Groups;
import ee461l.groupstudyendpoints.groupstudyEndpoint.model.User;

/**
 * Created by RyanMcClure on 4/27/15.
 */
public class CreateTaskAsyncTask extends AsyncTask<String, Void, Void> { //first String is task

    private static final String TAG = "CreateTaskAsync";
    private static GroupstudyEndpoint groupEndpointApi = null;
    private Context context;
    private String groupName = null;

    CreateTaskAsyncTask(Context context, String groupName){
        this.context=context;
        this.groupName=groupName;
    }

    @Override
    protected Void doInBackground(String... params) {
        if(groupEndpointApi == null) {  // Only do this once
            GroupstudyEndpoint.Builder builder = new GroupstudyEndpoint.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("https://groupstudy-ee-461l.appspot.com/_ah/api")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            groupEndpointApi = builder.build();
        }

        try {
            groupEndpointApi.createTask(groupName, params[0]);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
