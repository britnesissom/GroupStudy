package ee461l.groupstudyendpoints;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.ArrayList;

@Entity
public class GroupWrapperEntity {

    @Id
    Long id;
    private Groups group;


    public GroupWrapperEntity() {

    }

    public Groups getGroup() {
        return group;
    }

    public void setGroup(String groupName, String adminUser, ArrayList<String> teammates) {
        group = new Groups(groupName, adminUser, teammates);
    }

}