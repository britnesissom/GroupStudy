package ee461l.groupstudy;

import java.util.List;

import ee461l.groupstudyendpoints.groupstudyEndpoint.model.User;

/**
 * Created by britne on 4/23/15.
 * Listener for async task that loads all users from datastore
 * Sends the list of users to calling activity/fragment
 */
public interface OnRetrieveUsersTaskCompleted {
    public void onTaskCompleted(List<User> users);
}
