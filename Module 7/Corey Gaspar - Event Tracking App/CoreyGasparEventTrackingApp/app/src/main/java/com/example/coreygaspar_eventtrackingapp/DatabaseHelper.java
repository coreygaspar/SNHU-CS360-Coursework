package com.example.coreygaspar_eventtrackingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "AppDB.db";
    public static final int DB_VERSION = 2;

    // database setup
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // create tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE users (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "username TEXT UNIQUE," +
                        "password TEXT)"
        );

        db.execSQL(
                "CREATE TABLE events (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT," +
                        "description TEXT," +
                        "date TEXT," +
                        "time TEXT)"
        );
    }

    // upgrade database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS events");
        onCreate(db);
    }

    // insert event
    public boolean insertEvent(String name, String description, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("description", description);
        cv.put("date", date);
        cv.put("time", time);

        return db.insert("events", null, cv) != -1;
    }

    // get events
    public Cursor getAllEvents() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM events", null);
    }

    // delete event
    public void deleteEvent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("events", "id=?", new String[]{String.valueOf(id)});
    }

    // update event
    public void updateEvent(int id, String name, String description, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("description", description);
        cv.put("date", date);
        cv.put("time", time);

        db.update("events", cv, "id=?", new String[]{String.valueOf(id)});
    }
}