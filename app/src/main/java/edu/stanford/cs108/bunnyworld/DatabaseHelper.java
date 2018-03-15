package edu.stanford.cs108.bunnyworld;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Creates the database
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BunnyWorldDB.db";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

//    private static final String TYPE_INT_PRIMARY_KEY = "INTEGER PRIMARY KEY AUTOINCREMENT";
//    private static final String TYPE_INTEGER = "INTEGER";
//    private static final String TYPE_TEXT = "TEXT";
//    private static final String TYPE_FLOAT = "FLOAT";
//    private static final String TYPE_BOOL = "BOOL";

    private static final String SQL_CREATE_GAMES =
            "CREATE TABLE " + DatabaseContract.Games.TABLE_NAME + " (" +
                    "" + DatabaseContract.Games.GAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "" + DatabaseContract.Games.NAME + " TEXT, " +
                    "" + DatabaseContract.Games.CURR_PAGE_NUMBER + " INTEGER, " +
                    "" + DatabaseContract.Games.CURR_SHAPE_NUMBER + " INTEGER" +
                    ");";

    private static final String SQL_CREATE_PAGES =
            "CREATE TABLE " + DatabaseContract.Pages.TABLE_NAME + " (" +
                    "" + DatabaseContract.Pages.PAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "" + DatabaseContract.Pages.NAME + " TEXT, " +
                    "" + DatabaseContract.Pages.GAME_ID + " INTEGER" +
                    ");";

    private static final String SQL_CREATE_SHAPES =
            "CREATE TABLE " + DatabaseContract.Shapes.TABLE_NAME + " (" +
                    "" + DatabaseContract.Shapes.SHAPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "" + DatabaseContract.Shapes.NAME + " TEXT, " +
                    "" + DatabaseContract.Shapes.PAGE_ID + " INTEGER, " +
                    "" + DatabaseContract.Shapes.GAME_ID + " INTEGER, " +
                    "" + DatabaseContract.Shapes.SCRIPT + " TEXT, " +
                    "" + DatabaseContract.Shapes.IMAGE + " TEXT, " +
                    "" + DatabaseContract.Shapes.TEXT + " TEXT, " +
                    "" + DatabaseContract.Shapes.FONT_SIZE + " INTEGER, " +
                    "" + DatabaseContract.Shapes.IS_HIDDEN + " BOOL, " +
                    "" + DatabaseContract.Shapes.IS_MOVABLE + " BOOL, " +
                    "" + DatabaseContract.Shapes.IS_RECEIVING+ " BOOL, " +
                    "" + DatabaseContract.Shapes.X + " FLOAT, " +
                    "" + DatabaseContract.Shapes.Y + " FLOAT, " +
                    "" + DatabaseContract.Shapes.WIDTH + " FLOAT, " +
                    "" + DatabaseContract.Shapes.HEIGHT + " FLOAT" +
                    ");";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_GAMES);
        db.execSQL(SQL_CREATE_PAGES);
        db.execSQL(SQL_CREATE_SHAPES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO update database
    }
}
