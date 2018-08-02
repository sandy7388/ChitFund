package chitfund.wayzontech.chitfund.chitfund.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

import chitfund.wayzontech.chitfund.chitfund.activity.LoginActivity;
import chitfund.wayzontech.chitfund.chitfund.activity.SubdomainActivity;
import chitfund.wayzontech.chitfund.chitfund.model.Subdomain;

public class SubdomainSession {

    public static final String SHARED_PREF_NAME = "subdomainSharedPref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_SUB_DOMAIN = "subdomain";
    private static final String KEY_ROLE_ID = "role_id";
    private static final String KEY_NAME = "name";
    private static SubdomainSession mInstance;
    private static Context mCtx;
//    private static final String KEY_LOCATION_NAME = "location";
//    private static final String KEY_WORKING_STATUS = "working_status";

    public SubdomainSession(Context context) {
        mCtx = context;
    }

    public static synchronized SubdomainSession getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SubdomainSession(context);
        }
//        else {
//            mInstance = new SessionManager(context);
//        }
        return mInstance;
    }

    // for user login to store  the user session
    public boolean userLogin(Subdomain subdomain) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, subdomain.getStrUsername());
        editor.putString(KEY_USER_ID, subdomain.getStrUserId());
        editor.putString(KEY_SUB_DOMAIN, subdomain.getStrSubdomain());
        editor.putString(KEY_ROLE_ID, subdomain.getStrRoleId());
        editor.putString(KEY_NAME, subdomain.getStrName());
        editor.apply();
        editor.commit();

        return true;
    }

    //check whether the user is logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    // Get Username
    public String getName() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    // Get User id
    public String getUserID() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_ID, null);
    }

    // this method is used to logout the session
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        editor.commit();
        mCtx.startActivity(new Intent(mCtx, SubdomainActivity.class));
    }

    public void clearSession() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        editor.commit();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }

    public ArrayList<String> getIndexSerialItem() {
        try {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("SerializableObject", "");
            String[] yourSerializableObject = gson.fromJson(json, String[].class);
            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(yourSerializableObject));

            System.out.println(arrayList);

            return arrayList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setIndexSerialItem(ArrayList<String> items) {
        try {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(items);
            prefsEditor.putString("SerializableObject", json);
            prefsEditor.apply();
            prefsEditor.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public void locationSession(String location)
    {
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(KEY_LOCATION_NAME,location);
        editor.commit();
    }
    public String getLocation()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_LOCATION_NAME, "") ;
    }

    public void workingStatusSession(String working_status)
    {
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(KEY_WORKING_STATUS,working_status);
        editor.commit();
    }

    public String getWorkingStatus()
    {
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_WORKING_STATUS,"");
    }*/

    // Get Users Name
    public String getSubDomain() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_SUB_DOMAIN, null);
    }
}
