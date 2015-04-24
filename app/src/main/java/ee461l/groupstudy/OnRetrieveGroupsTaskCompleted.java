package ee461l.groupstudy;

import java.util.List;

import ee461l.groupstudyendpoints.groupsEndpoint.model.Groups;

/**
 * Created by britne on 4/24/15.
 */
public interface OnRetrieveGroupsTaskCompleted {
    public void onRetrieveGroupsCompleted(List<Groups> groups);
}
