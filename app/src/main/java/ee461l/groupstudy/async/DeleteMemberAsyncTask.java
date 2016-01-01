package ee461l.groupstudy.async;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Brit'ne on 4/27/15.
 * Deletes member from a group
 */
public class DeleteMemberAsyncTask extends AsyncTask<String, Void, Void> { //first String is task

    private static final String TAG = "DeleteMemberAsync";
    private Context context;
    private String groupName = null;

    public DeleteMemberAsyncTask(Context context, String groupName){
        this.context=context;
        this.groupName=groupName;
    }

    @Override
    protected Void doInBackground(String... member) {
        /*if(groupEndpointApi == null) {  // Only do this once
            GroupstudyEndpoint.Builder builder = new GroupstudyEndpoint.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("https://groupstudy-461l.appspot.com/_ah/api")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

            groupEndpointApi = builder.build();
        }

        try {
            groupEndpointApi.removeMember(groupName, member[0]).execute();
            Log.d(TAG, "group member removed");
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        return null;
    }
}
