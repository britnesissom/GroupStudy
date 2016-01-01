package ee461l.groupstudy.async;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Allows the admin user to add a member to the group
 */
public class AddMemberAsyncTask extends AsyncTask<String, Void, Void> {

    private static final String TAG = "AddMemberAsync";
    private Context context;
    private String groupName = null;

    public AddMemberAsyncTask(Context context, String groupName){
        this.context=context;
        this.groupName=groupName;
    }

    @Override
    protected Void doInBackground(String... member) {
        //build the endpoint to access its methods
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
            groupEndpointApi.addMember(groupName, member[0]).execute();
            Log.d(TAG, "group member added");
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        return null;
    }
}
