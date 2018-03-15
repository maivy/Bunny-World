package edu.stanford.cs108.bunnyworld;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * For debugging purposes only
 */

public class DatabaseDebug extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_debug);
        this.deleteDatabase("BunnyWorldDB.db"); // reset database

/**
 * Instantiate BunnyWorldDB first then use the below code to add values into database
 */
//        String addPages = "INSERT INTO pages VALUES " +
//                "(NULL,'page1',1)," +
//                "(NULL,'page2',1)," +
//                "(NULL,'page3',1)," +
//                "(NULL,'page4',2)" +
//                ";";
//
//        String addGames = "INSERT INTO games VALUES " +
//                "(NULL,'game1',4,4)," +
//                "(NULL,'game2',2,1)" +
//                ";";
//
//        String addShapes = "INSERT INTO shapes VALUES " +
//                "(NULL,'shape1',1,1,'on click hide;','carrot','text',10,1,1,0,10.0,20.0,100.0,200.0)," +
//                "(NULL,'shape2',2,2,'on click hide;','carrot','text',10,1,1,0,10.0,20.0,100.0,200.0)," +
//                "(NULL,'shape3',3,1,'on click hide;','carrot','text',10,1,1,0,10.0,20.0,100.0,200.0)" +
//                ";";
//
//        bunnyWorldDB.execSQL(addPages);
//        bunnyWorldDB.execSQL(addGames);
//        bunnyWorldDB.execSQL(addShapes);

//        HashMap<String, Page> allCurrPages = AllPages.getInstance().getAllPages();
//        HashMap<String, Shape> allCurrShapes = AllShapes.getInstance().getAllShapes();
//
//        // Dummy data for debug
//        allCurrPages.put("page1", new Page("page1"));
//        allCurrPages.put("page2", new Page("page2"));
//
//        allCurrShapes.put("shape1", new Shape("page1", "shape1", 1, 2, 3, 4, true, false, "carrot", null, "text", "script", 12));
//        allCurrShapes.put("shape2", new Shape("page2", "shape2", 1, 2, 3, 4, false, true, "death", null, "text", "script", 12));
//        allCurrShapes.put("shape3", new Shape("page1", "shape3", 1, 2, 3, 4, true, false, "duck", null, "text", "script", 12));
//        allCurrShapes.put("shape4", new Shape("page2", "shape4", 1, 2, 3, 4, false, false, "fire", null, "text", "script", 12));
//
//        BunnyWorldDB bunnyWorldDB = BunnyWorldDB.getInstance();
//
//        // add new game to database
//        bunnyWorldDB.addCurrentGame("game1");
//        bunnyWorldDB.addCurrentGame("game3");
//
//        // edit game in app
//        allCurrPages.put("page3", new Page("page3"));
//        allCurrShapes.remove("shape1");
//        allCurrShapes.remove("shape4");
//
//        ArrayList<String> games = bunnyWorldDB.getGameNames();
//        System.out.println(games);
//
//        // want to add new game, but and existing game already has same name
//        if (!games.contains("game1")) {
//            System.out.println("GAME ADDED");
//            bunnyWorldDB.addCurrentGame("game1");
//        } else {
//            System.out.println("GAME ALREADY EXISTS, RENAME THEN ADD");
//            bunnyWorldDB.addCurrentGame("game2");
//        }
//
//        // get game from database
//        bunnyWorldDB.loadGame("game1");
//        printCurrPages();
//        printCurrShapes();
//
//        // get another game from database
//        bunnyWorldDB.loadGame("game2");
//        printCurrPages();
//        printCurrShapes();
//
//        // removes a game from database, tries to load it
//        bunnyWorldDB.removeGame("game1");
//        bunnyWorldDB.loadGame("game1"); // game does not exist, nothing happens
//        printCurrPages();
//        printCurrShapes();
//
//        //edit an existing game
//        bunnyWorldDB.loadGame("game3");
//
//        allCurrPages = AllPages.getInstance().getAllPages(); // adding new data
//        allCurrShapes = AllShapes.getInstance().getAllShapes();
//        allCurrPages.put("page3", new Page("page3"));
//        allCurrPages.put("page4", new Page("page4"));
//        allCurrShapes.remove("shape2");
//        allCurrShapes.remove("shape3");
//
//        bunnyWorldDB.updateGame("game3");
//        bunnyWorldDB.loadGame("game2"); // load a different game
//        bunnyWorldDB.loadGame("game3"); // check if previous game was updated
//        printCurrPages();
//        printCurrShapes();
    }

    private void printCurrShapes() {
        HashMap<String,Shape> allCurrShapes = AllShapes.getInstance().getAllShapes();
        System.out.printf("CurrShapeNumber: %d\n",AllShapes.getInstance().getCurrShapeNumber());
        for (Shape shape: allCurrShapes.values()) {
            System.out.printf("Name: %s Page: %s Script: %s ImageName: %s ", shape.getName(), shape.getAssociatedPage(), shape.getScript(), shape.getImageName());
            System.out.printf("Image: %s Text: %s FontSize: %d isHidden: %b ",shape.getImage().toString(), shape.getText(), shape.getFontSize(), shape.isHidden());
            System.out.printf("isMovable: %b isReceiving: %b x: %f y: %f ", shape.isMovable(), false, shape.getX(), shape.getY());
            System.out.printf("width: %f height: %f\n", shape.getWidth(), shape.getHeight());
        }
    }

    private void printCurrPages() {
        System.out.printf("CurrPageNumber: %d\n",AllPages.getInstance().getCurrPageNumber());
        HashMap<String,Page> allCurrPages = AllPages.getInstance().getAllPages();
        for (Page page : allCurrPages.values()) {
            System.out.printf("Name: %s\n", page.getPageName());
        }
    }
}
