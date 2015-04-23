package ee461l.groupstudy;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by britne on 4/1/15.
 */
public class UserList implements Parcelable {
    private HashMap<String,String> userHashMap;   //easy to find username/password combos
    private ArrayList<User> usersList;   //makes it easier to find single user

    public UserList() {
        userHashMap = new HashMap<>(0);
        usersList = new ArrayList<>(0);
    }

    public ArrayList<User> getUsers() {
        return usersList;
    }

    public void add(User user) {
        userHashMap.put(user.getUsername(), user.getPassword());
        usersList.add(user);
    }

    //makes sure the username/password combination is valid
    public boolean checkIfTableContains(String username, String password) {
        return userHashMap.containsKey(username) && userHashMap.get(username).equals(password);
    }

    public boolean checkIfUserExists(String username) {
        return userHashMap.containsKey(username);
    }

    public User getUser(String username) {

        /*
        I feel like there's a faster way to do this
        Since we want to return the user object and not just the password from the username key
        We need an ArrayList to keep track of the users
        */
        for (int i = 0; i < usersList.size(); i++) {
            if (usersList.get(i).getUsername().equals(username))
                return usersList.get(i);
        }
        return null;
    }

/*
    necessary methods to implement parcelable and be able to send UserList in an intent to
    another activity
     */

    protected UserList(Parcel in) {
        usersList = new ArrayList<>(0);
        in.readTypedList(usersList, User.CREATOR);
        userHashMap = new HashMap<>(0);
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        int count = in.readInt();
        for (int i = 0; i < count; i++) {
            userHashMap.put(in.readString(), in.readString());
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(usersList);

        dest.writeInt(userHashMap.size());
        for (String s: userHashMap.keySet()) {
            dest.writeString(s);    //username
            dest.writeString(userHashMap.get(s));   //password
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<UserList> CREATOR = new Parcelable.Creator<UserList>() {
        @Override
        public UserList createFromParcel(Parcel in) {
            return new UserList(in);
        }

        @Override
        public UserList[] newArray(int size) {
            return new UserList[size];
        }
    };
}
