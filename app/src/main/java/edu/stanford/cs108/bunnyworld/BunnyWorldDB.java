package edu.stanford.cs108.bunnyworld;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;

import java.util.ArrayList;
import java.util.HashMap;

public class BunnyWorldDB {
    private static final Context CONTEXT = App.getContext();
    private DatabaseHelper bWDBHelper;
    private SQLiteDatabase bWDB;

    private static final BunnyWorldDB BUNNY_WORLD_DB = new BunnyWorldDB();

    public static BunnyWorldDB getInstance() {
        return BUNNY_WORLD_DB;
    }

    private BunnyWorldDB() {
        bWDBHelper = new DatabaseHelper(CONTEXT);
        bWDB = bWDBHelper.getWritableDatabase();
    }

    private static final BitmapDrawable CARROT = (BitmapDrawable) CONTEXT.getResources().getDrawable(R.drawable.carrot);
    private static final BitmapDrawable CARROT2 = (BitmapDrawable) CONTEXT.getResources().getDrawable(R.drawable.carrot2);
    private static final BitmapDrawable DEATH = (BitmapDrawable) CONTEXT.getResources().getDrawable(R.drawable.death);
    private static final BitmapDrawable DUCK = (BitmapDrawable) CONTEXT.getResources().getDrawable(R.drawable.duck);
    private static final BitmapDrawable FIRE = (BitmapDrawable) CONTEXT.getResources().getDrawable(R.drawable.fire);
    private static final BitmapDrawable MYSTIC = (BitmapDrawable) CONTEXT.getResources().getDrawable(R.drawable.mystic);

    // Returns id of object in specified table
    private int getId(String tableName, String objName) {
        String getObjectQuery = "SELECT * FROM " + tableName + " WHERE name = '" + objName + "';";
        Cursor cursor = bWDB.rawQuery(getObjectQuery,null);

        if (cursor.getCount() == 0) return -1;

        // assuming that there is only one table entry with specified name
        cursor.moveToFirst();
        int objIdCol = cursor.getColumnIndexOrThrow("_id");
        int objId = cursor.getInt(objIdCol);
        cursor.close();

        return objId;
    }

    /**
     *
     * Add a new game, new pages, and new shapes
     *
     */

    private long addGameToDB(String gameName) {
        ContentValues gameValues = new ContentValues();
        gameValues.put(DatabaseContract.Games.NAME,gameName);
        gameValues.put(DatabaseContract.Games.CURR_PAGE_NUMBER,AllPages.getInstance().getCurrPageNumber());
        gameValues.put(DatabaseContract.Games.CURR_SHAPE_NUMBER,AllShapes.getInstance().getCurrShapeNumber());
        long gameId = bWDB.insert("games",null,gameValues);
        return gameId;
    }

    private HashMap<String, Long> addPagesToDB(long game_id) {
        HashMap<String, Page> allCurrPages = AllPages.getInstance().getAllPages();
        HashMap<String, Long> pageIds = new HashMap<String, Long>();
        for (Page page: allCurrPages.values()) {
            ContentValues pageValues = new ContentValues();
            pageValues.put(DatabaseContract.Pages.NAME,page.getPageName());
            pageValues.put(DatabaseContract.Pages.GAME_ID,game_id);
            long pageId = bWDB.insert("pages",null,pageValues);
            pageIds.put(page.getPageName(),pageId);
        }
        return pageIds;
    }

    private void addShapesToDB(long gameId, HashMap<String, Long> pageIds) {
        HashMap<String,Shape> allCurrShapes = AllShapes.getInstance().getAllShapes();
        for (Shape shape: allCurrShapes.values()) {
            ContentValues shapeValues = new ContentValues();
            shapeValues.put(DatabaseContract.Shapes.NAME,shape.getName());
            shapeValues.put(DatabaseContract.Shapes.PAGE_ID,pageIds.get(shape.getAssociatedPage()));
            shapeValues.put(DatabaseContract.Shapes.GAME_ID,gameId);
            shapeValues.put(DatabaseContract.Shapes.SCRIPT,shape.getScript());
            shapeValues.put(DatabaseContract.Shapes.IMAGE,shape.getImageName());
            shapeValues.put(DatabaseContract.Shapes.TEXT,shape.getText());
            shapeValues.put(DatabaseContract.Shapes.FONT_SIZE,shape.getFontSize());
            shapeValues.put(DatabaseContract.Shapes.IS_HIDDEN,shape.isHidden());
            shapeValues.put(DatabaseContract.Shapes.IS_MOVABLE,shape.isMovable());
            shapeValues.put(DatabaseContract.Shapes.IS_RECEIVING,shape.isReceiving());
            shapeValues.put(DatabaseContract.Shapes.X,shape.getX());
            shapeValues.put(DatabaseContract.Shapes.Y,shape.getY());
            shapeValues.put(DatabaseContract.Shapes.WIDTH,shape.getWidth());
            shapeValues.put(DatabaseContract.Shapes.HEIGHT,shape.getHeight());
            bWDB.insert("shapes",null,shapeValues);
        }
    }

    /**
     * Creates a new game with current pages and shapes
     * from singletons
     * @param gameName
     */
    public void addCurrentGame(String gameName) {
        long gameId = addGameToDB(gameName);
        HashMap<String, Long> pageIds = addPagesToDB(gameId);
        addShapesToDB(gameId,pageIds);
    }

    /**
     *
     * Get pages from an existing game
     *
     */

    private static final int PAGE_NAME_COL = 1;

    public HashMap<String, Page> getGamePages(long gameId) {
        HashMap<String, Page> pages = new HashMap<String,Page>();

        String query = "SELECT * FROM pages WHERE gameId = " + gameId + ";";
        Cursor cursor = bWDB.rawQuery(query,null);

        while(cursor.moveToNext()) {
            String pageName = cursor.getString(PAGE_NAME_COL);
            Page page = new Page(pageName);
            pages.put(pageName,page);
        }
        cursor.close();

        return pages;
    }

    /**
     *
     * Get shapes from an existing game
     *
     */

    private static final int SHAPE_NAME_COL = 1;
    private static final int SHAPE_PAGE_ID_COL = 2;
    private static final int SHAPE_SCRIPT_COL = 4;
    private static final int SHAPE_IMAGE_COL = 5;
    private static final int SHAPE_TEXT_COL = 6;
    private static final int SHAPE_FONT_SIZE = 7;
    private static final int SHAPE_IS_HIDDEN = 8;
    private static final int SHAPE_IS_MOVABLE = 9;
    private static final int SHAPE_X = 11;
    private static final int SHAPE_Y = 12;
    private static final int SHAPE_WIDTH = 13;
    private static final int SHAPE_HEIGHT = 14;

    private String findPageName(long pageId) {
        String findPageQuery = "SELECT * FROM pages WHERE _id = " + pageId + ";";
        Cursor findPageCursor = bWDB.rawQuery(findPageQuery,null);
        findPageCursor.moveToFirst();
        String pageName = findPageCursor.getString(PAGE_NAME_COL);
        findPageCursor.close();
        return pageName;
    }

    private BitmapDrawable getBitmapDrawable(String drawingName) {
        BitmapDrawable drawable = null;
        if (drawingName.equals("carrot")) drawable = CARROT;
        else if (drawingName.equals("carrot2")) drawable = CARROT2;
        else if (drawingName.equals("death")) drawable = DEATH;
        else if (drawingName.equals("duck")) drawable = DUCK;
        else if (drawingName.equals("fire")) drawable = FIRE;
        else if (drawingName.equals("mystic")) drawable = MYSTIC;
        return drawable;
    }

    private Shape getShapeFromCursor(Cursor cursor, String name) {
        long pageId = cursor.getInt(SHAPE_PAGE_ID_COL);
        String pageName = findPageName(pageId);
        String script = cursor.getString(SHAPE_SCRIPT_COL);
        String imageText = cursor.getString(SHAPE_IMAGE_COL);
        BitmapDrawable image = getBitmapDrawable(imageText);
//        if (image == null) System.out.println("IMAGE NOT FOUND");
//        else System.out.println(image);
        String text = cursor.getString(SHAPE_TEXT_COL);
        int fontSize = cursor.getInt(SHAPE_FONT_SIZE);
        boolean isHidden = cursor.getInt(SHAPE_IS_HIDDEN) > 0;
        boolean isMovable = cursor.getInt(SHAPE_IS_MOVABLE) > 0;
        float x = cursor.getFloat(SHAPE_X);
        float y = cursor.getFloat(SHAPE_Y);
        float width = cursor.getFloat(SHAPE_WIDTH);
        float height = cursor.getFloat(SHAPE_HEIGHT);
//        Shape shape = new Shape(pageName,name,x,y,width,height,isHidden,isMovable,imageText,image,text,script,fontSize);
//        System.out.println("Image: " + shape.getImage());
//        return shape;
        return new Shape(pageName,name,x,y,width,height,isHidden,isMovable,imageText,image,text,script,fontSize);
    }

    public HashMap<String, Shape> getCurrShapes(long gameId) {
        HashMap<String, Shape> shapes = new HashMap<String, Shape>();

        String shapeQuery = "SELECT * FROM shapes WHERE gameId = " + gameId + ";";
        Cursor cursor = bWDB.rawQuery(shapeQuery,null);
        while(cursor.moveToNext()) {
            String shapeName = cursor.getString(SHAPE_NAME_COL);
            Shape shape = getShapeFromCursor(cursor,shapeName);
//            System.out.println("Image: " + shape.getImage());
            shapes.put(shapeName,shape);
//            shapes.put(shapeName,getShapeFromCursor(cursor,shapeName));
        }
        cursor.close();

        return shapes;
    }

    /**
     *
     * Get current shape number and current page number
     * of an existing game
     *
     */

    private Cursor getGameCursor(long gameId) {
        String query = "SELECT * FROM games WHERE _id = " + gameId + ";";
        Cursor cursor = bWDB.rawQuery(query,null);
        cursor.moveToFirst();
        return cursor;
    }

    private static final int CURR_PAGE_NUMBER_COL = 2;
    private static final int CURR_SHAPE_NUMBER_COL = 3;

    private int getCurrPageNumber(long gameId) {
        Cursor cursor = getGameCursor(gameId);
        int currPageNumber = cursor.getInt(CURR_PAGE_NUMBER_COL);
        cursor.close();
        return currPageNumber;
    }

    private int getCurrShapeNumber(long gameId) {
        Cursor cursor = getGameCursor(gameId);
        int currShapeNumber = cursor.getInt(CURR_SHAPE_NUMBER_COL);
        cursor.close();
        return currShapeNumber;
    }


    /**
     * Sets all pages and shapes singletons to data
     * of specified game. Nothing happens if game does
     * not exist.
     * @param gameName
     */
    public void loadGame(String gameName) {
        long gameId = getId("games", gameName);
        if (gameId == -1) return;
        AllPages allPages = AllPages.getInstance();
        allPages.setCurrPageNumber(getCurrPageNumber(gameId));
        allPages.setAllCurrPages(getGamePages(gameId));
        AllShapes allShapes = AllShapes.getInstance();
        allShapes.setCurrShapeNumber(getCurrShapeNumber(gameId));
        allShapes.setCurrShapes(getCurrShapes(gameId));
    }

    /**
     *
     * Get current list of games
     *
     */

    /**
     * Deletes all pages and shapes associated with given game.
     * Does nothing if given game does not exist.
     * @param gameName
     */
    public void removeGame(String gameName) {
        int gameId = getId("games", gameName);
        if (gameId == -1) return;

        // Delete game from games table
        String whereClause = "_id = " + gameId + ";";
        bWDB.delete("games",whereClause,null);

        // Delete rows in pages table
        whereClause = "gameId = " + gameId + ";";
        bWDB.delete("pages",whereClause,null);

        // Delete rows in shapes table
        bWDB.delete("shapes",whereClause,null);
    }

    /**
     * Deletes old version of game and adds current game data.
     * Be sure to load game first and then update. Nothing happens
     * if game does not exist.
     * @param gameName
     */
    public void updateGame(String gameName) {
        int gameId = getId("games", gameName);
        if (gameId == -1) return;
        removeGame(gameName);
        addCurrentGame(gameName);
    }

    private static final int GAME_NAME_COL = 1;

    public ArrayList<String> getGameNames() {
        ArrayList<String> gameNames = new ArrayList<String>();

        String getGamesQuery = "SELECT * FROM games;";
        Cursor gameCursor = bWDB.rawQuery(getGamesQuery,null);

        while(gameCursor.moveToNext()) {
            String gameName = gameCursor.getString(GAME_NAME_COL);
            gameNames.add(gameName);
        }
        gameCursor.close();

        return gameNames;
    }
}
