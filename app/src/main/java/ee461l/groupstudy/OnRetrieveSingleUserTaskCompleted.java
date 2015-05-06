package ee461l.groupstudy;

import ee461l.groupstudyendpoints.groupstudyEndpoint.model.User;

/**
 * Created by britne on 4/25/15.
 * Listener for retrieving a user
 * Will send the result to the activity/fragment that called it
 */
public interface OnRetrieveSingleUserTaskCompleted {

    public void onRetrieveUserCompleted(User user);
}
