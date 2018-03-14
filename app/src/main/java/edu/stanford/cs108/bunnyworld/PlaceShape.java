package edu.stanford.cs108.bunnyworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
        // Getting the name of the shape that got edit shape called on it
        String shapeName = getIntent().getStringExtra("shape");

        if (editing) {
            // if shape no longer exists, it won't do anything when you click finish but tell you the shape
            // no longer exists
            if (AllShapes.getInstance().getAllShapes().containsKey(shapeName)) {
                Intent intent = new Intent(this, EditShapeOptions.class);
                intent.putExtra("shape", shapeName);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "SHAPE NO LONGER EXISTS", Toast.LENGTH_SHORT).show();
            }

        } else {
            Intent intent = new Intent(this, NewPage.class);
            intent.putExtra("NEW_PAGE", false);
            intent.putExtra("pageName", page);
            startActivity(intent);
        }
    }

    public void set(View view) {
        EditText fontSize = (EditText) findViewById(R.id.fontSize);
        String fontSizeText = fontSize.getText().toString();

        if (!fontSizeText.isEmpty() && PlaceScreen.selectedShape != null) {
            if (AllShapes.getInstance().getAllShapes().containsKey(PlaceScreen.selectedShape.getName())) {
                int size = Integer.parseInt(fontSize.getText().toString());
                if (size > 0) {
                    PlaceScreen.selectedShape.setFontSize(size);
                    PlaceScreen screen = (PlaceScreen) findViewById(R.id.screen);
                    screen.refresh();
                }
            } else {
                Toast.makeText(getApplicationContext(), "SHAPE NO LONGER EXISTS", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
