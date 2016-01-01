package ee461l.groupstudy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.parse.ParseUser;

import ee461l.groupstudy.OnSendGroupNameListener;
import ee461l.groupstudy.R;
import ee461l.groupstudy.fragments.AboutFragment;
import ee461l.groupstudy.fragments.AppHomePageFragment;
import ee461l.groupstudy.fragments.CalendarFragment;
import ee461l.groupstudy.fragments.FileSharingFragment;
import ee461l.groupstudy.fragments.GroupHomePageFragment;
import ee461l.groupstudy.fragments.MessagingFragment;


public class MainActivity extends AppCompatActivity implements OnSendGroupNameListener {

    private static final String TAG = "NavDrawerGroups";
    private DrawerLayout drawerLayout;
    private CharSequence mTitle;    //title of action bar
    private static String groupId;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupDrawer((DrawerLayout) findViewById(R.id.nav_drawer_layout), (NavigationView)
                findViewById(R.id.nav_view));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        //default view when group is opened is list of groups
        groupId = null;
        username = getIntent().getStringExtra("username");

        //if no savedInstanceState, go to the first page in menu (home)
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_fragment,
                    AppHomePageFragment.newInstance(username)).commit();
        }
    }

    private void setupDrawer(DrawerLayout dl, final NavigationView nv) {
        this.drawerLayout = dl;
        nv.getMenu().getItem(0).setChecked(true);
        nv.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(final MenuItem menuItem) {
                        Log.d(TAG, "group name in main: " + groupId);
                        menuItem.setChecked(true);
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        switch (menuItem.getItemId()) {
                            case R.id.nav_app_home:
                                if (nv.getMenu().getItem(0).getItemId() != R.id.nav_app_home) {
                                    nv.getMenu().clear();
                                    nv.inflateMenu(R.menu.main_menu);
                                }
                                transaction.replace(R.id.content_fragment, AppHomePageFragment
                                        .newInstance(username));
                                transaction.commit();
                                break;
                            case R.id.nav_all_groups:
                                nv.getMenu().clear();
                                nv.inflateMenu(R.menu.main_menu);
                                transaction.replace(R.id.content_fragment, AppHomePageFragment
                                        .newInstance(username));
                                transaction.commit();
                                break;
                            case R.id.nav_group_home:
                                //only change menu if you're not already in group page
                                if (nv.getMenu().getItem(0).getItemId() != R.id.nav_group_home) {
                                    nv.getMenu().clear();
                                    nv.inflateMenu(R.menu.menu_group_nav);
                                }
                                transaction.replace(R.id.content_fragment, GroupHomePageFragment.newInstance(groupId, username));
                                transaction.commit();
                                break;
                            case R.id.nav_calendar:
                                transaction.replace(R.id.content_fragment, CalendarFragment
                                        .newInstance(groupId));
                                transaction.commit();
                                break;
                            case R.id.nav_messages:
                                transaction.replace(R.id.content_fragment, MessagingFragment
                                        .newInstance(groupId, username));
                                transaction.commit();
                                break;
                            case R.id.nav_files:
                                transaction.replace(R.id.content_fragment, FileSharingFragment
                                        .newInstance(groupId));
                                transaction.commit();
                                break;
                            case R.id.nav_about:
                                transaction.replace(R.id.content_fragment, AboutFragment.newInstance());
                                transaction.commit();
                                break;
                            case R.id.nav_logout:
                                ParseUser.logOut();
                                startActivity(new Intent(MainActivity.this, LoginScreenActivity
                                        .class));
                                break;
                        }
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void sendGroupName(String groupName) {
        groupId = groupName;
        Log.d(TAG, "sendGroupName: " + groupId);
    }
}