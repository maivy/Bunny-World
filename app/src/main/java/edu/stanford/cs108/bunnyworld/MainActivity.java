package edu.stanford.cs108.bunnyworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void createClicked(View view) {
        Intent intent = new Intent(this, NameNewGame.class);
        startActivity(intent);
    }

    public void editClicked(View view) {
        Intent intent = new Intent(this, GameToEdit.class);
        startActivity(intent);
    }

    public void playGame(View view) {
        Intent intent = new Intent(this, GameToPlay.class);
        startActivity(intent);
    }
}
