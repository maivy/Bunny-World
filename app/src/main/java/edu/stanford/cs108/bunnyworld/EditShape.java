package edu.stanford.cs108.bunnyworld;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    private static final String NO_IMG = "No Image";
    private static final String COPY = "copy";
    private static final String UPDATE = "update";

    private final ArrayList<String> allImages = new ArrayList<>(Arrays.asList(NO_IMG, "carrot", "carrot2", "death", "duck",
            "fire", "mystic"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shape);
        Intent intent = this.getIntent();
        currShapeName = intent.getStringExtra("shape");
        init();
    }


    private void fillInShapeFields(Shape currShape) {
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
        imageSpinner.setSelection(allImages.indexOf(imgName));

        // text
        EditText textInput = findViewById(R.id.textStringEdit);
        textInput.setText(currShape.getText());

        // text Size
        EditText textSizeInput = findViewById(R.id.textSizeEdit);
        textSizeInput.setText(Integer.toString(currShape.getFontSize()));
    }



    private void init () {
        if (AllShapes.getInstance().getCopiedShape() == null) {
            Button pasteButton = findViewById(R.id.pasteShapeEdit);
            pasteButton.setVisibility(View.INVISIBLE);
        }

        Shape currShape = AllShapes.getInstance().getAllShapes().get(currShapeName);
        fillInShapeFields(currShape);

        //displays the name of the current page
        TextView currentPage = findViewById(R.id.currPageEditShape);
        currentPage.setText(currShape.getAssociatedPage());

        EditText shapeNameBox = findViewById(R.id.currentShapeNameEdit);
        shapeNameBox.setText(currShapeName);

        String imgName = currShape.imageName;
        final Spinner imageSpinner = findViewById(R.id.imageNameSpinEdit);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item,allImages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        imageSpinner.setAdapter(adapter);
        imageSpinner.setSelection(allImages.indexOf(imgName));
        imageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Button imgDim = findViewById(R.id.dimEdit);
                String selected = imageSpinner.getSelectedItem().toString();
                if (selected.equals(NO_IMG)) {
                    imgDim.setVisibility(View.INVISIBLE);
                } else {
                    imgDim.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

    }

private boolean checkInputs (String mode) {
        HashMap<String, Shape> currShapes = AllShapes.getInstance().getAllShapes();
        EditText nameBox = findViewById(R.id.currentShapeNameEdit);
        String newName = nameBox.getText().toString().toLowerCase();

        if (newName.isEmpty() && mode.equals(UPDATE)) {
            Toast.makeText(getApplicationContext(), "MUST PROVIDE SHAPE NAME", Toast.LENGTH_SHORT).show();
            return false;
        } else if ((!currShapeName.equals(newName) && currShapes.containsKey(newName)) && mode.equals(UPDATE)) {
            Toast.makeText(getApplicationContext(), "SHAPE NAME ALREADY EXISTS", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            EditText shapeWidth = findViewById(R.id.shapeWidthEdit);
            String widthString = shapeWidth.getText().toString();

            EditText shapeHeight = findViewById(R.id.shapeHeightEdit);
            String heightString = shapeHeight.getText().toString();

            EditText textInput = findViewById(R.id.textStringEdit);
            String textString = textInput.getText().toString();

            EditText textSizeInput = findViewById(R.id.textSizeEdit);
            String textSizeString = textSizeInput.getText().toString();

            if (textString.isEmpty() && (heightString.isEmpty() || widthString.isEmpty())) {
                Toast.makeText(getApplicationContext(), "MUST PROVIDE DIMENSIONS FOR IMAGE", Toast.LENGTH_SHORT).show();
                return false;
            } else if (!textString.isEmpty() && textSizeString.isEmpty()) {
                Toast.makeText(getApplicationContext(), "MUST GIVE FONT SIZE FOR TEXT", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }
    }

  
  
    public void moveShape(View view) {
        Intent intent = new Intent(this, PlaceShape.class);
        Shape currShape = AllShapes.getInstance().getAllShapes().get(currShapeName);
        intent.putExtra("pageName", currShape.getAssociatedPage());
        intent.putExtra("editing", true);
        intent.putExtra("shape", currShapeName);
        startActivity(intent);
    }

    public void updateTheShape(View view) {
        HashMap<String, Shape> currShapes = AllShapes.getInstance().getAllShapes();
        EditText nameBox = findViewById(R.id.currentShapeNameEdit);
        String newName = nameBox.getText().toString().toLowerCase();

        if (newName.isEmpty() && mode.equals(UPDATE)) {
            Toast.makeText(getApplicationContext(), "MUST PROVIDE SHAPE NAME", Toast.LENGTH_SHORT).show();
            return false;
        } else if ((!currShapeName.equals(newName) && currShapes.containsKey(newName)) && mode.equals(UPDATE)) {
            Toast.makeText(getApplicationContext(), "SHAPE NAME ALREADY EXISTS", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            EditText shapeWidth = findViewById(R.id.shapeWidthEdit);
            String widthString = shapeWidth.getText().toString();

            EditText shapeHeight = findViewById(R.id.shapeHeightEdit);
            String heightString = shapeHeight.getText().toString();

            EditText textInput = findViewById(R.id.textStringEdit);
            String textString = textInput.getText().toString();

            EditText textSizeInput = findViewById(R.id.textSizeEdit);
            String textSizeString = textSizeInput.getText().toString();

            if (textString.isEmpty() && (heightString.isEmpty() || widthString.isEmpty())) {
                Toast.makeText(getApplicationContext(), "MUST PROVIDE DIMENSIONS FOR IMAGE", Toast.LENGTH_SHORT).show();
                return false;
            } else if (!textString.isEmpty() && textSizeString.isEmpty()) {
                Toast.makeText(getApplicationContext(), "MUST GIVE FONT SIZE FOR TEXT", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }
    }


    // updates the fields of a given shape based on the user inputs in the GUI
    private void updateAShape(Shape currShape) {
        EditText shapeWidth = findViewById(R.id.shapeWidthEdit);
        String widthString = shapeWidth.getText().toString();

        EditText shapeHeight = findViewById(R.id.shapeHeightEdit);
        String heightString = shapeHeight.getText().toString();

        Spinner imageSpinner = findViewById(R.id.imageNameSpinEdit);
        String imageName = imageSpinner.getSelectedItem().toString();

        EditText textInput = findViewById(R.id.textStringEdit);
        String textString = textInput.getText().toString();

        EditText textSizeInput = findViewById(R.id.textSizeEdit);
        String textSizeString = textSizeInput.getText().toString();

        //Width
        float width = 0f;
        if (!widthString.isEmpty()) width = Float.parseFloat(widthString);
        currShape.setWidth(width);

        //Height
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
        currShape.imageName = imageName;
        BitmapDrawable imageDrawable;
        if (!imageName.equals(NO_IMG)) {
            int imageID = getResources().getIdentifier(imageName, "drawable", getPackageName());
            imageDrawable = (BitmapDrawable) getResources().getDrawable(imageID);
        } else {
            imageDrawable = null;
        }
        currShape.setImage(imageDrawable);

        // text
        currShape.setText(textString);

        // text Size
        int textSize = 0;
        if (!textSizeString.isEmpty()) textSize = Integer.parseInt(textSizeString);
        currShape.fontSize = textSize;

    }


    public void updateTheShape(View view) {
        HashMap<String, Shape> currShapes = AllShapes.getInstance().getAllShapes();
        Shape currShape = currShapes.get(currShapeName);
        EditText nameBox = findViewById(R.id.currentShapeNameEdit);
        String newName = nameBox.getText().toString().toLowerCase();

        if (checkInputs(UPDATE)) {
            currShape.setName(newName);

            updateAShape(currShape);
            currShapes.remove(currShapeName);
            currShapes.put(newName, currShape);
            currShapeName = newName;

            Toast.makeText(getApplicationContext(), "SHAPE UPDATED", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), EditShapeOptions.class);
            intent.putExtra("shape", currShapeName);
            startActivity(intent);
        }
    }



    public void setImgDefault(View view) {
        Spinner imageSpin = findViewById(R.id.imageNameSpinEdit);
        String image = imageSpin.getSelectedItem().toString();

        if(!image.equals(NO_IMG)){
            int imageID = getResources().getIdentifier(image,"drawable", getPackageName());
            BitmapDrawable imageDrawable = (BitmapDrawable) getResources().getDrawable(imageID);
            float height = imageDrawable.getIntrinsicHeight();
            float width = imageDrawable.getIntrinsicWidth();
            EditText shapeHeight = findViewById(R.id.shapeHeightEdit);
            shapeHeight.setText(Float.toString(height));
            EditText shapeWidth = findViewById(R.id.shapeWidthEdit);
            shapeWidth.setText(Float.toString(width));
        }
    }

    public void copyShapeEdit(View view) {
        if (checkInputs(COPY)) {
            Shape newShape = new Shape(null, COPY, 0, 0, 0, 0, false, false, "", null, "", "", 0);
            updateAShape(newShape);
            AllShapes.getInstance().setCopiedShape(newShape);
            Toast.makeText(getApplicationContext(), "SHAPE COPIED", Toast.LENGTH_SHORT).show();
            Button pasteButton = findViewById(R.id.pasteShapeEdit);
            pasteButton.setVisibility(View.VISIBLE);
        }
    }

    public void pasteShapeEdit(View view) {
        Shape copiedShape = AllShapes.getInstance().getCopiedShape();
        if (copiedShape != null) fillInShapeFields(copiedShape);
    }
}