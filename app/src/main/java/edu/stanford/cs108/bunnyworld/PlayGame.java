package edu.stanford.cs108.bunnyworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PlayGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game2);
    }

    public void resetGame(View view) {
        BunnyWorldDB.getInstance().loadGame(AllPages.getInstance().getGameName());
        Intent intent = new Intent(this, PlayGame.class);
        startActivity(intent);
        finish();
    }
}
