package ee461l.groupstudyendpoints;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.OnLoad;

import java.util.ArrayList;
import java.util.logging.Logger;


/**
 * Created by britne on 4/1/15.
 */

@Entity
public class User {

    private static final Logger LOGGER = Logger.getLogger(User.class.getName());

    @Id
    String id;
    private String username;
    private String password;
    private boolean adminUser;

    //why does adding this break objectify? something to do with entities containing
    //each other I assume
    @Load
    private transient ArrayList<Ref<Groups>> listOfGroups;

    @Ignore
    private ArrayList<Groups> groupsToReturn;

    public User() {

    }

    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
        setId(username);
        listOfGroups = new ArrayList<>();
        groupsToReturn = new ArrayList<>();
        this.adminUser = false;
    }

    @OnLoad
    public void deRef() {
        if (listOfGroups != null) {
            for (Ref<Groups> group : listOfGroups) {
                if (group.isLoaded()) {
                    groupsToReturn.add(group.get());
                }
            }
        }
    }

    public void addGroup(Groups group) {
        Ref<Groups> g = Ref.create(Key.create(Groups.class, group.getId()));
        //Ref<Groups> g = Ref.create(group);
        LOGGER.info("Key: " + g.get().getGroupName());
        LOGGER.info("listOfGroups size: " + listOfGroups.size());
        listOfGroups.add(g);
        LOGGER.info("group added!");
    }

    public ArrayList<Groups> getListOfGroups() {
        return groupsToReturn;
    }

    public void setListOfGroups(ArrayList<Ref<Groups>> listOfGroups) {
        this.listOfGroups = listOfGroups;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) { this.password = password; }

    public boolean isAdminUser() { return this.adminUser; }

    public void setAdminUser(boolean adminUser) { this.adminUser = adminUser; }

    public void setUserToAdmin() {
        setAdminUser(true);
    }
}
