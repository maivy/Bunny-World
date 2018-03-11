package edu.stanford.cs108.bunnyworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class GameToPlay extends AppCompatActivity {
    private static final BunnyWorldDB BUNNY_WORLD_DB = BunnyWorldDB.getInstance();
    private Spinner imageSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_to_play);
        ArrayList<String> allPossGames = BUNNY_WORLD_DB.getGameNames();
        imageSpinner = findViewById(R.id.possibleGamesNames);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item, allPossGames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        imageSpinner.setAdapter(adapter);
    }

    public void playThisGame(View view) {
        String gameName = imageSpinner.getSelectedItem().toString();
        BUNNY_WORLD_DB.loadGame(gameName);
        Intent intent = new Intent(this, PlayGame.class);
        startActivity(intent);
    }
}
