package edu.stanford.cs108.bunnyworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class GameToEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_to_edit);
    }

    public void openGame(View view) {
        // TODO: Get the arraylist containing the list of all the games made
        // ArrayList<String> allPossGames = ;

//        Spinner imageSpinner = findViewById(R.id.possibleGames);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
//                android.R.layout.simple_spinner_item, allPossGames);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        imageSpinner.setAdapter(adapter);

        // TODO: upload all the information for the given game name
        Intent intent = new Intent(this, NewGame.class);
        startActivity(intent);
    }
}
