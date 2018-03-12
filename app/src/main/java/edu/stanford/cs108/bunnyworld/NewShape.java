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

public class NewShape extends AppCompatActivity {
    private static final String PLAY = "play";
    private static final String GOTO = "goto";
    private static final String HIDE = "hide";
    private static final String SHOW = "show";
    private static final String ON_CLICK = "on click";
    private static final String ON_ENTER = "on enter";
    private static final String ON_DROP = "on drop";
    private static final String NO_IMG = "No Image";

    // made the first entry in the list Select One So That's what Appears at the start
    private final ArrayList<String> initialTriggers = new ArrayList<>(Arrays.asList("Select One", ON_CLICK, ON_ENTER, ON_DROP));
    private final ArrayList<String> allActions = new ArrayList<>(Arrays.asList("Select One", GOTO, PLAY, HIDE, SHOW));
    private final ArrayList<String> allSounds = new ArrayList<>(Arrays.asList("Select One", "carrotcarrotcarrot", "evillaugh", "fire",
            "hooray", "munch", "munching", "woof"));

    private final ArrayList<String> allImages = new ArrayList<>(Arrays.asList(NO_IMG,"carrot", "carrot2", "death", "duck",
            "fire", "mystic"));

    private String currPageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_shape);
        Intent intent = this.getIntent();
        init(intent);
    }


    private void init (Intent intent) {
        //displays the name of the current page
        TextView currentPage = findViewById(R.id.currPage);
        currPageName = intent.getStringExtra("Page");
        currentPage.setText(currPageName);

        // displays the name of the current shape in an edit text so the user can edit it if they want
        EditText currShapeName = findViewById(R.id.currentShapeName);
        String newShapeName = "shape" + AllShapes.getInstance().getCurrShapeNumber();
        currShapeName.setText(newShapeName);

        // default behavior makes the shape moveable
        RadioButton moveYes = findViewById(R.id.moveableYes);
        moveYes.setChecked(true);

        //default behavior makes the shape visible
        RadioButton hiddenNo = findViewById(R.id.hiddenNo);
        hiddenNo.setChecked(true);

        //Set up list of possible images
        final Spinner imageSpinner = findViewById(R.id.imageNameSpin);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item,allImages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        imageSpinner.setAdapter(adapter);
        imageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Button imgDim = findViewById(R.id.defaultDim);
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

    /**
     * Sets all the needed parameters needed to create a new shape
     * @param shapeName
     */
    private Shape buildNewShape (String shapeName) {
        EditText shapeWidth = findViewById(R.id.shapeWidth);
        String widthString = shapeWidth.getText().toString();

        EditText shapeHeight = findViewById(R.id.shapeHeight);
        String heightString = shapeHeight.getText().toString();

        EditText textInput = findViewById(R.id.textString);
        String textString = textInput.getText().toString();

        EditText textSizeInput = findViewById(R.id.textSize);
        String textSizeString = textSizeInput.getText().toString();

        //Width
        float width = 0f;
        if (!widthString.isEmpty()) width = Float.parseFloat(widthString);

        //Height
        float height = 0f;
        if (!heightString.isEmpty()) height = Float.parseFloat(heightString);

        //Hidden
        RadioButton hideYes = findViewById(R.id.hiddenYes);
        boolean hidden = hideYes.isChecked();

        // Moveable
        RadioButton yesMove = findViewById(R.id.moveableYes);
        boolean moveable = yesMove.isChecked();

        //Image
        Spinner imageSpinner = findViewById(R.id.imageNameSpin);
        String imageName = imageSpinner.getSelectedItem().toString();
        BitmapDrawable imageDrawable;
        if (!imageName.equals(NO_IMG)) {
            int imageID = getResources().getIdentifier(imageName,"drawable", getPackageName());
            imageDrawable = (BitmapDrawable) getResources().getDrawable(imageID);
        } else {
           imageDrawable = null;
        }

        // text Size
        int textSize = 0;
        if (!textSizeString.isEmpty()) textSize = Integer.parseInt(textSizeString);

        Shape currShape = new Shape(currPageName, shapeName, 200, 200, width, height, hidden, moveable, imageName, imageDrawable, textString, "", textSize);
        return currShape;
    }


    /**
     *     Creates a shape assuming the user has given us all the necessary information
     */
    public void createNewShape(View view) {
        EditText shapeName = findViewById(R.id.currentShapeName);
        String shapeNameString = shapeName.getText().toString().toLowerCase();

        // If a Shape with that name doesn't already exist
        if (AllShapes.getInstance().getAllShapes().containsKey(shapeNameString)) {
            Toast.makeText(getApplicationContext(), shapeNameString +" ALREADY EXISTS", Toast.LENGTH_SHORT).show();
        } else if (shapeNameString.isEmpty()) {
            Toast.makeText(getApplicationContext(), "MUST PROVIDE SHAPE NAME", Toast.LENGTH_SHORT).show();
        } else {
            EditText shapeWidth = findViewById(R.id.shapeWidth);
            String widthString = shapeWidth.getText().toString();

            EditText shapeHeight = findViewById(R.id.shapeHeight);
            String heightString = shapeHeight.getText().toString();

            EditText textInput = findViewById(R.id.textString);
            String textString = textInput.getText().toString();

            EditText textSizeInput = findViewById(R.id.textSize);
            String textSizeString = textSizeInput.getText().toString();

            if (textString.isEmpty() && (heightString.isEmpty() || widthString.isEmpty())) {
                Toast.makeText(getApplicationContext(), "MUST PROVIDE DIMENSIONS FOR IMAGE", Toast.LENGTH_SHORT).show();
            } else if (!textString.isEmpty() && textSizeString.isEmpty()) {
                Toast.makeText(getApplicationContext(), "MUST GIVE FONT SIZE FOR TEXT", Toast.LENGTH_SHORT).show();
            } else {
                Shape newShape = buildNewShape(shapeNameString);
                AllShapes.getInstance().getAllShapes().put(shapeNameString, newShape);

                // adds one to the total number of shapes ever created so we know what to name future Shapes
                AllShapes.getInstance().updateCurrShapeNumber();
                Toast.makeText(getApplicationContext(), shapeNameString +" CREATED", Toast.LENGTH_SHORT).show();

                // Goes back to the page, does not create new page
//                Intent intent = new Intent(this, NewPage.class);
//                intent.putExtra("NEW_PAGE", false);
//                intent.putExtra("pageName", currPageName);
//                startActivity(intent);

                Intent intent = new Intent(this, EditScript.class);
                intent.putExtra("shape", shapeNameString);
                intent.putExtra("pageName", currPageName);
                intent.putExtra("editing", false);
                startActivity(intent);
            }
        }
    }


    // Gets the default dimensions of the image selected
    public void setDefaultDim(View view) {
        Spinner imageSpin = findViewById(R.id.imageNameSpin);
        String image = imageSpin.getSelectedItem().toString();

        if(!image.equals(NO_IMG)){
            int imageID = getResources().getIdentifier(image,"drawable", getPackageName());
            BitmapDrawable imageDrawable = (BitmapDrawable) getResources().getDrawable(imageID);
            float height = imageDrawable.getIntrinsicHeight();
            float width = imageDrawable.getIntrinsicWidth();
            EditText shapeHeight = findViewById(R.id.shapeHeight);
            shapeHeight.setText(Float.toString(height));
            EditText shapeWidth = findViewById(R.id.shapeWidth);
            shapeWidth.setText(Float.toString(width));
        }
    }
}