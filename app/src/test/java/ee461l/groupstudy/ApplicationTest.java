package ee461l.groupstudy;


import android.app.Application;
import android.test.ApplicationTestCase;

import org.junit.*;

import java.util.List;

import ee461l.groupstudyendpoints.userEndpoint.model.User;
/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    List<User> users;

    @Override
    @Before
    protected void setUp() throws Exception {
        new CreateUserEndpointsAsyncTask(getContext()).execute("John","123");
        new CreateUserEndpointsAsyncTask(getContext()).execute("Sue","1234");
        new CreateUserEndpointsAsyncTask(getContext()).execute("Frank","12345");

        new LoadUserEndpointsAsyncTask(getContext(), new OnRetrieveUsersTaskCompleted() {
            @Override
            public void onTaskCompleted(List<User> list) {
                users = list;
            }
        });
    }

    @org.junit.Test
    public void testAdd(){
        //how to test a user exists since it's a list of custom objects so there
        //is obviously no getUser command
        //assertEquals("John", users.contains("John"));
        //assertEquals("Sue", users.getUser("Sue").getUsername());
        //assertEquals("Frank", users.getUser("Frank").getUsername());

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}