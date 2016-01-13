package ee461l.groupstudy.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Collections;

import ee461l.groupstudy.models.Group;

/**
 * Created by Brit'ne on 4/27/15.
 * Deletes member from a group
 */
public class DeleteMemberAsyncTask extends AsyncTask<String, Void, Void> { //first String is task

    private static final String TAG = "DeleteMemberAsync";
    private Context context;
    private Group group;

    public DeleteMemberAsyncTask(Context context, Group group){
        this.context = context;
        this.group = group;
    }

    @Override
    protected Void doInBackground(String... member) {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", member);

        try {
            ParseUser user = query.getFirst();
            group.removeAll("members", Collections.singletonList(user.getObjectId()));
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
