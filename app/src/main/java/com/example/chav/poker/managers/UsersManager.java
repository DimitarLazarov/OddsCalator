package com.example.chav.poker.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.chav.poker.model_db.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import model.User;

/**
 * Created by Chav on 4/5/2016.
 */
public class UsersManager {

    public static UsersManager instance;

    public static UsersManager getInstance(Context context) {
        if (instance == null) {
            instance = new UsersManager(context);
        }

        return instance;
    }


    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context mContext;
    private User mCurrentUser;

    private UsersManager(Context context) {
        mContext = context;
        dbHelper = DatabaseHelper.getInstance(context);
    }

    // Open Database function
    public void open() {
        // Allow database to be in writable mode
        database = dbHelper.getWritableDatabase();
    }

    // Close Database function
    public void close() {
        if (database != null)
            database.close();
    }

    public void registerUser(User user) {

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_USER_USERNAME, user.getUsername());
        values.put(DatabaseHelper.KEY_USER_PASSWORD, user.getPassword());
        values.put(DatabaseHelper.KEY_USER_EMAIL, user.getEmail());

        open();
        long userId = database.insert(DatabaseHelper.TABLE_USERS, DatabaseHelper.KEY_USER_USERNAME, values);
        user.setId(userId);
        close();

        this.mCurrentUser = user;

        Log.d("user", "" + user.getId());
        Log.d("user", "" + user.getUsername());
    }

    public User getUser(String user) {
        open();
        String selectKey = "SELECT * FROM " + DatabaseHelper.TABLE_USERS + " WHERE " + DatabaseHelper.KEY_USER_USERNAME + " = \"" + user + "\"";

        Cursor c = database.rawQuery(selectKey, null);

        long userId = -1;
        String name = null;
        String email = null;
        String pass = null;

        if (c != null) {
            c.moveToFirst();
            userId = c.getLong(c.getColumnIndex(DatabaseHelper.KEY_ID));
            name = c.getString(c.getColumnIndex(DatabaseHelper.KEY_USER_USERNAME));
            email = c.getString(c.getColumnIndex(DatabaseHelper.KEY_USER_EMAIL));
            pass = c.getString(c.getColumnIndex(DatabaseHelper.KEY_USER_PASSWORD));
        }
        close();
        c.close();
        return new User(userId, name, pass, email);

    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> allUsers = new ArrayList<>();
        String selectQuery = " SELECT * FROM " + DatabaseHelper.TABLE_USERS;
        database = dbHelper.getInstance(mContext).getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        Log.d("chavdar", "we got here");
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.KEY_ID));
                String username = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_USER_USERNAME));
                String eMail = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_USER_EMAIL));
                String password = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_USER_PASSWORD));

                User user = new User(id, username, eMail, password);
                allUsers.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return allUsers;
    }


    public User getCurrentUser() {
        return mCurrentUser;
    }

    public void setCurrentUser(User currentUser) {
        mCurrentUser = currentUser;
    }

    public void signOut() {
        mCurrentUser = null;
    }

    public boolean login(String username, String password) throws SQLException{
        open();

        Cursor mCursor = database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_USERS + " WHERE username=? AND password=?", new String[]{username,password});
        if (mCursor != null) {
            if(mCursor.getCount() > 0)
            {
                return true;
            }
        }

        mCursor.close();
        close();
        return false;
    }

    public boolean checkUserEmail(String email) {
        open();
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_USERS
                + " WHERE " + DatabaseHelper.KEY_USER_EMAIL + " = \"" + email + "\"";

        Cursor c = database.rawQuery(selectQuery, null);

        if (c != null && c.moveToFirst()) {
            close();
            c.close();
            return true;
        }
        else{
            close();
            c.close();
            return false;
        }
    }

    public boolean checkUsername(String username) {
        open();
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_USERS
                + " WHERE " + DatabaseHelper.KEY_USER_USERNAME + " = \"" + username + "\"";

        Cursor c = database.rawQuery(selectQuery, null);


        if (c != null && c.moveToFirst()) {
            close();
            c.close();
            return true;
        } else{
            close();
            c.close();
            return false;
        }
    }

//    public User getUser(String username) {
//
//        User saveUser = null;
//
//        for (User user : getAllUsers()) {
//            if (user.getEmail().equals(username)) {
//                saveUser = user;
//            }
//        }
//
//        return saveUser;
//    }

    //    public void getUserDeck(){
//
//    }

//    public long createUser(User user){
//        database = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(KEY_USER_USERNAME, user.getUsername());
//        values.put(KEY_USER_FIRST_NAME, user.getFirstName());
//        values.put(KEY_USER_LAST_NAME, user.getLastName());
//        values.put(KEY_USER_PASSWORD, user.getPassword());
//        values.put(KEY_USER_EMAIL, user.getEmail());
//
//        //insert row
//        long user_id = db.insert(TABLE_USERS, KEY_USER_USERNAME, values);
//
//        return user_id;
//    }
}
