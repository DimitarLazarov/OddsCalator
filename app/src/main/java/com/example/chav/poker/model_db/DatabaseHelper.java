package com.example.chav.poker.model_db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Chav on 4/5/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    //Database name and version
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "OddsCalatorDatabase";

    //Tables names
    public static final String TABLE_USERS = "Users";
    public static final String TABLE_CRAM_CARDS = "CramCards";
    public static final String TABLE_CRAM_DECK = "CramDeck";

    //Common column names
    public static final String KEY_ID = "_id";


    //USERS table column names
    public static final String KEY_USER_USERNAME = "Username";
    public static final String KEY_USER_EMAIL = "Email";
    public static final String KEY_USER_PASSWORD = "Password";

    //CRAM_CARDS table column names
    public static final String KEY_CRAM_CARD_FRONT = "Question";
    public static final String KEY_CRAM_CARD_BACK = "Answer";
    public static final String KEY_CRAM_CARD_DECK_ID = "Deck";

    //CRAM_DECK table column names
    public static final String KEY_CRAM_DECK_TITLE = "Title";
    public static final String KEY_CRAM_DECK_USER_ID = "Creator";

    //Table create statements
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + KEY_USER_USERNAME + " VARCHAR(20) NOT NULL UNIQUE, "
            + KEY_USER_EMAIL + " TEXT UNIQUE, "
            + KEY_USER_PASSWORD + " TEXT" + ")";

    private static final String CREATE_TABLE_CRAM_CARDS = "CREATE TABLE " + TABLE_CRAM_CARDS + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + KEY_CRAM_CARD_FRONT + " VARCHAR(100) NOT NULL, "
            + KEY_CRAM_CARD_BACK + " VARCHAR(100) NOT NULL, "
            + KEY_CRAM_CARD_DECK_ID + " INTEGER" + " REFERENCES " + TABLE_CRAM_DECK + "("+KEY_ID+")" + ")";


    private static final String CREATE_TABLE_CRAM_DECK = "CREATE TABLE " + TABLE_CRAM_DECK + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + KEY_CRAM_DECK_TITLE + " TEXT NOT NULL, "
            + KEY_CRAM_DECK_USER_ID + " INTEGER REFERENCES " + TABLE_USERS + "("+KEY_ID+")" + ")";
//            + " FOREIGN KEY (" + KEY_CRAM_DECK_USER_ID + ") REFERENCES " + TABLE_USERS + "("+KEY_ID+")" + ")";

    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    private Context context;


    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_CRAM_DECK);
        db.execSQL(CREATE_TABLE_CRAM_CARDS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CRAM_CARDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CRAM_DECK);

        onCreate(db);
    }

}
