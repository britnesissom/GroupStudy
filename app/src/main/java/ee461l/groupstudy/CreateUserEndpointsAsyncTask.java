package ee461l.groupstudy;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import ee461l.groupstudyendpoints.userEndpoint.UserEndpoint;
import ee461l.groupstudyendpoints.userEndpoint.model.User;


/**
 * Created by britne on 4/11/15.
 */
class CreateUserEndpointsAsyncTask extends AsyncTask<String, Void, Void> {
    private static UserEndpoint usersEndpointApi = null;
    private Context context;

    CreateUserEndpointsAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... userInfo) {
        if(usersEndpointApi == null) {  // Only do this once
            UserEndpoint.Builder builder = new UserEndpoint.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            usersEndpointApi = builder.build();
        }

        //createUser(name, password)
        try {
            usersEndpointApi.createUser(userInfo[0], userInfo[1]).execute();
        } catch (IOException e) {
            Log.i("UserEndpointsAsyncTask", "" + e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
    }
}