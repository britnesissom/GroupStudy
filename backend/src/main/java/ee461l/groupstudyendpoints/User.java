package ee461l.groupstudyendpoints;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;



/**
 * Created by britne on 4/1/15.
 */

@Entity
public class User {

    @Id
    Long id;
    private String username;
    private String password;
    private boolean adminUser;

    //might need userID to make it easier to find user?

    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
        this.adminUser = false;
    }

    public long getId() { return id; }

    public void setId(Long id) { this.id = id; }

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