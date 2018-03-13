package edu.stanford.cs108.bunnyworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class EditShapeOptions extends AppCompatActivity {
    private String shapeName;
    private String currPageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shape_options);
        Intent intent = this.getIntent();
        shapeName = intent.getStringExtra("shape");
        TextView currShapeText = findViewById(R.id.currPageEditOptions);
        currShapeText.setText(shapeName);
        HashMap<String, Shape> currShapes = AllShapes.getInstance().getAllShapes();
        Shape currShape = currShapes.get(shapeName);
        currPageName = currShape.getAssociatedPage();
    }

    /**
     * Deletes the current shape.
     * @param view
     */
    public void deleteShape(View view) {
        HashMap<String, Shape> currShapes = AllShapes.getInstance().getAllShapes();
        Shape currShape = currShapes.get(shapeName);
        currShapes.remove(shapeName);
        Intent intent = new Intent(this, ViewAllShapes.class);
        intent.putExtra("Page", currShape.getAssociatedPage());
        startActivity(intent);
    }

    /**
     * Goes to the activity to edit the shape's script.
     * @param view
     */
    public void deleteOrAddToScript(View view) {
        Intent intent = new Intent(getApplicationContext(), EditScript.class);
        intent.putExtra("shape", shapeName);
        startActivity(intent);
    }

    /**
     * Goes to the activity that edits all the parts of the shape except the script.
     * @param view
     */
    public void editTheShape(View view) {
        Intent intent = new Intent(getApplicationContext(), EditShape.class);
        intent.putExtra("shape", shapeName);
        startActivity(intent);
    }

    public void returnToAllShapes(View view) {
        Intent intent = new Intent(this, ViewAllShapes.class);
        intent.putExtra("Page", currPageName);
        startActivity(intent);
    }
}
