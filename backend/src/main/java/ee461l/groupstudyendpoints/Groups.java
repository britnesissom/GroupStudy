package ee461l.groupstudyendpoints;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.ArrayList;

@Entity
public class Groups {

    @Id
    Long id;
    private String groupname;
    private ArrayList files;
    private ArrayList<String> messages;
    private ArrayList<User> users;
    private ArrayList<String> tasks;
    private User adminUser;

    //might need userID to make it easier to find user?

    public Groups(String groupname, User adminUser) {
        this.groupname = groupname;
        this.adminUser = adminUser;
        this.files = new ArrayList();
        this.messages = new ArrayList<String>();
        this.users = new ArrayList<User>();
        this.tasks = new ArrayList<String>();
    }

    public long getId() { return id; }

//    public void setId(Long id) { this.id = id; }

    public String getGroupName() {
        return groupname;
    }

    public void setGroupname(String groupname) { this.groupname = groupname; }

    public void changeAdminUser(User adminUser) { this.adminUser = adminUser; }

    public ArrayList getFiles() {
        return files;
    }

    public ArrayList<String> getMessages() { return messages ; }

    public ArrayList<String> getTasks() { return tasks;}

    public ArrayList<User> getUsers() { return users; }
}
