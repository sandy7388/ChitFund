package chitfund.wayzontech.chitfund.chitfund.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.fragment.AuctionFragment;
import chitfund.wayzontech.chitfund.chitfund.fragment.GroupListFragment;
import chitfund.wayzontech.chitfund.chitfund.fragment.HomeFragment;
import chitfund.wayzontech.chitfund.chitfund.fragment.JoinedGroupFragment;
import chitfund.wayzontech.chitfund.chitfund.fragment.LastAuctionFragment;
import chitfund.wayzontech.chitfund.chitfund.fragment.NotificationsFragment;
import chitfund.wayzontech.chitfund.chitfund.fragment.ProfileFragment;
import chitfund.wayzontech.chitfund.chitfund.session.SessionManager;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {
    private SessionManager session;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private View navHeader;
    public static int navItemIndex = 0;

    private static final String TAG_HOME = "home";
    private static final String TAG_PROFILE = "profile";
    private static final String TAG_AUCTION = "auction";
    private static final String TAG_LAST_AUCTION = "last_auction";
    private static final String TAG_NOTIFICATIONS = "notifications";
    private static final String TAG_GRPLIST = "group_list";
    private static final String TAG_JOINEDGRP = "joined_grplist";

    public static String CURRENT_TAG = TAG_HOME;

    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

    }

    public void initialize()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        session=new SessionManager(this);
        mHandler = new Handler();
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        fab = findViewById(R.id.fab);
        navHeader = navigationView.getHeaderView(0);
        fab.setOnClickListener(this);
//        if(!SessionManager.getInstance(this).isLoggedIn())
//        {
//            startActivity(new Intent(this, LoginActivity.class));
//            finish();
//        }
        setUpNavigationView();
        navigationView.getMenu().getItem(4).setActionView(R.layout.menu_dot);
    }

    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        //setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            // show or hide the fab button
            toggleFab();
            return;
        }


        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.content, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex)
        {
            case 0:
                // home
                return new HomeFragment();
            case 1:
                // Profile
                return new ProfileFragment();
            case 2:
                // Auction
                return new AuctionFragment();
            case 3:
                // Last Auction
                return new JoinedGroupFragment();
            case 4:
                // Notification
                return new LastAuctionFragment();
            case 5:
                // Group List
                return new GroupListFragment();
            case 6:
                // Joined Group
                return new NotificationsFragment();
            default:
                // Home
                return new HomeFragment();
        }
    }

    private void selectNavMenu()
    {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }
    private void setUpNavigationView()
    {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_profile:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_PROFILE;
                        break;
                    case R.id.nav_auction:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_AUCTION;
                        break;
                    case R.id.nav_joinedGroup:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_JOINEDGRP;
                        break;
                    case R.id.nav_last_auction:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_LAST_AUCTION;
                        break;
                    case R.id.nav_grpList:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_GRPLIST;
                        break;
                    case R.id.nav_notifications:
                        navItemIndex = 6;
                        CURRENT_TAG = TAG_NOTIFICATIONS;
                        break;
                    default:
                        navItemIndex = 0;
                }
                //Checking if the item is in checked state or not, if not make it in checked state
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                item.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        if (navItemIndex == 4)
        {
            navigationView.getMenu().getItem(4).setActionView(R.layout.menu_dot_1);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id)
        {
            case R.id.action_logout:
                alertForLogout();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fab:
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
        }
    }

    public void setFragment(Fragment targetFragment)
    {
        Fragment fragment = targetFragment;
        if (fragment != null)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content, fragment)
                    .addToBackStack(null)
                    .commit();
        }

    }

    // Alert for logout
    void alertForLogout()
    {
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(this);

        aBuilder.setMessage("Are you sure, You want to Logout");
        aBuilder.setTitle("Logout Alert");
        //aBuilder.setIcon(R.drawable.warning);
        aBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SessionManager.getInstance(MainActivity.this).logout();
                finish();
            }
        });
        aBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = aBuilder.create();
        alertDialog.show();
    }


    // onBack pressed
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    // show or hide the fab
    private void toggleFab() {
        if (navItemIndex == 0)
            fab.show();
        else
            fab.hide();
    }

}
