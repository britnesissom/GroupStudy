package ee461l.groupstudy;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class NavDrawerGroups extends AppCompatActivity {

    private static final String TAG = "NavDrawerGroups";
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private String[] navMenuTitles;
    private ActionBarDrawerToggle drawerToggle;
    private CharSequence mDrawerTitle;  //title of navigation drawer eg menu
    private CharSequence mTitle;    //title of action bar
    private String groupName;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer_layout);

        Log.d(TAG, "NavDrawerGroup opened");

        //default view when group is opened is list of groups
        groupName = getIntent().getStringExtra("groupName");
        username = getIntent().getStringExtra("username");

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,
                GroupHomePageFragment.newInstance(groupName, username)).commit();

        mTitle = mDrawerTitle = getTitle();

        // R.id.drawer_layout should be in every activity with exactly the same id.
        drawerLayout = (DrawerLayout) findViewById(R.id.nav_drawer_layout);
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_group_items);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.nav_drawer_list_item, navMenuTitles));
        // Set the list's click listener
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.drawer_open, R.string.drawer_closed)
        {
            public void onDrawerClosed(View view)
            {
            }

            public void onDrawerOpened(View drawerView)
            {
                getSupportActionBar().setTitle(R.string.menu);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

        //if no savedInstanceState, go to the first page in menu (home)
        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        Fragment fragment = null;
        Bundle args = null;

        //go to selected activity
        switch (position) {
            case 0: //go to group home page
                fragment = GroupHomePageFragment.newInstance(groupName, username);
                /*args = new Bundle();
                args.putInt(MainPageFragment.ARG_MENU_CHOICE_NUM, position);*/
                break;
            case 1: //go to app's home page not group home page
                Intent intent = new Intent(this, NavDrawerHomePage.class);
                intent.putExtra("username", username);
                startActivity(intent);
            case 2: //go to calendar
                fragment = CalendarFragment.newInstance(groupName);
                break;
            case 3: //go to messages
                fragment = MessagingFragment.newInstance(groupName, username);
                break;
            case 4: //go to files
                fragment = FileSharingFragment.newInstance(groupName);
                break;
            case 5: //view info about the app
                fragment = AboutFragment.newInstance();
                break;
            case 6: //log out of app
                Intent i = new Intent(this, LoginScreenActivity.class);
                startActivity(i);
            default:
        }

        //if a menu option is chosen, start fragment
        //else, do not do anything
        if (fragment != null) {
            //fragment.setArguments(args);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            // update selected item and title, then close the drawer
            drawerList.setItemChecked(position, true);
            setTitle(navMenuTitles[position]);
            drawerLayout.closeDrawer(drawerList);
        }
    }

    //set the action bar title ie where it normally says GroupStudy
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }
}