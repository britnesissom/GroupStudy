/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package ee461l.groupstudyendpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiClass;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Nullable;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(name = "groupstudyEndpoint", version = "v1",
        namespace = @ApiNamespace(ownerDomain = "groupstudyEndpoints.ee461l",
                ownerName = "groupstudyEndpoints.ee461l", packagePath = ""))
public class GroupstudyEndpoint {

    public GroupstudyEndpoint() {

    }

    /**
     * An endpoint that takes group wrapper and passes info to a group
     */
    @ApiMethod(name = "createGroup")
    public Groups createGroup(GroupWrapperEntity newGroup) {
    /*    User testAdminUser = new User("Abraham", "1234");
        Groups groups = new Groups("GroupStudy", testAdminUser);
*/
        Groups group = new Groups(newGroup.getGroup().getGroupName(), newGroup.getGroup().getAdminUser(),
                newGroup.getGroup().getUsers());
        OfyService.ofy().save().entity(group).now();
        return group;
    }

    /**
     * An endpoint that loads a single group so task, messaging, etc info can be retrieved
     */
    @ApiMethod(name = "retrieveSingleGroup")
    public Groups retrieveSingleGroup(@Named("groupName") String groupName) {

        //will return null if group does not exist
        Groups group = OfyService.ofy().load().type(Groups.class).id(groupName).now();
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
        if (cursorString != null && cursorString != "") {
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
    public User retrieveSingleUser(@Named("username") String username) {

        //will return null if user is not found
        return OfyService.ofy().load().type(User.class).id(username).now();
    }
}
