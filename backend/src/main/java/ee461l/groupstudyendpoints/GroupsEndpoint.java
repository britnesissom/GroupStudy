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
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(name = "groupsEndpoint", version = "v1",
        namespace = @ApiNamespace(ownerDomain = "groupstudyEndpoints.ee461l",
                ownerName = "groupstudyEndpoints.ee461l", packagePath = ""))
public class GroupsEndpoint {

    public GroupsEndpoint() {

    }

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "createGroup")
    public Groups createGroup(@Named("groupName") String groupName, User adminUser) {
        User testAdminUser = new User("Abraham", "1234");
        Groups groups = new Groups("GroupStudy", testAdminUser);

        OfyService.ofy().save().entity(groups).now();
        return groups;
    }

    //loads the list of users in the app
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

}
