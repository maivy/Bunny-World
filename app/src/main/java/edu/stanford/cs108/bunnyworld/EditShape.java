package edu.stanford.cs108.bunnyworld;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class EditShape extends AppCompatActivity {
    private String currShapeName;
    private final ArrayList<String> allImages = new ArrayList<>(Arrays.asList("", "carrot", "carrot2", "death", "duck",
            "fire", "mystic"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shape);
        Intent intent = this.getIntent();
        currShapeName = intent.getStringExtra("shape");
        init();
    }


    private void init () {
        Shape currShape = AllShapes.getInstance().getAllShapes().get(currShapeName);

        //displays the name of the current page
        TextView currentPage = findViewById(R.id.currPageEditShape);
        currentPage.setText(currShape.getAssociatedPage());

        EditText shapeNameBox = findViewById(R.id.currentShapeNameEdit);
        shapeNameBox.setText(currShapeName);

        //startX
        EditText xLoc = findViewById(R.id.startXEdit);
        xLoc.setText(Float.toString(currShape.getX()));

        //startY
        EditText yLoc = findViewById(R.id.startYEdit);
        yLoc.setText(Float.toString(currShape.getY()));

        //Width
        EditText shapeWidth = findViewById(R.id.shapeWidthEdit);
        shapeWidth.setText(Float.toString(currShape.getWidth()));

        //Height
        EditText shapeHeight = findViewById(R.id.shapeHeightEdit);
        shapeHeight.setText(Float.toString(currShape.getHeight()));

        //Hidden
        if (currShape.isHidden()) {
            RadioButton hideYes = findViewById(R.id.hiddenYesEdit);
            hideYes.setChecked(true);
        } else {
            RadioButton hideNo = findViewById(R.id.hiddenNoEdit);
            hideNo.setChecked(true);
        }

        // Moveable
        if(currShape.isMovable()) {
            RadioButton yesMove = findViewById(R.id.moveableYesEdit);
            yesMove.setChecked(true);
        } else {
            RadioButton noMove = findViewById(R.id.moveableNoEdit);
            noMove.setChecked(true);
        }

        //Image
        String imgName = currShape.imageName;
        Spinner imageSpinner = findViewById(R.id.imageNameSpinEdit);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item,allImages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        imageSpinner.setAdapter(adapter);
        imageSpinner.setSelection(allImages.indexOf(imgName));


        // text
        EditText textInput = findViewById(R.id.textStringEdit);
        textInput.setText(currShape.getText());

        // text Size
        EditText textSizeInput = findViewById(R.id.textSizeEdit);
        textSizeInput.setText(Integer.toString(currShape.getFontSize()));
    }

    public void updateTheShape(View view) {
        HashMap<String, Shape> currShapes = AllShapes.getInstance().getAllShapes();
        Shape currShape = currShapes.get(currShapeName);
        EditText nameBox = findViewById(R.id.currentShapeNameEdit);
        String newName = nameBox.getText().toString();
        currShape.setName(newName);


        if (currShapeName.equals(newName) || (!currShapeName.equals(newName) && !currShapes.containsKey(newName))) {

            //startX
            EditText xLoc = findViewById(R.id.startXEdit);
            String xString = xLoc.getText().toString();
            float x = 0f;
            if (!xString.isEmpty()) x = Float.parseFloat(xString);
            currShape.setX(x);

            //startY
            EditText yLoc = findViewById(R.id.startYEdit);
            String yString = yLoc.getText().toString();
            float y = 0f;
            if (!yString.isEmpty()) y = Float.parseFloat(yString);
            currShape.setY(y);

            //Width
            EditText shapeWidth = findViewById(R.id.shapeWidthEdit);
            String widthString = shapeWidth.getText().toString();
            float width = 0f;
            if (!widthString.isEmpty()) width = Float.parseFloat(widthString);
            currShape.setWidth(width);

            //Height
            EditText shapeHeight = findViewById(R.id.shapeHeightEdit);
            String heightString = shapeHeight.getText().toString();
            float height = 0f;
            if (!heightString.isEmpty()) height = Float.parseFloat(heightString);
            currShape.setHeight(height);

            //Hidden
            RadioButton hideYes = findViewById(R.id.hiddenYesEdit);
            boolean hidden = hideYes.isChecked();
            currShape.setHidden(hidden);

            // Moveable
            RadioButton yesMove = findViewById(R.id.moveableYesEdit);
            boolean moveable = yesMove.isChecked();
            currShape.setMovable(moveable);

            //Image
            Spinner imageSpinner = findViewById(R.id.imageNameSpinEdit);
            String imageName = imageSpinner.getSelectedItem().toString();
            currShape.imageName = imageName;

            // text
            EditText textInput = findViewById(R.id.textStringEdit);
            String textString = textInput.getText().toString();
            currShape.setText(textString);

            // text Size
            EditText textSizeInput = findViewById(R.id.textSizeEdit);
            String textSizeString = textSizeInput.getText().toString();
            int textSize = 0;
            if (!textSizeString.isEmpty()) textSize = Integer.parseInt(textSizeString);
            currShape.fontSize = textSize;

            currShapes.remove(currShapeName);
            currShapes.put(newName, currShape);
            currShapeName = newName;

            Toast.makeText(getApplicationContext(), "SHAPE UPDATED", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), EditShapeOptions.class);
            intent.putExtra("shape", currShapeName);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "INVALID NAME", Toast.LENGTH_SHORT).show();
        }
    }
}