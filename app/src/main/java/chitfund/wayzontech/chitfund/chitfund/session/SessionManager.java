package chitfund.wayzontech.chitfund.chitfund.session;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;

import chitfund.wayzontech.chitfund.chitfund.activity.LoginActivity;
import chitfund.wayzontech.chitfund.chitfund.model.MemberLogin;


/**
 * Created by sandy on 25/11/17.
 */

@SuppressLint("StaticFieldLeak")
public class SessionManager
{

    private static SessionManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "mySharedPref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USER_PASSWORD = "password";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_MEMBER_ID = "member_id";
    private static final String KEY_SUB_DOMAIN = "subdomain";

//    private static final String KEY_LOCATION_NAME = "location";
//    private static final String KEY_WORKING_STATUS = "working_status";

    public SessionManager(Context context)
    {
        mCtx = context;
    }
    public static synchronized SessionManager getInstance(Context context)
    {
        if (mInstance == null)
        {
            mInstance = new SessionManager(context);
        }
        return mInstance;
    }

    // for user login to store  the user session
    public boolean userLogin(MemberLogin memberLogin)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, memberLogin.getUsername());
        editor.putString(KEY_USER_PASSWORD, memberLogin.getPassword());
        //editor.putString(KEY_MEMBER_ID,memberLogin.getMember_id());
        editor.putString(KEY_USER_ID, memberLogin.getId());
        editor.putString(KEY_SUB_DOMAIN, memberLogin.getSubdomain());

        editor.apply();
        editor.commit();

        return true;
    }
    public boolean setUserId(MemberLogin memberLogin)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_ID, memberLogin.getUsername());
        editor.apply();
        editor.commit();
        return true;
    }

    public boolean setMemberId(MemberLogin memberLogin)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_MEMBER_ID, memberLogin.getUsername());
        editor.apply();
        editor.commit();
        return true;
    }
    //check whether the user is logged in or not
    public boolean isLoggedIn()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USERNAME, null) != null)
        {
            return true;
        }
        return false;
    }

    // Get Username
    public String getUsername()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) ;
    }

    // Get Subdomain
    public static String getSubdomain()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_SUB_DOMAIN, null) ;
    }

    // Get User id
    public String getUserID()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_ID, null) ;
    }

    // Get User id
    public String getMemberID()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_MEMBER_ID, null) ;
    }

    // Get Users Name
    public String getName()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAME,null);
    }

    // this method is used to logout the session
    public void logout()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        editor.commit();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }

    public void setIndexSerialItem(ArrayList<String> items)
    {
        try
        {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(items);
            prefsEditor.putString("SerializableObject", json);
            prefsEditor.apply();
            prefsEditor.commit();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getIndexSerialItem()
    {
        try
        {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("SerializableObject", "");
            String[] yourSerializableObject = gson.fromJson(json, String[].class);
            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(yourSerializableObject));

            System.out.println(arrayList);

            return arrayList;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
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
}
