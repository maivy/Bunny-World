package edu.stanford.cs108.bunnyworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class NewGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
    }

    public void createNewPage(View view) {
        Intent intent = new Intent(this, NewPage.class);
        intent.putExtra("NEW_PAGE", true);
        startActivity(intent);
    }

    public void finishCreatingGame(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        // TODO : anything necessary to finish building the game
    }

    // Displays all the pages currently available
    public void displayAllPages(View view) {
        ListView currPagesView = findViewById(R.id.pagesInNewGame);
        HashMap<String, Page> currPages = AllPages.getInstance().getAllPages();
        ArrayList<String> allPageNames  = new ArrayList<>();
        for (String key : currPages.keySet()) {
            allPageNames.add(key);
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, allPageNames);
        currPagesView.setAdapter(adapter);
        currPagesView.setVisibility(View.VISIBLE);
        findViewById(R.id.pagesInNewGame).setVisibility(View.VISIBLE);
    }

    public void saveGame(View view) {
        //TODO: save game on android
    }

    public void goToEdit(View view) {
        Intent intent = new Intent(this, EditOptions.class);
        startActivity(intent);
    }
}
