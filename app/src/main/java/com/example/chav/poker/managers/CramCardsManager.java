package com.example.chav.poker.managers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.chav.poker.model_db.DatabaseHelper;

import model.CramCard;
import model.CramDeck;
import model.User;

/**
 * Created by Chav on 4/5/2016.
 */
public class CramCardsManager {

    public static CramCardsManager instance;

    public static CramCardsManager getInstance(Context context){
        if (instance == null) {
            instance = new CramCardsManager(context);
        }

        return instance;
    }


    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context mContext;

    private CramCardsManager(Context context) {
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

    public void addCard(CramDeck cramDeck, String title, String front, String back) {

    }
}
