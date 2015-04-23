/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package ee461l.groupstudy;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(name = "groupstudy", version = "v1", namespace = @ApiNamespace(ownerDomain = "groupstudy.ee461l", ownerName = "groupstudy.ee461l", packagePath = ""))
public class UsersEndpoint{

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "createUser")
    public void createUser(@Named("name") String name, @Named("password") String password) {
        Users users = new Users("Abraham", "1234");

        OfyService.ofy().save().entity(users).now();
    }

    @ApiMethod(name = "loadUser")
    public void loadUser(Users users) {
        OfyService.ofy().load().entity(Users.class).now();
    }

}
