package chitfund.wayzontech.chitfund.chitfund.sqliteHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import chitfund.wayzontech.chitfund.chitfund.model.UserProfile;

/**
 * Created by sandy on 12/2/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Create table
    public static final String CREATE_TABLE = "create table " +
            DBContract.TABLE_NAME + "(" +
            DBContract.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,  " +
            DBContract.COLUMN_SUBDOMAIN_NAME + " text, " +
            DBContract.COLUMN_ROLE_ID + " text, " +
            DBContract.COLUMN_USER_ID + " text, " +
            DBContract.COLUMN_NAME + " text, " +
            DBContract.COLUMN_USERNAME + " text);";
    // Database version
    private static final int DB_VERSION = 1;
    // Drop table if exits
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + DBContract.TABLE_NAME;

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DBContract.DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);

    }

    public void saveInformation(UserProfile userProfile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBContract.COLUMN_SUBDOMAIN_NAME, userProfile.getSundomainName());
        contentValues.put(DBContract.COLUMN_ROLE_ID, userProfile.getRole_id());
        contentValues.put(DBContract.COLUMN_USER_ID, userProfile.getUser_id());
        contentValues.put(DBContract.COLUMN_USERNAME, userProfile.getUsername());
        contentValues.put(DBContract.COLUMN_NAME, userProfile.getName());
        // Inserting Row
        db.insert(DBContract.TABLE_NAME, null, contentValues);
        db.close();


    }

    public List<UserProfile> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                DBContract.COLUMN_SUBDOMAIN_NAME,
                DBContract.COLUMN_ROLE_ID,
                DBContract.COLUMN_USER_ID,
                DBContract.COLUMN_USERNAME,
                DBContract.COLUMN_NAME,

        };
        // sorting orders
        String sortOrder =
                DBContract.COLUMN_SUBDOMAIN_NAME + " ASC";
        List<UserProfile> userProfileList = new ArrayList<UserProfile>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(DBContract.TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UserProfile userProfile = new UserProfile();
                userProfile.setSundomainName(cursor.getString(cursor.getColumnIndex(DBContract.COLUMN_SUBDOMAIN_NAME)));
                userProfile.setRole_id(cursor.getString(cursor.getColumnIndex(DBContract.COLUMN_ROLE_ID)));
                userProfile.setUser_id(cursor.getString(cursor.getColumnIndex(DBContract.COLUMN_USER_ID)));
                userProfile.setUsername(cursor.getString(cursor.getColumnIndex(DBContract.COLUMN_USERNAME)));
                userProfile.setName(cursor.getString(cursor.getColumnIndex(DBContract.COLUMN_NAME)));
                // Adding userProfile record to list
                userProfileList.add(userProfile);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userProfileList;
    }


    public List<UserProfile> getSubdomain() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<UserProfile> jodiList = new ArrayList<UserProfile>();

        String selectQuery = "SELECT  * FROM " + DBContract.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                UserProfile contact = new UserProfile();
                //contact.set_id(Integer.parseInt(cursor.getString(0)));
                contact.setSundomainName(cursor.getString(1));
                contact.setRole_id(cursor.getString(2));
                contact.setUser_id(cursor.getString(3));
                contact.setUsername(cursor.getString(4));
                contact.setName(cursor.getString(5));
                // Adding contact to list
                jodiList.add(contact);
            } while (cursor.moveToNext());

        }

        return jodiList;
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + DBContract.TABLE_NAME);
        db.close();
    }
}
