package edu.stanford.cs108.bunnyworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NameNewGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_new_game);
    }

    public void createNewGame(View view) {
        // saves the Game Name given by the user to the singleton for future use
        EditText newGameName = findViewById(R.id.newGameName);
        String gameNameString = newGameName.getText().toString();
        AllPages.getInstance().nameGame(gameNameString);

        Intent intent = new Intent(this, NewGame.class);
        startActivity(intent);
    }
}
