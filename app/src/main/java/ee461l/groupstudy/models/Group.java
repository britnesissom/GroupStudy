package ee461l.groupstudy.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by britne on 11/22/15.
 */
@ParseClassName("Group")
public class Group extends ParseObject {

    private String groupName;
    private ArrayList<ParseFile> files;
    private ArrayList<ParseUser> teammates;
    private ArrayList<ParseObject> messages;    //change to string with objectId??
    private ArrayList<ParseObject> tasks;
    private ParseUser adminUser;

    public Group() { }

    public ArrayList<ParseObject> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<ParseObject> messages) {
        this.messages = messages;
    }

    public ArrayList<ParseObject> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<ParseObject> tasks) {
        this.tasks = tasks;
    }

    public ParseUser getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(ParseUser adminUser) {
        this.adminUser = adminUser;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public ArrayList<ParseFile> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<ParseFile> files) {
        this.files = files;
    }

    public void addFile(ParseFile file) {
        files.add(file);
    }

    public ArrayList<ParseUser> getTeammates() {
        return teammates;
    }

    public void setTeammates(ArrayList<ParseUser> teammates) {
        this.teammates = teammates;
    }
}
