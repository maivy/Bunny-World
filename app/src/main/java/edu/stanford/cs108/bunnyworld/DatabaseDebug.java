package edu.stanford.cs108.bunnyworld;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

public class DatabaseDebug extends AppCompatActivity {
    DatabaseHelper bWDBHelper;
    SQLiteDatabase bWDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_debug);
//        this.deleteDatabase("BunnyWorldDB.db");
        bWDBHelper = new DatabaseHelper(getApplicationContext());
        bWDB = bWDBHelper.getWritableDatabase();

//        String addPages = "INSERT INTO pages VALUES " +
//                "(NULL,'page1',1)," +
//                "(NULL,'page2',1)," +
//                "(NULL,'page3',1)," +
//                "(NULL,'page4',2)" +
//                ";";
//
//        String addGames = "INSERT INTO games VALUES " +
//                "(NULL,'game1')," +
//                "(NULL,'game2')" +
//                ";";
//
//        bWDB.execSQL(addPages);
//        bWDB.execSQL(addGames);

//        String addShapes = "INSERT INTO shapes VALUES " +
//                "(NULL,'shape1',1,'on click hide;','carrot','text',10,'000',1,1,0,10.0,20.0,100.0,200.0)," +
//                "(NULL,'shape2',1,'on click hide;','carrot','text',10,'000',1,1,0,10.0,20.0,100.0,200.0)," +
//                "(NULL,'shape3',2,'on click hide;','carrot','text',10,'000',1,1,0,10.0,20.0,100.0,200.0)" +
//                ";";
//
//        bWDB.execSQL(addShapes);

//        HashMap<String, Page> pages = getPages("game2");
//
//        for (Map.Entry<String,Page> page_entry: pages.entrySet()) {
//            System.out.println(page_entry.getKey());
//            Page page = page_entry.getValue();
//            System.out.println(page.getPageName());
//        }
//
//        HashMap<String, Shape> shapes = getShapes("page1");
//
//        for (Map.Entry<String,Shape> shape_entry: shapes.entrySet()) {
//            System.out.println(shape_entry.getKey());
//            Shape shape = shape_entry.getValue();
//            System.out.println(shape.getAssociatedPage());
//            System.out.println(shape.isHidden());
//        }

//        addCurrentGame("game3");
        removeGame("game1");
    }

    /**
     * Returns id of specified object or -1 if not found.
     * @param table_name
     * @param obj_name
     * @return
     */
    private int getId(String table_name, String obj_name) {
        String getObjectQuery = "SELECT * FROM " + table_name + " WHERE name = '" + obj_name + "';";
        Cursor cursor = bWDB.rawQuery(getObjectQuery,null);

        if (cursor.getCount() == 0) return -1;

        // assuming that there is only one table entry with specified name
        cursor.moveToFirst();
        int obj_id_col = cursor.getColumnIndexOrThrow("_id");
        int obj_id = cursor.getInt(obj_id_col);

        return obj_id;
    }

    // TODO: how to keep track of counter for shape name

    /**
     * Returns a hash map of all pages associated with specified
     * game name or null.
     * @param game_name
     * @return
     */
    public HashMap<String, Page> getPages(String game_name) {
        HashMap<String, Page> pages = new HashMap<String,Page>();

        int game_id = getId("games", game_name);
        if (game_id == -1) return null;

        String query = "SELECT * FROM pages WHERE game_id = " + game_id + ";";
        Cursor cursor = bWDB.rawQuery(query,null);

        while(cursor.moveToNext()) {
            int page_name_col = cursor.getColumnIndexOrThrow(DatabaseContract.Pages.NAME);
            String page_name = cursor.getString(page_name_col);
            Page page = new Page(page_name);
            pages.put(page_name,page);
        }
        cursor.close();

        return pages;
    }

    // TODO: how to keep track of counter for page name

    public HashMap<String, Shape> getShapes(String page_name) {
        HashMap<String, Shape> shapes = new HashMap<String, Shape>();

        int page_id = getId("pages",page_name);
        if (page_id == -1) return null;

        String shape_query = "SELECT * FROM shapes WHERE page_id = " + page_id + ";";
        System.out.println(page_id);

        Cursor cursor = bWDB.rawQuery(shape_query,null);

        int name_col = cursor.getColumnIndexOrThrow(DatabaseContract.Shapes.NAME);
        int script_col = cursor.getColumnIndex(DatabaseContract.Shapes.SCRIPT);
        int image_id_col = cursor.getColumnIndex(DatabaseContract.Shapes.IMAGE); // TODO how are we storing images?
        int text_col = cursor.getColumnIndex(DatabaseContract.Shapes.TEXT);
        int font_size_col = cursor.getColumnIndex(DatabaseContract.Shapes.FONT_SIZE);
        int text_color_col = cursor.getColumnIndex(DatabaseContract.Shapes.TEXT_COLOR);
        int is_hidden_col = cursor.getColumnIndex(DatabaseContract.Shapes.IS_HIDDEN);
        int is_movable_col = cursor.getColumnIndex(DatabaseContract.Shapes.IS_MOVABLE);
        int is_receiving_col = cursor.getColumnIndex(DatabaseContract.Shapes.IS_RECEIVING);
        int x_col = cursor.getColumnIndex(DatabaseContract.Shapes.X);
        int y_col = cursor.getColumnIndex(DatabaseContract.Shapes.Y);
        int width_col = cursor.getColumnIndex(DatabaseContract.Shapes.WIDTH);
        int height_col = cursor.getColumnIndex(DatabaseContract.Shapes.HEIGHT);

        while(cursor.moveToNext()) {
            String name = cursor.getString(name_col);
            String script = cursor.getString(script_col);
            String image = cursor.getString(image_id_col);
            String text = cursor.getString(text_col);
            int font_size = cursor.getInt(font_size_col);
            System.out.println(text_color_col);
            String text_color = cursor.getString(text_color_col); // TODO no text-color in constructor?
            boolean is_hidden = cursor.getInt(is_hidden_col) > 0;
            boolean is_movable = cursor.getInt(is_movable_col) > 0;
            boolean is_receiving = cursor.getInt(is_receiving_col) > 0;
            float x = cursor.getFloat(x_col);
            float y = cursor.getFloat(y_col);
            float width = cursor.getFloat(width_col);
            float height = cursor.getFloat(height_col);

            Shape shape = new Shape(page_name,name,x,y,width,height,is_hidden,is_movable,image,null,text,script,font_size);
            shapes.put(name,shape);
        }
        cursor.close();

        return shapes;
    }

    private void addCurrentGame(String game_name) {
        // Add a new game
        ContentValues game_values = new ContentValues();
        game_values.put(DatabaseContract.Games.NAME,game_name);
        long game_id = bWDB.insert("games",null,game_values);

        HashMap<String, Page> allCurrPages = AllPages.getInstance().getAllPages();
        HashMap<String, Shape>  allCurrShapes = AllShapes.getInstance().getAllShapes();

        // Dummy data for debug
        allCurrPages.put("page1",new Page("page1"));
        allCurrPages.put("page2",new Page("page2"));

        allCurrShapes.put("shape1", new Shape("page1","shape1",1,2,3,4,true,false,"carrot",null,"text","script",12));
        allCurrShapes.put("shape2", new Shape("page2","shape2",1,2,3,4,false,true,"bunny",null,"text","script",12));
        allCurrShapes.put("shape3", new Shape("page1","shape3",1,2,3,4,true,false,"apple",null,"text","script",12));
        allCurrShapes.put("shape4", new Shape("page2","shape4",1,2,3,4,false,false,"banana",null,"text","script",12));

        HashMap<String, Long> page_ids = new HashMap<String, Long>();

        for (Page page: allCurrPages.values()) {
            ContentValues page_values = new ContentValues();
            page_values.put(DatabaseContract.Pages.NAME,page.getPageName());
            page_values.put(DatabaseContract.Pages.GAME_ID,game_id);
            long page_id = bWDB.insert("pages",null,page_values);
            page_ids.put(page.getPageName(),page_id);
        }

        for (Shape shape: allCurrShapes.values()) {
            ContentValues shape_values = new ContentValues();
            shape_values.put(DatabaseContract.Shapes.NAME,shape.getName());
            shape_values.put(DatabaseContract.Shapes.PAGE_ID,page_ids.get(shape.getAssociatedPage()));
            shape_values.put(DatabaseContract.Shapes.SCRIPT,shape.getScript());
            shape_values.put(DatabaseContract.Shapes.IMAGE,shape.getImageName());
            shape_values.put(DatabaseContract.Shapes.TEXT,shape.getText());
            shape_values.put(DatabaseContract.Shapes.FONT_SIZE,shape.getFontSize());
            shape_values.put(DatabaseContract.Shapes.TEXT_COLOR,"color"); // TODO does shape include a field for the text color?
            shape_values.put(DatabaseContract.Shapes.IS_HIDDEN,shape.isHidden());
            shape_values.put(DatabaseContract.Shapes.IS_MOVABLE,shape.isMovable());
            shape_values.put(DatabaseContract.Shapes.IS_RECEIVING,shape.isReceiving());
            shape_values.put(DatabaseContract.Shapes.X,shape.getX());
            shape_values.put(DatabaseContract.Shapes.Y,shape.getY());
            shape_values.put(DatabaseContract.Shapes.WIDTH,shape.getWidth());
            shape_values.put(DatabaseContract.Shapes.HEIGHT,shape.getHeight());
            bWDB.insert("shapes",null,shape_values);
        }
    }

    private void removeGame(String game_name) {
        int game_id = getId("games", game_name);
        if (game_id == -1) return;

        // Delete game from games table
        String whereClause = "name = '" + game_name + "'";
        bWDB.delete("games",whereClause,null);



//        HashMap<String, Page>

        // Delete rows in pages table
        whereClause = "game_id = " + game_id + ";";
        bWDB.delete("shapes",whereClause,null);

        // Delete rows in shapes table

    }
}
