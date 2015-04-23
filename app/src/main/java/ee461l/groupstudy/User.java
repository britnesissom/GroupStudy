package ee461l.groupstudy;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/**
 * Created by britne on 4/1/15.
 */
public class User implements Parcelable {

    public ArrayList<Group> usersGroups;
    private String username;
    private String password;
    private boolean adminUser;
    //might need userID to make it easier to find user?

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.adminUser = false;
        usersGroups = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUserToAdmin() {
        this.adminUser = true;
    }

    public boolean isAdmin() {
        return this.adminUser;
    }

    public ArrayList<Group> getUsersGroups() {
        return this.usersGroups;
    }

    /*
    necessary methods to implement parcelable and be able to send User in an intent to
    another activity
     */

    protected User(Parcel in) {
        username = in.readString();
        password = in.readString();
        adminUser = in.readByte() == 0;     //adminUser == true if byte != 0

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(password);
        dest.writeByte((byte) (adminUser ? 1 : 0));     //if adminUser == true, byte == 1
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
