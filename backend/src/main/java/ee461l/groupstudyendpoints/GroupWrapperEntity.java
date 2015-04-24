package ee461l.groupstudyendpoints;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.ArrayList;

@Entity
public class GroupWrapperEntity {

    @Id
    Long id;
    private String groupname;
    private ArrayList<String> fileUris;
    private ArrayList<String> messages;
    private ArrayList<String> users;
    private ArrayList<String> tasks;
    private String adminUser;

    public GroupWrapperEntity() {

    }

    public GroupWrapperEntity(String groupname, String adminUser, ArrayList<String> teammates,
                              ArrayList<String> messages, ArrayList<String> tasks, ArrayList<String> fileUris) {
        this.groupname = groupname;
        this.adminUser = adminUser;
        this.fileUris = fileUris;
        this.messages = messages;
        this.users = teammates;
        this.tasks = tasks;
    }

    public void setFileUris(ArrayList<String> fileUris) {
        this.fileUris = fileUris;
    }

    public ArrayList<String> getFileUris() {
        return fileUris;
    }

    public long getId() { return id; }

//    public void setId(Long id) { this.id = id; }

    public String getGroupName() {
        return groupname;
    }

    public void setGroupName(String groupname) { this.groupname = groupname; }

    public void changeAdminUser(String adminUser) { this.adminUser = adminUser; }

    public void setMessages(ArrayList<String> messages) {
        this.messages = messages;
    }
    public ArrayList<String> getMessages() { return messages ; }

    public void setTasks(ArrayList<String> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<String> getTasks() { return tasks;}

    public ArrayList<String> getUsers() { return users; }

    public void setUsers(ArrayList<String> teammates) {
        this.users = teammates;
    }

    public void setAdminUser(String adminUser) {
        this.adminUser = adminUser;
    }

    public String getAdminUser() {
        return adminUser;
    }
}