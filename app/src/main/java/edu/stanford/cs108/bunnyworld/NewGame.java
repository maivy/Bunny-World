package edu.stanford.cs108.bunnyworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class NewGame extends AppCompatActivity {
    private static final String NO_GAME = "NO GAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
    }

    private boolean gameIsEmpty() {
        return AllPages.getInstance().getGameName().equals(NO_GAME);
    }

    /**
     * Adds a new page to the game.
     * @param view
     */
    public void createNewPage(View view) {
        if (gameIsEmpty()) {
            Toast.makeText(getApplicationContext(), "GAME NO LONGER EXISTS", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, NewPage.class);
        intent.putExtra("NEW_PAGE", true);
        startActivity(intent);
    }

    public void saveGame(View view) {
        if (gameIsEmpty()) {
            Toast.makeText(getApplicationContext(), "GAME NO LONGER EXISTS", Toast.LENGTH_SHORT).show();
            return;
        }
        BunnyWorldDB bunnyWorldDB = BunnyWorldDB.getInstance();
        ArrayList<String> gameNames = bunnyWorldDB.getGameNames();
        String gameName = AllPages.getInstance().getGameName();
        if (!gameNames.contains(gameName)) bunnyWorldDB.addCurrentGame(gameName);
        else bunnyWorldDB.updateGame(gameName);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void deleteGame(View view) {
        if (gameIsEmpty()) {
            Toast.makeText(getApplicationContext(), "GAME NO LONGER EXISTS", Toast.LENGTH_SHORT).show();
            return;
        }
        AllPages.getInstance().clearAllPages();
        AllShapes.getInstance().clearAllShapes();
        BunnyWorldDB.getInstance().removeGame(AllPages.getInstance().getGameName());
        AllPages.getInstance().nameGame(NO_GAME);
        Toast.makeText(getApplicationContext(), "DELETED GAME FROM DATABASE", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, GameToEdit.class);
        startActivity(intent);
    }

    /**
     * Goes to the activity that allows the user to see all the pages in the game and edit them.
     * @param view
     */
    public void goToEdit(View view) {
        if (gameIsEmpty()) {
            Toast.makeText(getApplicationContext(), "GAME NO LONGER EXISTS", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, EditOptions.class);
        startActivity(intent);
    }
}
