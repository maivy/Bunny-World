package edu.stanford.cs108.bunnyworld;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class BunnyWorldDB {
    private static final Context CONTEXT = App.getContext();
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    private static final BunnyWorldDB BUNNY_WORLD_DB = new BunnyWorldDB();

    public static BunnyWorldDB getInstance() {
        return BUNNY_WORLD_DB;
    }

    private BunnyWorldDB() {
        databaseHelper = new DatabaseHelper(CONTEXT);
        database = databaseHelper.getWritableDatabase();
    }



    // Returns id of object in specified table
    private int getId(String tableName, String objName) {
        String getObjectQuery = "SELECT * FROM " + tableName + " WHERE name = '" + objName + "';";
        Cursor cursor = database.rawQuery(getObjectQuery,null);

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
     * Get custom images and preloaded images
     *
     */

    private static final BitmapDrawable CARROT = (BitmapDrawable) CONTEXT.getResources().getDrawable(R.drawable.carrot);
    private static final BitmapDrawable CARROT2 = (BitmapDrawable) CONTEXT.getResources().getDrawable(R.drawable.carrot2);
    private static final BitmapDrawable DEATH = (BitmapDrawable) CONTEXT.getResources().getDrawable(R.drawable.death);
    private static final BitmapDrawable DUCK = (BitmapDrawable) CONTEXT.getResources().getDrawable(R.drawable.duck);
    private static final BitmapDrawable FIRE = (BitmapDrawable) CONTEXT.getResources().getDrawable(R.drawable.fire);
    private static final BitmapDrawable MYSTIC = (BitmapDrawable) CONTEXT.getResources().getDrawable(R.drawable.mystic);

    int IMG_NAME_COL = 0;
    int IMG_COL = 1;

    /**
     * Source:
     * https://stackoverflow.com/questions/11790104/how-to-storebitmap-image-and-retrieve-image-from-sqlite-database-in-android
     * @param bitmap
     * @return
     */
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    /**
     * Source:
     * https://stackoverflow.com/questions/11790104/how-to-storebitmap-image-and-retrieve-image-from-sqlite-database-in-android
     * @param image
     * @return
     */
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static BitmapDrawable getBitmapDrawable(String drawingName) {
        BitmapDrawable drawable = null;
        if (drawingName.equals("carrot")) drawable = CARROT;
        else if (drawingName.equals("carrot2")) drawable = CARROT2;
        else if (drawingName.equals("death")) drawable = DEATH;
        else if (drawingName.equals("duck")) drawable = DUCK;
        else if (drawingName.equals("fire")) drawable = FIRE;
        else if (drawingName.equals("mystic")) drawable = MYSTIC;
        else {
            HashMap<String, BitmapDrawable> images = CustomImages.getInstance().getBitmapDrawables();
            if (images.containsKey(drawingName)) {
                drawable = images.get(drawingName);
            }
        }
        return drawable;
    }

    private HashMap<String, BitmapDrawable> getCustomBitmapDrawables() {
        HashMap<String, BitmapDrawable> bitmapDrawables = new HashMap<String,BitmapDrawable>();

        String query = "SELECT * FROM images;";
        Cursor cursor = database.rawQuery(query,null);

        if (cursor.getCount() == 0 || cursor == null) return bitmapDrawables;

        try {
            while(cursor.moveToNext()) {
                String imageName = cursor.getString(IMG_NAME_COL);
                byte [] imageInBytes = cursor.getBlob(IMG_COL);
                Bitmap imageBitmap = getImage(imageInBytes);
                BitmapDrawable imageDrawable = new BitmapDrawable(CONTEXT.getResources(),imageBitmap);
                bitmapDrawables.put(imageName,imageDrawable);
            }
        } catch (Exception e) {

        } finally {
            cursor.close();
        }

        return bitmapDrawables;
    }



    /**
     *
     * Add a new game, new pages, new shapes, and new custom images
     *
     */

    private long addGameToDB(String gameName) {
        ContentValues gameValues = new ContentValues();
        gameValues.put(DatabaseContract.Games.NAME,gameName);
        gameValues.put(DatabaseContract.Games.CURR_PAGE_NUMBER,AllPages.getInstance().getCurrPageNumber());
        gameValues.put(DatabaseContract.Games.CURR_SHAPE_NUMBER,AllShapes.getInstance().getCurrShapeNumber());
        long gameId = database.insert(DatabaseContract.Games.TABLE_NAME,null,gameValues);
        return gameId;
    }

    private HashMap<String, Long> addPagesToDB(long game_id) {
        HashMap<String, Page> allCurrPages = AllPages.getInstance().getAllPages();
        HashMap<String, Long> pageIds = new HashMap<String, Long>();
        for (Page page: allCurrPages.values()) {
            ContentValues pageValues = new ContentValues();
            pageValues.put(DatabaseContract.Pages.NAME,page.getPageName());
            pageValues.put(DatabaseContract.Pages.GAME_ID,game_id);
            pageValues.put(DatabaseContract.Pages.BACKGROUND_IMG,page.getBackgroundImageName());
            long pageId = database.insert(DatabaseContract.Pages.TABLE_NAME,null,pageValues);
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
            shapeValues.put(DatabaseContract.Shapes.X,shape.getX());
            shapeValues.put(DatabaseContract.Shapes.Y,shape.getY());
            shapeValues.put(DatabaseContract.Shapes.WIDTH,shape.getWidth());
            shapeValues.put(DatabaseContract.Shapes.HEIGHT,shape.getHeight());
            database.insert(DatabaseContract.Shapes.TABLE_NAME,null,shapeValues);
        }
    }

    public void addImageToDB(String name, BitmapDrawable image) {
        ContentValues imageValues = new ContentValues();
        imageValues.put(DatabaseContract.Images.IMG_NAME,name);
        imageValues.put(DatabaseContract.Images.IMG,getBytes(image.getBitmap()));
        database.insert(DatabaseContract.Images.TABLE_NAME,null,imageValues);
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
//        addImagesToDB();
    }



    /**
     *
     * Get pages from an existing game
     *
     */

    private static final int PAGE_NAME_COL = 1;
    private static final int PAGE_BACKGROUND_IMG_COL = 3;

    public HashMap<String, Page> getGamePages(long gameId) {
        HashMap<String, Page> pages = new HashMap<String,Page>();

        String query = "SELECT * FROM pages WHERE gameId = " + gameId + ";";
        Cursor cursor = database.rawQuery(query,null);

        while(cursor.moveToNext()) {
            String pageName = cursor.getString(PAGE_NAME_COL);
            String backgroundImageName = cursor.getString(PAGE_BACKGROUND_IMG_COL);
            // TODO: if constructor changes, this code will change
            Page page = new Page(pageName);
            page.setBackgroundImageName(backgroundImageName);
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
    private static final int SHAPE_X = 10;
    private static final int SHAPE_Y = 11;
    private static final int SHAPE_WIDTH = 12;
    private static final int SHAPE_HEIGHT = 13;

    private String findPageName(long pageId) {
        String findPageQuery = "SELECT * FROM pages WHERE _id = " + pageId + ";";
        Cursor findPageCursor = database.rawQuery(findPageQuery,null);
        findPageCursor.moveToFirst();
        String pageName = findPageCursor.getString(PAGE_NAME_COL);
        findPageCursor.close();
        return pageName;
    }

    private Shape getShapeFromCursor(Cursor cursor, String name) {
        long pageId = cursor.getInt(SHAPE_PAGE_ID_COL);
        String pageName = findPageName(pageId);
        String script = cursor.getString(SHAPE_SCRIPT_COL);
        String imageText = cursor.getString(SHAPE_IMAGE_COL);
        BitmapDrawable image = getBitmapDrawable(imageText);
        String text = cursor.getString(SHAPE_TEXT_COL);
        int fontSize = cursor.getInt(SHAPE_FONT_SIZE);
        boolean isHidden = cursor.getInt(SHAPE_IS_HIDDEN) > 0;
        boolean isMovable = cursor.getInt(SHAPE_IS_MOVABLE) > 0;
        float x = cursor.getFloat(SHAPE_X);
        float y = cursor.getFloat(SHAPE_Y);
        float width = cursor.getFloat(SHAPE_WIDTH);
        float height = cursor.getFloat(SHAPE_HEIGHT);
        return new Shape(pageName,name,x,y,width,height,isHidden,isMovable,imageText,image,text,script,fontSize);
    }

    public HashMap<String, Shape> getCurrShapes(long gameId) {
        HashMap<String, Shape> shapes = new HashMap<String, Shape>();

        String shapeQuery = "SELECT * FROM shapes WHERE gameId = " + gameId + ";";
        Cursor cursor = database.rawQuery(shapeQuery,null);
        while(cursor.moveToNext()) {
            String shapeName = cursor.getString(SHAPE_NAME_COL);
            shapes.put(shapeName,getShapeFromCursor(cursor,shapeName));
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
        Cursor cursor = database.rawQuery(query,null);
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
        allPages.nameGame(gameName);
        allPages.setCurrPageNumber(getCurrPageNumber(gameId));
        allPages.setAllCurrPages(getGamePages(gameId));
        AllShapes allShapes = AllShapes.getInstance();
        allShapes.setCurrShapeNumber(getCurrShapeNumber(gameId));
        allShapes.setCurrShapes(getCurrShapes(gameId));
    }

    public void loadCustomImages() {
        CustomImages.getInstance().setBitmapDrawables(getCustomBitmapDrawables());
    }



    /**
     * Deletes all pages and shapes associated with given game.
     * Also deletes all images to avoid duplicates.
     * Does nothing if given game does not exist.
     * @param gameName
     */
    public void removeGame(String gameName) {
        int gameId = getId("games", gameName);
        if (gameId == -1) return;

        // Delete game from games table
        String whereClause = "_id = " + gameId + ";";
        database.delete("games",whereClause,null);

        // Delete rows in pages table
        whereClause = "gameId = " + gameId + ";";
        database.delete("pages",whereClause,null);

        // Delete rows in shapes table
        database.delete("shapes",whereClause,null);

        // Delete all rows in images table
//        database.delete(DatabaseContract.Images.TABLE_NAME,null,null);
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



    /**
     *
     * Get current list of games
     *
     */

    private static final int GAME_NAME_COL = 1;

    public ArrayList<String> getGameNames() {
        ArrayList<String> gameNames = new ArrayList<String>();

        String getGamesQuery = "SELECT * FROM games;";
        Cursor gameCursor = database.rawQuery(getGamesQuery,null);

        while(gameCursor.moveToNext()) {
            String gameName = gameCursor.getString(GAME_NAME_COL);
            gameNames.add(gameName);
        }
        gameCursor.close();

        return gameNames;
    }

}
