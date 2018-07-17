package chitfund.wayzontech.chitfund.chitfund.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.fragment.GroupListFragment;
import chitfund.wayzontech.chitfund.chitfund.fragment.HomeFragment;
import chitfund.wayzontech.chitfund.chitfund.fragment.JoinedGroupFragment;
import chitfund.wayzontech.chitfund.chitfund.fragment.LastAuctionFragment;
import chitfund.wayzontech.chitfund.chitfund.fragment.ProfileFragment;
import chitfund.wayzontech.chitfund.chitfund.fragment.ReportFragment;
import chitfund.wayzontech.chitfund.chitfund.httpHelper.Config;
import chitfund.wayzontech.chitfund.chitfund.httpHelper.URLs;
import chitfund.wayzontech.chitfund.chitfund.other.CircleTransform;
import chitfund.wayzontech.chitfund.chitfund.receiverNservices.NotificationUtils;
import chitfund.wayzontech.chitfund.chitfund.session.MemberSession;
import chitfund.wayzontech.chitfund.chitfund.volley.VolleySingleton;

public class MainActivity extends RuntimePermissionActivity
        implements View.OnClickListener
{
    private static final int REQUEST_PERMISSIONS = 20;
    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private MemberSession session;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private Toolbar mToolbar;
    private String loginStatus = "0";
    private FloatingActionButton fab;
    private View navHeader;
    private TextView textName,textEmail;
    private ImageView profile;
    private String strName,strEmail;
    public static int navItemIndex = 0;
    private static final String urlProfileImg = "https://s-media-cache-ak0.pinimg.com/736x/2c/bb/04/2cbb04e7ef9266e1e57a9b0e75bc555f.jpg";
    private static final String TAG_HOME = "home";
    private static final String TAG_PROFILE = "profile";
    //private static final String TAG_AUCTION = "auction";
    private static final String TAG_LAST_AUCTION = "last_auction";
    //private static final String TAG_NOTIFICATIONS = "notifications";
    private static final String TAG_GRPLIST = "group_list";
    private static final String TAG_JOINEDGRP = "joined_grplist";
    private static final String TAG_REPORTS = "reports";
    private static final String TAG_MEMBER_REPORTS = "member_reports";

    public static String CURRENT_TAG = TAG_HOME;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        permission();
        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                }
            }
        };

        displayFirebaseRegId();

    }

    // Runtime Permission
    @Override
    public void onPermissionsGranted(final int requestCode) {
        Toast.makeText(this, "Permissions Received.", Toast.LENGTH_LONG).show();
    }

    void permission() {
        MainActivity.super.requestAppPermissions(new
                        String[]{Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION}, R.string
                        .runtime_permissions_txt
                , REQUEST_PERMISSIONS);
    }



    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);
//        Toast.makeText(getApplicationContext(), "Firebase reg id: " + regId
//                , Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
    public void initialize()
    {
        mToolbar = findViewById(R.id.mainActivityToolbar1);
        setSupportActionBar(mToolbar);
        session=new MemberSession(this);
        mHandler = new Handler();
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        fab = findViewById(R.id.fab);
        navHeader = navigationView.getHeaderView(0);
        textName = navHeader.findViewById(R.id.userName);
        textEmail = navHeader.findViewById(R.id.userEmail);
        profile = navHeader.findViewById(R.id.imageView);
        fab.setOnClickListener(this);
        if(!MemberSession.getInstance(this).isLoggedIn())
        {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        loadHeaderMenu();
        setUpNavigationView();
    }

    private void loadHomeFragment() {

        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }

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

        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

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
//            case 2:
//                // Auction
//                return new AuctionFragment();
            case 2:
                // Joined Group
                return new JoinedGroupFragment();
            case 3:
                // Last Auction
                return new LastAuctionFragment();
            case 4:
                // Group List
                return new GroupListFragment();
            case 5:
                // Collection Reports
                return new ReportFragment();

            default:
                // Home
                return new HomeFragment();
        }
    }

    void loadHeaderMenu()
    {
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(profile);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.PROFILE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("profile_info");
                            for (int i=0;i<jsonArray.length();i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                strName = object.getString("member_name");
                                strEmail = object.getString("email");

                                textName.setText(strName);
                                textEmail.setText(strEmail);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("user_id",session.getUserID());
                return map;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    private void selectNavMenu()
    {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }
    private void setUpNavigationView()
    {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

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
//                    case R.id.nav_auction:
//                        navItemIndex = 2;
//                        CURRENT_TAG = TAG_AUCTION;
//                        break;
                    case R.id.nav_joinedGroup:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_JOINEDGRP;
                        break;
                    case R.id.nav_last_auction:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_LAST_AUCTION;
                        break;
                    case R.id.nav_grpList:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_GRPLIST;
                        break;

                    case R.id.nav_report:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_REPORTS;
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
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, mToolbar, R.string.openDrawer, R.string.closeDrawer) {

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
    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

//        if (navItemIndex == 4)
//        {
//            navigationView.getMenu().getItem(6).setActionView(R.layout.menu_dot_1);
//        }
//        if (navItemIndex == 1)
//        {
//            getMenuInflater().inflate(R.menu.edit_profile,menu);
//        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_logout:
                //androidLogout();
                MemberSession.getInstance(MainActivity.this).logout();
                finish();
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
                MemberSession.getInstance(MainActivity.this).logout();
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
    private boolean doubleBackToExitPressedOnce = false;
    boolean shouldLoadHomeFragOnBackPress = true;

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

        super.onBackPressed();
    }

    // Method to check the network connection
    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return (networkInfo != null && networkInfo.isConnected());
    }

    private void androidLogout()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.LOGOUT_URL,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getString("success").equals("1"))
                    {
                        Toast.makeText(MainActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                        if (checkNetworkConnection())
                        {
                            MemberSession.getInstance(MainActivity.this).logout();
                            finish();
                        }
                        else
                            Toast.makeText(MainActivity.this,"Please check your network connection",Toast.LENGTH_SHORT).show();
                    }

                    else
                        Toast.makeText(MainActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();


                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userid",session.getUserID());
                //params.put("loginstatus",loginStatus);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


}
