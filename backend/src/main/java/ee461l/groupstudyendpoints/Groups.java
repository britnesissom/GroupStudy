package ee461l.groupstudyendpoints;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.ArrayList;

@Entity
public class Groups {

    @Id
    String id;
    private String groupName;
    private ArrayList<String> files;
    private ArrayList<String> messages;
    private ArrayList<User> users;
    private ArrayList<String> tasks;
    private User adminUser;

    public Groups() {

    }

    public Groups(String groupName, User adminUser, ArrayList<User> teammates) {
        setId(groupName);
        this.groupName = groupName;
        this.adminUser = adminUser;
        this.files = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.users = teammates;
        this.tasks = new ArrayList<>();
    }

    public void setFiles(ArrayList<String> files) {
        this.files = files;
    }

    public void setMessages(ArrayList<String> messages) {
        this.messages = messages;
    }

    public void setTasks(ArrayList<String> tasks) {
        this.tasks = tasks;
    }

    public void addTask(String task) {
        tasks.add(task);
    }

    public void addFile(String file) {
        files.add(file);
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupname(String groupName) { this.groupName = groupName; }

    public void changeAdminUser(User adminUser) { this.adminUser = adminUser; }

    public ArrayList<String> getFiles() {
        return files;
    }

    public ArrayList<String> getMessages() { return messages ; }

    public ArrayList<String> getTasks() { return tasks;}

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public User getAdminUser() {
        return adminUser;
    }
}
