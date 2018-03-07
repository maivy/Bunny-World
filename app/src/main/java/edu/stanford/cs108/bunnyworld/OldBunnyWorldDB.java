package edu.stanford.cs108.bunnyworld;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;

public class BunnyWorldDB extends AppCompatActivity {
    DatabaseHelper bWDBHelper;
    SQLiteDatabase bWDB;

    public BunnyWorldDB() {
        bWDBHelper = new DatabaseHelper(getApplicationContext());
        bWDB = bWDBHelper.getWritableDatabase();

        String addData = "INSERT INTO pages VALUES " +
                "(NULL,'page1',1)" +
                ";";

        bWDB.execSQL(addData);
    }

}
