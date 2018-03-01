package edu.stanford.cs108.bunnyworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NewGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
    }

    /**
     * Adds a new page to the game.
     * @param view
     */
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

    public void saveGame(View view) {
        //TODO: save game on android
    }

    /**
     * Goes to the activity that allows the user to see all the pages in the game and edit them.
     * @param view
     */
    public void goToEdit(View view) {
        Intent intent = new Intent(this, EditOptions.class);
        startActivity(intent);
    }
}
