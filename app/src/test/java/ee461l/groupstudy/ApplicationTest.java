package ee461l.groupstudy;


import android.app.Application;
import android.test.ApplicationTestCase;

import org.junit.*;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    UserList userlist = null;

    @Override
    @Before
    protected void setUp() throws Exception {
        User john = new User("John", "123");
        userlist = new UserList();
        userlist.add(john);
        User sue = new User ("Sue", "1234");
        userlist.add(sue);
        User frank = new User("Frank", "12345");
        userlist.add(frank);
    }

    @org.junit.Test
    public void testAdd(){
        assertEquals("John", userlist.getUser("John").getUsername());
        assertEquals("Sue", userlist.getUser("Sue").getUsername());
        assertEquals("Frank", userlist.getUser("Frank").getUsername());

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}