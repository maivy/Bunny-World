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


    private void init () {
        Shape currShape = AllShapes.getInstance().getAllShapes().get(currShapeName);

        //displays the name of the current page
        TextView currentPage = findViewById(R.id.currPageEditShape);
        currentPage.setText(currShape.getAssociatedPage());

        EditText shapeNameBox = findViewById(R.id.currentShapeNameEdit);
        shapeNameBox.setText(currShapeName);

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
        String newName = nameBox.getText().toString().toLowerCase();
        currShape.setName(newName);

        if (newName.isEmpty()) {
            Toast.makeText(getApplicationContext(), "MUST PROVIDE SHAPE NAME", Toast.LENGTH_SHORT).show();
        } else if (!currShapeName.equals(newName) && currShapes.containsKey(newName)) {
            Toast.makeText(getApplicationContext(), "SHAPE NAME ALREADY EXISTS", Toast.LENGTH_SHORT).show();
        } else {
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

            if (textString.isEmpty() && (heightString.isEmpty() || widthString.isEmpty())) {
                Toast.makeText(getApplicationContext(), "MUST PROVIDE DIMENSIONS FOR IMAGE", Toast.LENGTH_SHORT).show();
            } else if (!textString.isEmpty() && textSizeString.isEmpty()) {
                Toast.makeText(getApplicationContext(), "MUST GIVE FONT SIZE FOR TEXT", Toast.LENGTH_SHORT).show();
            } else {
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

                currShapes.remove(currShapeName);
                currShapes.put(newName, currShape);
                currShapeName = newName;

                Toast.makeText(getApplicationContext(), "SHAPE UPDATED", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), EditShapeOptions.class);
                intent.putExtra("shape", currShapeName);
                startActivity(intent);
            }
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
}