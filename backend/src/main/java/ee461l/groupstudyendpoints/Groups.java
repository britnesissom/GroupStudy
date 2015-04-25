package ee461l.groupstudyendpoints;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.ArrayList;

@Entity
public class Groups {

    @Id
    Long id;
    private String groupName;
    private ArrayList<String> fileUris;
    private ArrayList<String> messages;
    private ArrayList<User> users;
    private ArrayList<String> tasks;
    private String adminUser;

    //might need userID to make it easier to find user?

    public Groups(String groupName, String adminUser, ArrayList<User> teammates) {
        this.groupName = groupName;
        this.adminUser = adminUser;
        this.fileUris = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.users = teammates;
        this.tasks = new ArrayList<>();
    }

    public long getId() { return id; }

//    public void setId(Long id) { this.id = id; }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupname(String groupName) { this.groupName = groupName; }

    public void changeAdminUser(String adminUser) { this.adminUser = adminUser; }

    public ArrayList<String> getFileUris() {
        return fileUris;
    }

    public ArrayList<String> getMessages() { return messages ; }

    public ArrayList<String> getTasks() { return tasks;}

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public String getAdminUser() {
        return adminUser;
    }
}
