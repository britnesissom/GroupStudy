package ee461l.groupstudy.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by britne on 11/24/15.
 */
@ParseClassName("Task")
public class Task extends ParseObject {

    public Task getTask(String objectId) {
        return (Task) getParseObject(objectId);
    }

    public String getDescription() {
        return getString("description");
    }

    public String getLocation() {
        return getString("location");
    }

    public String getDate() {
        return getString("date");
    }
}
