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
                    "" + DatabaseContract.Pages.GAME_ID + " INTEGER, " +
                    "" + DatabaseContract.Pages.BACKGROUND_IMG + " TEXT" +
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
                    "" + DatabaseContract.Shapes.X + " FLOAT, " +
                    "" + DatabaseContract.Shapes.Y + " FLOAT, " +
                    "" + DatabaseContract.Shapes.WIDTH + " FLOAT, " +
                    "" + DatabaseContract.Shapes.HEIGHT + " FLOAT" +
                    ");";

    private static final String SQL_CREATE_IMAGES =
            "CREATE TABLE " + DatabaseContract.Images.TABLE_NAME + " (" +
                    "" + DatabaseContract.Images.IMG_NAME + " TEXT, " +
                    "" + DatabaseContract.Images.IMG + " BLOB" +
                    ");";

    private static final String PRELOAD_GAME = "INSERT INTO games VALUES(NULL,'preloadedBunnyWorld',6,22);";

    private static final String PRELOAD_PAGES = "INSERT INTO pages VALUES " +
            "(NULL,'fireroom',1)," +
            "(NULL,'page1',1)," +
            "(NULL,'mysticbunny',1)," +
            "(NULL,'deathbunny',1)," +
            "(NULL,'wingame',1)" +
            ";";

    private static final String PRELOAD_SHAPES = "INSERT INTO shapes VALUES " +
            "(NULL,'doorbacktomain',3,1,'on click goto page1;','No Image','',0,0,0,0,140.14038,931.5625,141.09863,132.03125)," +
            "(NULL,'win-carrot2',5,1,'','carrot','',0,0,0,0,128.21265,121.23047,252.0,300.0)," +
            "(NULL,'doortodeathbunny',2,1,'on click goto deathbunny;','No Image','',0,0,0,0,762.6703,889.0332,197.16125,192.60742)," +
            "(NULL,'page1text',2,1,'','No Image','You are in a maze of twisty little\npassages, all alike',45,0,0,0,37.971924,664.39453,0.0,0.0)," +
            "(NULL,'shape15',5,1,'on enter play hooray;','No Image','You Win! Yay!',100,0,0,0,238.97339,893.5547,0.0,0.0)," +
            "(NULL,'escapedoor',1,1,'on click goto mysticbunny;','No Image','',0,0,0,0,287.02728,869.4478,122.56653,130.245)," +
            "(NULL,'doortomysticbunny',2,1,'on click goto mysticbunny;','No Image','',0,0,0,0,88.576965,892.041,197.1283,191.11328)," +
            "(NULL,'shape12',5,1,'','carrot','',0,0,0,0,413.47266,410.83008,252.0,300.0)," +
            "(NULL,'shape11',4,1,'','No Image','You must appease the bunny of death!',60,0,0,0,16.974121,861.5039,0.0,0.0)," +
            "(NULL,'exit',4,1,'on click goto wingame;','No Image','',0,1,0,0,861.28357,940.5957,161.10474,152.01172)," +
            "(NULL,'door2',2,1,'on click goto fireroom;','No Image','',0,1,0,0,422.7565,888.0078,202.77203,193.63281)," +
            "(NULL,'evilbunny',4,1,'on enter play evillaugh; on drop firecarrot hide firecarrot play munching hide evilbunny show exit;on click play evillaugh;','death','',0,0,0,0,233.38586,70.72266,654.22925,643.125)," +
            "(NULL,'shape8',1,1,'','No Image','Eek! Fire-Room. Run away!',50,0,0,0,48.801514,817.5,0.0,0.0)," +
            "(NULL,'wincarrot3',5,1,'','carrot','',0,0,0,0,745.76514,159.25781,252.0,300.0)," +
            "(NULL,'firecarrot',1,1,'','carrot','',0,0,1,0,718.7388,743.5547,252.0,300.0)," +
            "(NULL,'mystictext',3,1,'','No Image','Mystic Bunny! Rub my tummy for a big surprise!',50,0,0,0,18.971924,749.47266,252.0,300.0)," +
            "(NULL,'fire',1,1,'on enter play fire;','fire','',0,0,0,0,74.95941,61.453125,905.93555,601.66406)," +
            "(NULL,'mysticshape',3,1,'on click hide firecarrot play munching;on enter show door2;','mystic','',0,0,0,0,312.97192,102.75,459.0,567.0)," +
            "(NULL,'bunnyworldsign',2,1,'','No Image','Bunny World!',100,0,0,0,29.27295,116.13281,0.0,0.0)" +
            ";";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_GAMES);
        db.execSQL(SQL_CREATE_PAGES);
        db.execSQL(SQL_CREATE_SHAPES);
        db.execSQL(SQL_CREATE_IMAGES);
        db.execSQL(PRELOAD_GAME);
        db.execSQL(PRELOAD_PAGES);
        db.execSQL(PRELOAD_SHAPES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        
    }
}
