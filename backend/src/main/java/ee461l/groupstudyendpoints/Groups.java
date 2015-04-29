package ee461l.groupstudyendpoints;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Load;

import java.util.ArrayList;
import java.util.logging.Logger;

@Entity
public class Groups {

    private static final Logger LOGGER = Logger.getLogger(Groups.class.getName());

    @Id
    String id;
    private String groupName;

    @Load
    private transient ArrayList<Ref<FilesEntity>> files = new ArrayList<>();

    @Ignore
    private ArrayList<FilesEntity> filesToReturn = new ArrayList<>();

    private ArrayList<String> messages = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<String> tasks = new ArrayList<>();
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

    public void deRef() {
        if (files != null) {
            filesToReturn = new ArrayList<>();
            for (int i = 0; i < files.size(); i++) {
                if (files.get(i).isLoaded()) {
                    //LOGGER.info("group ref: " + listOfGroups.get(i).getValue());
                    filesToReturn.add(files.get(i).get());
                    //LOGGER.info("Group name with getValue: " + listOfGroups.get(i).getValue().getGroupName());
                    LOGGER.info("File name w/o getValue: " + files.get(i).get().getFileName());
                }
            }
        }
        //no files have been added
        else {
            filesToReturn = new ArrayList<>();
        }
        //LOGGER.info("deref groupsToReturn size: " + groupsToReturn.size());
    }

    public void setFiles(ArrayList<Ref<FilesEntity>> files) {
        this.files = files;
    }

    public ArrayList<FilesEntity> getFiles() {
        deRef();
        LOGGER.info("filesToReturn size: " + filesToReturn.size());
        return filesToReturn;
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

    public void addFile(FilesEntity file) {
        Ref<FilesEntity> g = Ref.create(file);
        //LOGGER.info("Key: " + g.safe().getFileName());
        LOGGER.info("list of ref<files> size: " + files.size());
        files.add(g);
        LOGGER.info("file added!");
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setAdminUser(User adminUser) {
        this.adminUser = adminUser;
    }

    public String getGroupName() {
        return groupName;
    }

    public void changeAdminUser(User adminUser) { this.adminUser = adminUser; }

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
