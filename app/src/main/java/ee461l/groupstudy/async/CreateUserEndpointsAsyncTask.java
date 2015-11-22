package ee461l.groupstudy.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import ee461l.groupstudyendpoints.groupstudyEndpoint.GroupstudyEndpoint;


/**
 * Created by britne on 4/11/15.
 */
public class CreateUserEndpointsAsyncTask extends AsyncTask<String, Void, Void> {
    private static GroupstudyEndpoint usersEndpointApi = null;
    private Context context;

    public CreateUserEndpointsAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... userInfo) {
        if(usersEndpointApi == null) {  // Only do this once
            GroupstudyEndpoint.Builder builder = new GroupstudyEndpoint.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("https://groupstudy-461l.appspot.com/_ah/api")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

            usersEndpointApi = builder.build();
        }

        //createUser(name, password)
        try {
            usersEndpointApi.createUser(userInfo[0], userInfo[1]).execute();
        } catch (IOException e) {
            Log.d("CreateUserAsync", "" + e.getMessage());
        }

        return null;
    }

}