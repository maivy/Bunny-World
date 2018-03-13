package edu.stanford.cs108.bunnyworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class PlaceShape extends AppCompatActivity {

    public static String page;
    boolean editing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        page = intent.getStringExtra("pageName");
        editing = intent.getBooleanExtra("editing", false);
        setContentView(R.layout.activity_place_shape);
        System.out.println("place shape curr page: " + page);
    }

    public void finish(View view) {
        if(editing) {
            Intent intent = new Intent(this, EditShapeOptions.class);
            String shapeName = getIntent().getStringExtra("shape");
            intent.putExtra("shape", shapeName);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, NewPage.class);
            intent.putExtra("NEW_PAGE", false);
            intent.putExtra("pageName", page);
            startActivity(intent);
        }
    }

    public void set(View view) {
        EditText fontSize = (EditText) findViewById(R.id.fontSize);
        if(!fontSize.equals("") && PlaceScreen.selectedShape != null){
            int size = Integer.parseInt(fontSize.getText().toString());
            if(size > 0) {
                PlaceScreen.selectedShape.setFontSize(size);
                PlaceScreen screen = (PlaceScreen) findViewById(R.id.screen);
                screen.refresh();
            }
        }
    }
}
