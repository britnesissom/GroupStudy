package ee461l.groupstudyendpoints;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.OnLoad;
import com.googlecode.objectify.annotation.Serialize;
import com.googlecode.objectify.annotation.Subclass;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

@Entity
@Subclass
@Cache
public class Groups implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(Groups.class.getName());

    public static class Everything {}

    @Id
    String id;
    private String groupName;

    @Load(Everything.class)
    private transient ArrayList<Ref<FilesEntity>> files;
    @Ignore
    private ArrayList<FilesEntity> filesToReturn = new ArrayList<>();

    @Load(Everything.class)
    private transient ArrayList<Ref<User>> teammates;
    @Ignore
    private ArrayList<User> teammatesToReturn = new ArrayList<>();

    @Load
    private ArrayList<String> messages = new ArrayList<>();

    @Load
    private ArrayList<String> tasks = new ArrayList<>();

    //@Load
    private String adminUser;

    public Groups() {

    }

    public Groups(String groupName, String adminUser, ArrayList<User> teammates) {
        setId(groupName);
        this.groupName = groupName;
        setAdminUser(adminUser);
        this.files = new ArrayList<>();
        this.messages = new ArrayList<>();
        setTeammates(teammates);
        this.tasks = new ArrayList<>();
    }

    //@OnLoad
    private void deRefFiles() {
        if (files != null) {

            filesToReturn = new ArrayList<>();
            for (int i = 0; i < files.size(); i++) {
                LOGGER.info("File key for retrieval: " + files.get(i).getKey());
                while (!files.get(i).isLoaded()) {
                    LOGGER.info("file is loading");
                }
                if (files.get(i).isLoaded()) {
                    //LOGGER.info("group ref: " + listOfGroups.get(i).getValue());
                    filesToReturn.add(files.get(i).get());

                    if (files.get(i).get() == null) {
                        LOGGER.info("file is null");
                    } else {
                        //LOGGER.info("Group name with getValue: " + listOfGroups.get(i).getValue().getGroupName());
                        LOGGER.info("File safe get: " + files.get(i).get().getFileName());
                    }
                    //LOGGER.info("File name w/o getValue: " + files.get(i).get().getFileName());
                }

            }
        }
        //no files have been added
        else {
            filesToReturn = new ArrayList<>();
        }
        LOGGER.info("deref filesToReturn size: " + filesToReturn.size());
    }

    //@OnLoad
    private void deRefUsers() {
        if (teammates != null && teammates.size() != 0) {
            teammatesToReturn = new ArrayList<>();
            for (int i = 0; i < teammates.size(); i++) {
                //if (teammates.get(i).isLoaded()) {
                    //LOGGER.info("group ref: " + listOfGroups.get(i).getValue());
                    teammatesToReturn.add(teammates.get(i).get());
                    //LOGGER.info("Group name with getValue: " + listOfGroups.get(i).getValue().getGroupName());
                    LOGGER.info("username: " + teammates.get(i).get().getUsername());
                //}
            }
        }
        //no teammates have been added
        else {
            teammatesToReturn = new ArrayList<>();
        }
        LOGGER.info("deref groupsToReturn size: " + teammatesToReturn.size());
    }

    public ArrayList<FilesEntity> getFiles() {
        deRefFiles();

        LOGGER.info("filesToReturn size for " + groupName + ": " + filesToReturn.size());
        return filesToReturn;
    }

    public void setMessages(ArrayList<String> messages) {
        this.messages = messages;
    }

    public void setTasks(ArrayList<String> tasks) {
        this.tasks = new ArrayList<>();
        this.tasks.addAll(tasks);
    }

    public void addTask(String task) {
        if (this.tasks == null)
            this.tasks = new ArrayList<>();
        tasks.add(task);
        LOGGER.info("task added!");
    }

    public Groups addFile(FilesEntity file) {
        //Ref<FilesEntity> g = Ref.create(Key.create(FilesEntity.class, file.getId()));
        Ref<FilesEntity> g = Ref.create(file);
        LOGGER.info("File key: " + g.getKey());
        if (files == null)
            files = new ArrayList<>();
        LOGGER.info("list of ref<files> size: " + files.size());
        files.add(g);
        LOGGER.info("list of ref<files> size after add: " + files.size());
        LOGGER.info("file added!");
        return this;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setAdminUser(String adminUser) {
        //Ref<User> u = Ref.create(adminUser);
        this.adminUser = adminUser;
    }

    public String getGroupName() {
        return groupName;
    }

    public void changeAdminUser(String adminUser) {
        //Ref<User> u = Ref.create(adminUser);
        this.adminUser = adminUser;
    }

    public ArrayList<String> getMessages() { return messages ; }

    public ArrayList<String> getTasks() { return tasks;}

    public ArrayList<User> getTeammates() {
        deRefUsers();
        LOGGER.info("teammatesToReturn size for " + groupName + ": " + teammatesToReturn.size());
        return teammatesToReturn;
    }

    public void setTeammates(ArrayList<User> teammates) {
        teammates = new ArrayList<>();
        for (User u : teammates) {
            Ref<User> refToUser = Ref.create(u);
            this.teammates.add(refToUser);
        }
    }

    public String getAdminUser() {
        return adminUser;
    }
}
