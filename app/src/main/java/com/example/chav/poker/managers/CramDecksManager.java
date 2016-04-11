package com.example.chav.poker.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.chav.poker.model_db.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import model.CramDeck;
import model.User;

/**
 * Created by Chav on 4/5/2016.
 */
public class CramDecksManager {

    public static CramDecksManager instance;

    public static CramDecksManager getInstance(Context context) {
        if (instance == null) {
            instance = new CramDecksManager(context);
        }

        return instance;
    }


    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context mContext;

    private CramDecksManager(Context context) {
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

    public void addDeck(User user, CramDeck cramDeck) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_CRAM_DECK_TITLE, cramDeck.getTitle());
        values.put(DatabaseHelper.KEY_CRAM_DECK_USER_ID, user.getId());

        open();
        long cramDeckId = database.insert(DatabaseHelper.TABLE_CRAM_DECK, DatabaseHelper.KEY_CRAM_DECK_TITLE, values);
        cramDeck.setId(cramDeckId);
        close();

    }

    public ArrayList<CramDeck> getUsersCramDecks(long userId) {
        ArrayList<CramDeck> usersDecks = new ArrayList<>();
//        dbHelper.getReadableDatabase();
        open(); //TODO check if it works
        String selectKey = "SELECT *FROM " + DatabaseHelper.TABLE_CRAM_DECK + " WHERE " + DatabaseHelper.KEY_CRAM_DECK_USER_ID + " = " + userId;
        Cursor cursor = database.rawQuery(selectKey, null);
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.KEY_ID));
                String title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_CRAM_DECK_TITLE));

                CramDeck cramDeck = new CramDeck(id, title);
                usersDecks.add(cramDeck);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();

        return usersDecks;
    }

    public void removeDeck(long cramDeckId) {
        open();
        database.delete(DatabaseHelper.TABLE_CRAM_DECK, DatabaseHelper.KEY_ID + " = ?", new String[]{Long.toString(cramDeckId)});
        close();
    }
}
