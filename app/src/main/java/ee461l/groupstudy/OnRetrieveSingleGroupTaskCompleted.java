package ee461l.groupstudy;

import ee461l.groupstudyendpoints.groupstudyEndpoint.model.Groups;

/**
 * Created by britne on 4/25/15.
 * Listener for retrieving a group
 * Will send the result to the activity/fragment that called it
 */
public interface OnRetrieveSingleGroupTaskCompleted {
    public void onRetrieveSingleGroupCompleted(Groups group);
}
