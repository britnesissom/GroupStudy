package ee461l.groupstudyendpoints;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.OnLoad;

import java.util.ArrayList;
import java.util.logging.Logger;

@Entity
public class Groups {

    private static final Logger LOGGER = Logger.getLogger(Groups.class.getName());

    public static class Everything {}

    @Id
    String id;
    private String groupName;

    @Load/*(Everything.class)*/
    private transient ArrayList<Ref<FilesEntity>> files = new ArrayList<>();
    @Ignore
    private ArrayList<FilesEntity> filesToReturn = new ArrayList<>();

    @Load/*(Everything.class)*/
    private transient ArrayList<Ref<User>> teammates = new ArrayList<>();
    @Ignore
    private ArrayList<User> teammatesToReturn = new ArrayList<>();

    private ArrayList<String> messages = new ArrayList<>();
    private ArrayList<String> tasks = new ArrayList<>();

    @Load
    private transient Ref<User> adminUser;

    public Groups() {

    }

    public Groups(String groupName, User adminUser, ArrayList<User> teammates) {
        setId(groupName);
        this.groupName = groupName;
        setAdminUser(adminUser);
        this.files = new ArrayList<>();
        this.messages = new ArrayList<>();
        setTeammates(teammates);
        this.tasks = new ArrayList<>();
    }

    @OnLoad
    private void deRefFiles() {
        if (files != null && files.size() != 0) {
            filesToReturn = new ArrayList<>();
            for (int i = 0; i < files.size(); i++) {
                if (files.get(i).isLoaded()) {
                    //LOGGER.info("group ref: " + listOfGroups.get(i).getValue());
                    filesToReturn.add(files.get(i).get());
                    //LOGGER.info("Group name with getValue: " + listOfGroups.get(i).getValue().getGroupName());
                    LOGGER.info("File name: " + files.get(i).get().getFileName());
                }
            }
        }
        //no files have been added
        else {
            filesToReturn = new ArrayList<>();
        }
        //LOGGER.info("deref groupsToReturn size: " + groupsToReturn.size());
    }

    @OnLoad
    private void deRefUsers() {
        if (teammates != null && teammates.size() != 0) {
            teammatesToReturn = new ArrayList<>();
            for (int i = 0; i < teammates.size(); i++) {
                if (teammates.get(i).isLoaded()) {
                    //LOGGER.info("group ref: " + listOfGroups.get(i).getValue());
                    teammatesToReturn.add(teammates.get(i).get());
                    //LOGGER.info("Group name with getValue: " + listOfGroups.get(i).getValue().getGroupName());
                    LOGGER.info("username: " + teammates.get(i).get().getUsername());
                }
            }
        }
        //no teammates have been added
        else {
            teammatesToReturn = new ArrayList<>();
        }
        //LOGGER.info("deref groupsToReturn size: " + groupsToReturn.size());
    }

    public ArrayList<FilesEntity> getFiles() {
        //deRefFiles();
        LOGGER.info("filesToReturn size for " + groupName + ": " + filesToReturn.size());
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
        Ref<User> u = Ref.create(adminUser);
        this.adminUser = u;
    }

    public String getGroupName() {
        return groupName;
    }

    public void changeAdminUser(User adminUser) {
        Ref<User> u = Ref.create(adminUser);
        this.adminUser = u;
    }

    public ArrayList<String> getMessages() { return messages ; }

    public ArrayList<String> getTasks() { return tasks;}

    public ArrayList<User> getTeammates() {
        //deRefUsers();
        return teammatesToReturn;
    }

    public void setTeammates(ArrayList<User> teammates) {
        for (User u : teammates) {
            Ref<User> refToUser = Ref.create(u);
            this.teammates.add(refToUser);
        }
    }

    public User getAdminUser() {
        return adminUser.get();
    }
}
