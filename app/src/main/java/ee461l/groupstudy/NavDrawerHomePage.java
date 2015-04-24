package ee461l.groupstudy;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class NavDrawerHomePage extends ActionBarActivity {

    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private String[] navMenuTitles;
    private ActionBarDrawerToggle drawerToggle;
    private CharSequence mDrawerTitle;  //title of navigation drawer eg menu
    private CharSequence mTitle;    //title of action bar

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer_layout);

        //default view when app is loaded is the home page
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,
                new HomePageFragment()).commit();

        mTitle = mDrawerTitle = getTitle();

        // R.id.drawer_layout should be in every activity with exactly the same id.
        drawerLayout = (DrawerLayout) findViewById(R.id.nav_drawer_layout);
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_home_page_items);
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
            //if user doesn't select anything, change title back to the previous fragment title
            public void onDrawerClosed(View view)
            {
                getSupportActionBar().setTitle(mTitle);
            }

            //set title to menu when drawer is opened
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
            case 0: //go to home page
                fragment = HomePageFragment.newInstance();
                /*args = new Bundle();
                args.putInt(MainPageFragment.ARG_MENU_CHOICE_NUM, position);*/
                break;
            case 1: //go to about screen
                fragment = AboutFragment.newInstance();
                break;
            case 2: //log out of app
                Intent intent = new Intent(this, LoginScreenActivity.class);
                startActivity(intent);
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

    //changes to group specific nav drawer
    public void newNavDrawerActivity(View view) {
        Intent intent = new Intent(this, NavDrawerGroups.class);
        startActivity(intent);
    }
}
