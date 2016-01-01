package ee461l.groupstudy.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by britne on 11/22/15.
 */
@ParseClassName("Group")
public class Group extends ParseObject {

    public Group() { }

    public ArrayList<Message> getMessages() {
        ArrayList<Message> messages = new ArrayList<>();
        List<Object> list = this.getList("messages");
        messages.clear();
        for (Object obj : list) {
            messages.add((Message) obj);
        }
        return messages;
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        List<Object> list = this.getList("tasks");
        tasks.clear();
        for (Object obj : list) {
            tasks.add((Task) obj);
        }
        return tasks;
    }

    public String getAdminUser() {
        return getString("admin");
    }

    public String getGroupName() {
        return getString("name");
    }

    public ArrayList<ParseFile> getFiles() {
        ArrayList<ParseFile> files = new ArrayList<>();
        List<Object> list = this.getList("files");
        files.clear();
        for (Object obj : list) {
            files.add((ParseFile) obj);
        }
        return files;
    }

    public ArrayList<String> getTeammates() {
        ArrayList<String> teammates = new ArrayList<>();
        List<Object> list = this.getList("members");
        teammates.clear();
        for (Object obj : list) {
            teammates.add((String) obj);
        }
        return teammates;
    }
}
