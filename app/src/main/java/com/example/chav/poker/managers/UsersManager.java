package com.example.chav.poker.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.UserManager;
import android.util.Log;

import com.example.chav.poker.model_db.DatabaseHelper;

import model.User;

/**
 * Created by Chav on 4/5/2016.
 */
public class UsersManager {

    public static UsersManager instance;

    public static UsersManager getInstance(Context context){
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
        database
        long user_id = db.insert(TABLE_USERS, KEY_USER_USERNAME, values);
        this.mCurrentUser = mUserDAO.addUser(user);


        mAllUsers.add(user);
        Log.d("user", "" + user.getId());
        Log.d("user", "" + user.getUsername());
        mIsSignedIn = true;
    }

    public long createUser(User user){
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_USERNAME, user.getUsername());
        values.put(KEY_USER_FIRST_NAME, user.getFirstName());
        values.put(KEY_USER_LAST_NAME, user.getLastName());
        values.put(KEY_USER_PASSWORD, user.getPassword());
        values.put(KEY_USER_EMAIL, user.getEmail());

        //insert row
        long user_id = db.insert(TABLE_USERS, KEY_USER_USERNAME, values);

        return user_id;
    }
}
