package edu.stanford.cs108.bunnyworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GameToPlay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_to_play);
    }

    public void playThisGame(View view) {
        // TODO: Get the arraylist containing the list of all the games made
        // ArrayList<String> allPossGames = ;

        //        Spinner imageSpinner = findViewById(R.id.possibleGamesNames);
        //        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
        //                android.R.layout.simple_spinner_item, allPossGames);
        //        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //        imageSpinner.setAdapter(adapter);

        // TODO: upload all the information for the given game name
        Intent intent = new Intent(this, PlayGame.class);
        startActivity(intent);
    }
}
