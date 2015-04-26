package ee461l.groupstudyendpoints;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.ArrayList;


/**
 * Created by britne on 4/1/15.
 */

@Entity
public class User {

    @Id
    String id;
    private String username;
    private String password;
    private boolean adminUser;
    private ArrayList<Groups> listOfGroups;

    public User() {

    }

    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
        setId(username);
        listOfGroups = new ArrayList<>();
        this.adminUser = false;
    }

    public void addGroup(Groups group) {
        listOfGroups.add(group);
    }

    public ArrayList<Groups> getListOfGroups() {
        return listOfGroups;
    }

    public void setListOfGroups(ArrayList<Groups> listOfGroups) {
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
