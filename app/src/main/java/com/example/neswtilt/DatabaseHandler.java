package com.example.neswtilt;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "highscoreDatabase";
    private static final String TABLE_HIGHSCORE = "highscore";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_HIGHSCORE = "highscore";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_HIGHSCORE_TABLE = "CREATE TABLE " + TABLE_HIGHSCORE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_HIGHSCORE + " TEXT" + ")";
        db.execSQL(CREATE_HIGHSCORE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIGHSCORE);

        // Create tables again
        onCreate(db);
    }

    public void emptyHighscore() {
        // Drop older table if existed
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIGHSCORE);

        // Create tables again
        onCreate(db);
    }
    // code to add the new contact
   void addHighscore(HighScoreClass highscore) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, highscore.getName()); // Contact Name
        values.put(KEY_HIGHSCORE, highscore.getHighscore()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_HIGHSCORE, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single contact
    HighScoreClass getHighscoreClass(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_HIGHSCORE, new String[] { KEY_ID,
                        KEY_NAME, KEY_HIGHSCORE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        HighScoreClass highscore = new HighScoreClass(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), Integer.parseInt(cursor.getString(2)));
        // return contact
        return highscore;
    }
    public List<HighScoreClass> top5Highscore()
    {
        List<HighScoreClass> topFiveHighscoreList = new ArrayList<HighScoreClass>();
        // Select All Query

        String selectFilterQuery = "SELECT id, name, highscore FROM " + TABLE_HIGHSCORE + " ORDER BY CAST(highscore as INTEGER) DESC LIMIT 5";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectFilterQuery, null);


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                HighScoreClass highscore = new HighScoreClass();
                highscore.setID(Integer.parseInt(cursor.getString(0)));
                highscore.setName(cursor.getString(1));
                highscore.setHighscore(Integer.parseInt(cursor.getString(2)));
                // Adding contact to list
                topFiveHighscoreList.add(highscore);
            } while (cursor.moveToNext());
        }

        return topFiveHighscoreList;
    }


    // code to get all contacts in a list view
    public List<HighScoreClass> getAllHighscore() {
        List<HighScoreClass> highscoreList = new ArrayList();


        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_HIGHSCORE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HighScoreClass highscore = new HighScoreClass();
                highscore.setID(Integer.parseInt(cursor.getString(0)));
                highscore.setName(cursor.getString(1));
                highscore.setHighscore(Integer.parseInt(cursor.getString(2)));
                // Adding contact to list
                highscoreList.add(highscore);
            } while (cursor.moveToNext());
        }


        return highscoreList;
    }

    // code to update the single contact
    public int updateHighscore(HighScoreClass highscore) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, highscore.getName());
        values.put(KEY_HIGHSCORE, highscore.getHighscore());

        // updating row
        return db.update(TABLE_HIGHSCORE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(highscore.getID()) });
    }


    public void deleteHighscore(HighScoreClass highscore) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HIGHSCORE, KEY_ID + " = ?",
                new String[] { String.valueOf(highscore.getID()) });
        db.close();
    }


    public int getHighscoreCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_HIGHSCORE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }
}