/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package ee461l.groupstudyendpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Nullable;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(name = "groupstudyEndpoint", version = "v1",
        namespace = @ApiNamespace(ownerDomain = "groupstudyEndpoints.ee461l",
                ownerName = "groupstudyEndpoints.ee461l", packagePath = ""))
public class GroupstudyEndpoint {

    private static final Logger LOGGER = Logger.getLogger(GroupstudyEndpoint.class.getName());

    public GroupstudyEndpoint() {

    }

    /**
     * An endpoint that takes group wrapper and passes info to a group
     */
    @ApiMethod(name = "createGroup")
    public Groups createGroup(GroupWrapperEntity newGroup) {
        LOGGER.info("createGroup reached");
        Groups group = new Groups();
        group.setId(newGroup.getGroup().getId());
        group.setGroupName(newGroup.getGroup().getGroupName());
        group.setAdminUser(newGroup.getGroup().getAdminUser());
        group.setTeammates(newGroup.getGroup().getTeammates());
        LOGGER.info("group instantiated");
        /*Groups group = new Groups(newGroup.getGroup().getGroupName(), newGroup.getGroup().getAdminUser(),
                newGroup.getGroup().getTeammates());*/
        LOGGER.info("group saved to store");
        LOGGER.info("teammates size for new group: " + group.getTeammates().size());
        updateUsersGroups(newGroup.getGroup().getAdminUser(), group);
        OfyService.ofy().save().entity(group).now();
        return group;
    }

    /**
     * An endpoint that adds a task to a specific group's calendar
     */
    @ApiMethod(name = "createTask")
    public Groups createTask(@Named("groupName") String groupName, @Named("task") String task) {
        //will return null if group does not exist
        LOGGER.info("createTask reached");
        Groups group = OfyService.ofy().load().type(Groups.class).id(groupName).now();
        group.addTask(task);
        ArrayList<String> tasks = group.getTasks();
        group.setTasks(tasks);
        OfyService.ofy().save().entity(group).now();
        return group;
    }

    /**
     * An endpoint that adds a task to a specific group's calendar
     */
    @ApiMethod(name = "addMember")
    public Groups addMember(@Named("groupName") String groupName, @Named("member") String member) {
        //will return null if group does not exist
        LOGGER.info("removeMember reached");
        Groups group = OfyService.ofy().load().type(Groups.class).id(groupName).now();
        group.addMember(member);
        ArrayList<String> teammates = group.getTeammates();
        group.setTeammates(teammates);
        OfyService.ofy().save().entity(group).now();

        //add group to user's list of groups
        User u = OfyService.ofy().load().type(User.class).id(member).now();
        u = u.addGroup(group);
        OfyService.ofy().save().entity(u).now();
        return group;
    }

    /**
     * An endpoint that adds a task to a specific group's calendar
     */
    @ApiMethod(name = "removeMember")
    public Groups removeMember(@Named("groupName") String groupName, @Named("member") String member) {
        //will return null if group does not exist
        LOGGER.info("removeMember reached");
        Groups group = OfyService.ofy().load().type(Groups.class).id(groupName).now();
        group.removeMember(member);
        ArrayList<String> teammates = group.getTeammates();
        group.setTeammates(teammates);

        removeGroupFromUser(member, group.getGroupName());
        OfyService.ofy().save().entity(group).now();
        return group;
    }

    /**
     * removes the group from the user's list of groups
     */
    @ApiMethod(name = "removeGroupFromUser")
    public void removeGroupFromUser(@Named("member") String member, @Named("groupName") String groupName) {
        User u = OfyService.ofy().load().type(User.class).id(member).now();
        u.removeGroup(groupName);
        OfyService.ofy().save().entity(u).now();
        LOGGER.info("group removed from member");
    }

    /**
     * Creates message and stores it in objectify
     */
    @ApiMethod(name = "createMessage")
    public Groups createMessage(@Named("groupName") String groupName, @Named("message") String message) {
        //will return null if group does not exist
        LOGGER.info("createTask reached");
        Groups group = OfyService.ofy().load().type(Groups.class).id(groupName).now();
        group.addMessage(message);
        ArrayList<String> messages = group.getMessages();
        group.setMessages(messages);
        OfyService.ofy().save().entity(group).now();
        return group;
    }

    /**
     * An endpoint that adds a file to a specific group
     */
    @ApiMethod(name = "addFile")
    public Groups addFile(@Named("groupName") String groupName, FilesEntity file) {
        //byte[] fileBytes = file.getBytes(Charset.forName("UTF-8"));
        //will return null if group does not exist
        //String groupName = file.getGroupName();
        //Byte[] fileContents = file.getFile();
        LOGGER.info("file contents: " + file.getFileContents());
        Groups group = OfyService.ofy().load().type(Groups.class).id(groupName).now();
        group = group.addFile(file);
        OfyService.ofy().save().entity(group).now();
        LOGGER.info("returned to endpoint addFile method");
        return group;
    }

    @ApiMethod(name = "updateUsersGroups")
    public Groups updateUsersGroups(@Named("adminUsername") String username, Groups group) {
        LOGGER.info("teammates size for new group: " + group.getTeammates().size());
        List<User> users = new ArrayList<User>();
        User u = OfyService.ofy().load().type(User.class).id(username).now();
        u = u.addGroup(group);
        users.add(u);

        for (int i = 0; i < group.getTeammates().size(); i++) {
            u = OfyService.ofy().load().type(User.class).id(group.getTeammates().get(i)).now();
            LOGGER.info("teammate name: " + u.getUsername());
            //Groups g = OfyService.ofy().load().type(Groups.class).id(group.getGroup().getGroupName()).now();
            LOGGER.info(group.getTeammates().get(i) + " groups size: " + u.getListOfGroups().size());
            LOGGER.info("updateUsersGroups group name: " + group.getGroupName());
            u = u.addGroup(group);
            users.add(u);
        }

        OfyService.ofy().save().entities(users).now();
        Groups g = group;
        return g;
    }

    /**
     * An endpoint that loads a single group so task, messaging, etc info can be retrieved
     */
    @ApiMethod(name = "retrieveSingleGroup")
    public Groups retrieveSingleGroup(@Named("groupName") String groupName) {
        //will return null if group does not exist
        Groups group = OfyService.ofy().load().type(Groups.class).id(groupName).now();
        //LOGGER.info(group.getAdminUser().getUsername());
        return group;
    }

    //loads the list of groups in the app
    @ApiMethod(name = "loadGroups")
    public CollectionResponse<Groups> loadGroups(@Nullable @Named("cursor") String cursorString,
                                                 @Nullable @Named("count") Integer count) {
        Query<Groups> query = OfyService.ofy().load().type(Groups.class);
        if (count != null) query.limit(count);
        if (cursorString != null && cursorString != "") {
            query = query.startAt(Cursor.fromWebSafeString(cursorString));
        }

        List<Groups> listOfGroups = new ArrayList<>();
        QueryResultIterator<Groups> iterator = query.iterator();
        int num = 0;
        while (iterator.hasNext()) {
            listOfGroups.add(iterator.next());
            if (count != null) {
                num++;
                if (num == count) break;
            }
        }

        //Find the next cursor
        if (cursorString != null && cursorString != "") {
            Cursor cursor = iterator.getCursor();
            if (cursor != null) {
                cursorString = cursor.toWebSafeString();
            }
        }

        return CollectionResponse.<Groups>builder().setItems(listOfGroups).setNextPageToken(cursorString).build();
    }

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "createUser")
    public User createUser(@Named("name") String name, @Named("password") String password) {
        User user = new User(name, password);

        OfyService.ofy().save().entity(user).now();
        return user;
    }

    //loads the list of users in the app
    @ApiMethod(name = "loadUsers")
    public CollectionResponse<User> loadUsers(@Nullable @Named("cursor") String cursorString,
                                              @Nullable @Named("count") Integer count) {
        Query<User> query = OfyService.ofy().load().type(User.class);
        if (count != null) query.limit(count);
        if (cursorString != null && !cursorString.equals("")) {
            query = query.startAt(Cursor.fromWebSafeString(cursorString));
        }

        List<User> listOfUsers = new ArrayList<>();
        QueryResultIterator<User> iterator = query.iterator();
        int num = 0;
        while (iterator.hasNext()) {
            listOfUsers.add(iterator.next());
            if (count != null) {
                num++;
                if (num == count) break;
            }
        }

        //Find the next cursor
        if (cursorString != null && cursorString != "") {
            Cursor cursor = iterator.getCursor();
            if (cursor != null) {
                cursorString = cursor.toWebSafeString();
            }
        }

        return CollectionResponse.<User>builder().setItems(listOfUsers).setNextPageToken(cursorString).build();
    }

    //loads the single user in the app
    @ApiMethod(name = "retrieveSingleUser")
    public User retrieveSingleUser(@Named("username") String username,
                                   @Named("activityName") String activityName) {
        LOGGER.info("Called from: " + activityName);
        //will return null if user is not found
        User user = OfyService.ofy().load().type(User.class).id(username).now();
        LOGGER.info("username: " + user.getUsername());
        return user;
    }
}
