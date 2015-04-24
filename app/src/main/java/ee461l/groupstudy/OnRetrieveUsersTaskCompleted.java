package ee461l.groupstudy;

import java.util.List;

import ee461l.groupstudyendpoints.userEndpoint.model.User;

/**
 * Created by britne on 4/23/15.
 */
public interface OnRetrieveUsersTaskCompleted {
    public void onTaskCompleted(List<User> users);
}
