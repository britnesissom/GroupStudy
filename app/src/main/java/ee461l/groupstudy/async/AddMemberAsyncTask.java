package ee461l.groupstudy.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import ee461l.groupstudy.models.Group;

/**
 * Allows the admin user to add a member to the group
 */
public class AddMemberAsyncTask extends AsyncTask<String, Void, Void> {

    private static final String TAG = "AddMemberAsync";
    private Context context;
    private Group group;

    public AddMemberAsyncTask(Context context, Group group){
        this.context = context;
        this.group = group;
    }

    @Override
    protected Void doInBackground(String... member) {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", member);

        try {
            ParseUser user = query.getFirst();
            group.add("members", user.getObjectId());
            group.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    Log.d(TAG, "member removed from group");
                }
            });
        }
        catch (ParseException e) {
            Log.d(TAG, e.getMessage());
        }

        return null;
    }
}
