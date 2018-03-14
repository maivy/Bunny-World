package edu.stanford.cs108.bunnyworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Iterator;

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
     * @param
     */
    private void deleteShape() {
        if (AllShapes.getInstance().getAllShapes().containsKey(shapeName)) {
            HashMap<String, Shape> currShapes = AllShapes.getInstance().getAllShapes();
            Shape currShape = currShapes.get(shapeName);
            currShapes.remove(shapeName);
            Intent intent = new Intent(this, ViewAllShapes.class);
            intent.putExtra("Page", currShape.getAssociatedPage());
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "SHAPE NO LONGER EXISTS", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Goes to the activity to edit the shape's script.
     * @param view
     */
    public void deleteOrAddToScript(View view) {
        if (AllShapes.getInstance().getAllShapes().containsKey(shapeName)) {
            Intent intent = new Intent(getApplicationContext(), EditScript.class);
            intent.putExtra("shape", shapeName);
            intent.putExtra("editing", true);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "SHAPE NO LONGER EXISTS", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Goes to the activity that edits all the parts of the shape except the script.
     * @param view
     */
    public void editTheShape(View view) {
        if (AllShapes.getInstance().getAllShapes().containsKey(shapeName)) {
            Intent intent = new Intent(getApplicationContext(), EditShape.class);
            intent.putExtra("shape", shapeName);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "SHAPE NO LONGER EXISTS", Toast.LENGTH_SHORT).show();
        }
    }

    public void returnToAllShapes(View view) {
        Intent intent = new Intent(this, ViewAllShapes.class);
        intent.putExtra("Page", currPageName);
        startActivity(intent);
    }

    /**
     * Goes through all the shapes' scripts and checks if they are using the
     * shape to be deleting.
     */
    public void checkIfShapeUsed(View view) {
        if (AllShapes.getInstance().getAllShapes().containsKey(shapeName)) {
            final HashMap<String, Shape> allShapes = AllShapes.getInstance().getAllShapes();
            Iterator<String> it = allShapes.keySet().iterator();

            boolean shapeUsed = false;
            while (it.hasNext()) {
                String currShapeName = it.next();
                Shape currShape = allShapes.get(currShapeName);
                String currScript  = currShape.getScript();
                if (currScript.contains(shapeName)) {
                    giveUserWarning();
                    shapeUsed = true;
                    break;
                }
            }
            if (!shapeUsed) deleteShape();
        } else {
            Toast.makeText(getApplicationContext(), "SHAPE NO LONGER EXISTS", Toast.LENGTH_SHORT).show();
        }
    }


    private void giveUserWarning() {
        Button  editShape = findViewById(R.id.editShapeButton);
        editShape.setVisibility(View.GONE);
        Button editScript = findViewById(R.id.editScriptButton);
        editScript.setVisibility(View.GONE);
        Button deleteShape = findViewById(R.id.deleteShapeButton);
        deleteShape.setVisibility(View.GONE);
        Button returnButton = findViewById(R.id.returnButton);
        returnButton.setVisibility(View.GONE);

        Button yes = findViewById(R.id.yesButton);
        yes.setVisibility(View.VISIBLE);
        Button no = findViewById(R.id.noButton);
        no.setVisibility(View.VISIBLE);
        TextView warning = findViewById(R.id.warningText);
        warning.setVisibility(View.VISIBLE);
        TextView question = findViewById(R.id.questionText);
        question.setVisibility(View.VISIBLE);
    }

    public void continueToDelete(View view) {
        deleteShape();
    }

    public void cancelDelete(View view) {
        Button  editShape = findViewById(R.id.editShapeButton);
        editShape.setVisibility(View.VISIBLE);
        Button editScript = findViewById(R.id.editScriptButton);
        editScript.setVisibility(View.VISIBLE);
        Button deleteShape = findViewById(R.id.deleteShapeButton);
        deleteShape.setVisibility(View.VISIBLE);
        Button returnButton = findViewById(R.id.returnButton);
        returnButton.setVisibility(View.VISIBLE);

        Button yes = findViewById(R.id.yesButton);
        yes.setVisibility(View.GONE);
        Button no = findViewById(R.id.noButton);
        no.setVisibility(View.GONE);
        TextView warning = findViewById(R.id.warningText);
        warning.setVisibility(View.GONE);
        TextView question = findViewById(R.id.questionText);
        question.setVisibility(View.GONE);
    }
}
