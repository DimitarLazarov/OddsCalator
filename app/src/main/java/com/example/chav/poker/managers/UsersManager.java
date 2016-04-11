package com.example.chav.poker.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.chav.poker.model_db.DatabaseHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    private static String md5(String password) {
        try {

            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context mContext;
    private User mCurrentUser;

    private UsersManager(Context context) {
        mContext = context;
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public void open() {
        // Allow database to be in writable mode
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null)
            database.close();
    }

    public void registerUser(User user) {
        String gryptPass = md5(user.getPassword());
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_USER_USERNAME, user.getUsername());
        values.put(DatabaseHelper.KEY_USER_PASSWORD, gryptPass);
        values.put(DatabaseHelper.KEY_USER_EMAIL, user.getEmail());

        open();
        long userId = database.insert(DatabaseHelper.TABLE_USERS, DatabaseHelper.KEY_USER_USERNAME, values);
        user.setId(userId);
        close();

        this.mCurrentUser = user;

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
        String cryptPass = md5(password);
        Cursor mCursor = database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_USERS + " WHERE username=? AND password=?", new String[]{username,cryptPass});
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

}
