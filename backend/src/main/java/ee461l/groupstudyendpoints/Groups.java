package ee461l.groupstudyendpoints;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.ArrayList;

@Entity
public class Groups {

    @Id
    Long id;
    private String groupname;
    private ArrayList<String> fileUris;
    private ArrayList<String> messages;
    private ArrayList<User> users;
    private ArrayList<String> tasks;
    private User adminUser;

    //might need userID to make it easier to find user?

    public Groups(String groupname, User adminUser, ArrayList<User> teammates, ArrayList<String> messages,
                  ArrayList<String> tasks, ArrayList<String> fileUris) {
        this.groupname = groupname;
        this.adminUser = adminUser;
        this.fileUris = fileUris;
        this.messages = messages;
        this.users = teammates;
        this.tasks = tasks;
    }

    public long getId() { return id; }

//    public void setId(Long id) { this.id = id; }

    public String getGroupName() {
        return groupname;
    }

    public void setGroupname(String groupname) { this.groupname = groupname; }

    public void changeAdminUser(User adminUser) { this.adminUser = adminUser; }

    public ArrayList<String> getFileUris() {
        return fileUris;
    }

    public ArrayList<String> getMessages() { return messages ; }

    public ArrayList<String> getTasks() { return tasks;}

    public ArrayList<User> getUsers() { return users; }

    public void setUsers(ArrayList<User> teammates) {
        this.users = teammates;
    }
}
