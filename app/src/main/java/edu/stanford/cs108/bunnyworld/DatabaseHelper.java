package edu.stanford.cs108.bunnyworld;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BunnyWorldDB.db";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    private static final String SQL_CREATE_GAMES =
            "CREATE TABLE games (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT" +
                    ");";

    private static final String SQL_CREATE_PAGES =
            "CREATE TABLE pages (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, " +
                    "game_id INTEGER" +
                    ");";

    private static final String SQL_CREATE_SHAPES =
            "CREATE TABLE shapes (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, " +
                    "page_id INTEGER, " +
                    "script TEXT, " +
                    "image TEXT, " +
                    "text TEXT, " +
                    "font_size INTEGER, " +
                    "text_color TEXT, " +
                    "is_hidden BOOL, " +
                    "is_movable BOOL, " +
                    "is_receiving BOOL, " +
                    "x FLOAT, " +
                    "y FLOAT, " +
                    "width FLOAT, " +
                    "height FLOAT " +
                    ");";

//    private static final String SQL_CREATE_IMAGES =
//            "CREATE TABLE images (" +
//                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    "file_name TEXT" +
//                    ");";

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println(SQL_CREATE_GAMES);
        db.execSQL(SQL_CREATE_GAMES);
        db.execSQL(SQL_CREATE_PAGES);
        db.execSQL(SQL_CREATE_SHAPES);
//        db.execSQL(SQL_CREATE_IMAGES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO update database
    }
}
