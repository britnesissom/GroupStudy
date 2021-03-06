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
    private ArrayList<FilesEntity> filesToReturn;

    @Load
    private ArrayList<String> teammates;

    @Load
    private ArrayList<String> messages;

    @Load
    private ArrayList<String> tasks;

    //@Load
    private String adminUser;

    public Groups() {

    }

    public Groups(String groupName, String adminUser, ArrayList<String> teammates) {
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

    public void addMessage(String message) {
        if (this.messages == null)
            this.messages = new ArrayList<>();
        messages.add(message);
        LOGGER.info("message added!");
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

    public ArrayList<String> getMessages() { return messages; }

    public ArrayList<String> getTasks() { return tasks;}

    public ArrayList<String> getTeammates() {
        return teammates;
    }

    public void setTeammates(ArrayList<String> team) {
        teammates = new ArrayList<>();
        for (String member : team) {
            teammates.add(member);
        }

    }

    public String getAdminUser() {
        return adminUser;
    }

    public void removeMember(String member) {
        teammates.remove(member);
    }

    public void addMember(String member) {
        teammates.add(member);
    }
 }
