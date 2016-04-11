package com.example.chav.poker.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.chav.poker.model_db.DatabaseHelper;

import java.util.ArrayList;

import model.CramCard;
import model.CramDeck;
import model.User;

/**
 * Created by Chav on 4/5/2016.
 */
public class CramCardsManager {

    public static CramCardsManager instance;

    public static CramCardsManager getInstance(Context context) {
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

    public void addCard(long cramDeckId, CramCard cramCard) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_CRAM_CARD_DECK_ID, cramDeckId);
        values.put(DatabaseHelper.KEY_CRAM_CARD_FRONT, cramCard.getQuestion());
        values.put(DatabaseHelper.KEY_CRAM_CARD_BACK, cramCard.getAnswer());

        open();
        long cramCardId = database.insert(DatabaseHelper.TABLE_CRAM_CARDS, DatabaseHelper.KEY_CRAM_CARD_FRONT, values);
        cramCard.setId(cramCardId);
        close();
    }

    public ArrayList<CramCard> getDeckCards(long decId) {
        ArrayList<CramCard> deckCards = new ArrayList<>();
        open();
        String selectKey = "SELECT *FROM " + DatabaseHelper.TABLE_CRAM_CARDS + " WHERE " + DatabaseHelper.KEY_CRAM_CARD_DECK_ID + " = " + decId;
        Cursor cursor = database.rawQuery(selectKey, null);
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.KEY_ID));
                String question = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_CRAM_CARD_FRONT));
                String answer = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_CRAM_CARD_BACK));
                CramCard cramCard = new CramCard(id, question, answer);
                deckCards.add(cramCard);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();

        return deckCards;

    }

    public void removeCard(long cramCardID) {
        open();
        database.delete(DatabaseHelper.TABLE_CRAM_CARDS, DatabaseHelper.KEY_ID + " = ?", new String[]{Long.toString(cramCardID)});
        close();
    }

    public void removeAllDeckCard(long cramDeckId) {
        open();
        database.delete(DatabaseHelper.TABLE_CRAM_CARDS, DatabaseHelper.KEY_CRAM_CARD_DECK_ID + " = ?", new String[]{Long.toString(cramDeckId)});
        close();
    }
}
