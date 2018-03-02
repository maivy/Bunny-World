package edu.stanford.cs108.bunnyworld;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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


    // made the first entry in the list Select One So That's what Appears at the start
    private final ArrayList<String> initialTriggers = new ArrayList<>(Arrays.asList("Select One", ON_CLICK, ON_ENTER, ON_DROP));
    private final ArrayList<String> allActions = new ArrayList<>(Arrays.asList("Select One", GOTO, PLAY, HIDE, SHOW));
    private final ArrayList<String> allSounds = new ArrayList<>(Arrays.asList("Select One", "carrotcarrotcarrot", "evillaugh", "fire",
                                                                "hooray", "munch", "munching", "woof"));

    private final ArrayList<String> allImages = new ArrayList<>(Arrays.asList("carrot", "carrot2", "death", "duck",
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
        Spinner imageSpinner = findViewById(R.id.imageNameSpin);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item,allImages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        imageSpinner.setAdapter(adapter);
        setUpFirstSpinner();
    }


    // Sets up the first spinner and its listener
    private void setUpFirstSpinner () {
        Spinner scriptPartOne = findViewById(R.id.scriptFirst);
        ArrayAdapter<String> firstAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item,initialTriggers);
        firstAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        scriptPartOne.setAdapter(firstAdapter);
        scriptPartOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setUpSpinnerTwo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
    }


    // Based on what was selected in spinner one it sets up spinner two
    private void setUpSpinnerTwo () {
        Spinner firstSpin = findViewById(R.id.scriptFirst);
        Spinner secondSpin = findViewById(R.id.scriptSecond);
        String selected = firstSpin.getSelectedItem().toString();

        if (selected.equals(ON_DROP)) {
            setSpinnerListToAllShapes(secondSpin);
            setUpSpinnerTwoListener(secondSpin);
        } else if (selected.equals(ON_ENTER) || selected.equals(ON_CLICK)){
           setSpinnerListToAllActions(secondSpin);
           setUpSpinnerTwoListener(secondSpin);
           findViewById(R.id.scriptFourth).setVisibility(View.INVISIBLE);
            findViewById(R.id.scriptThird).setVisibility(View.INVISIBLE);
        }
    }


    // Makes Spinner Two visible and adds an OnItemSelectedListener to it
    private void setUpSpinnerTwoListener (Spinner secondSpin) {
        secondSpin.setVisibility(View.VISIBLE);

        // sets up the listener for the new spinner
        secondSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setUpSpinnerThree();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
    }


    // Based on what was selected in spinner two, sets up spinner three if necessary
    private void setUpSpinnerThree() {
        Spinner firstSpin = findViewById(R.id.scriptFirst);
        Spinner secondSpin = findViewById(R.id.scriptSecond);
        Spinner thirdSpin = findViewById(R.id.scriptThird);

        String selectedOne = firstSpin.getSelectedItem().toString();
        String selectedTwo = secondSpin.getSelectedItem().toString();

        if (selectedOne.equals(ON_DROP)) {
            setSpinnerListToAllActions(thirdSpin);
            setUpSpinnerThreeListerner(thirdSpin);
        } else if (selectedTwo.equals(PLAY)) {
            setSpinnerListToAllSounds(thirdSpin);
            setUpSpinnerThreeListerner(thirdSpin);
        } else if (selectedTwo.equals(GOTO)) {
            setSpinnerListToAllPages(thirdSpin);
            setUpSpinnerThreeListerner(thirdSpin);
        } else if (selectedTwo.equals(HIDE) || selectedTwo.equals(SHOW)) {
            setSpinnerListToAllShapes(thirdSpin);
            setUpSpinnerThreeListerner(thirdSpin);
        }
    }


    // Makes Spinner Three visible and adds an OnItemSelectedListener to it
    private void setUpSpinnerThreeListerner(Spinner thirdSpin) {
        thirdSpin.setVisibility(View.VISIBLE);
        thirdSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setUpSpinnerFour();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
    }


    // Based on what was selected in spinner three it sets up spinner four if necessary
    private void setUpSpinnerFour () {
        Spinner thirdSpin = findViewById(R.id.scriptThird);
        Spinner fourthSpin = findViewById(R.id.scriptFourth);
        String selected = thirdSpin.getSelectedItem().toString();

        if (selected.equals(PLAY)) {
            setSpinnerListToAllSounds(fourthSpin);
            fourthSpin.setVisibility(View.VISIBLE);
        } else if (selected.equals(GOTO)) {
            setSpinnerListToAllPages(fourthSpin);
            fourthSpin.setVisibility(View.VISIBLE);
        } else if (selected.equals(HIDE) || selected.equals(SHOW)) {
            setSpinnerListToAllShapes(fourthSpin);
            fourthSpin.setVisibility(View.VISIBLE);
        }
    }


    // Sets the ArrayList of all the possible things you can select in the given spinner,
    // to be a list of all the actions available in the game (4 actions)
    private void setSpinnerListToAllActions (Spinner currSpinner) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                    android.R.layout.simple_spinner_item,allActions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currSpinner.setAdapter(adapter);
    }


    // Sets the ArrayList of items the currSpinner is displaying to be all the available sounds
    private void setSpinnerListToAllSounds (Spinner currSpinner) {
              ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item,allSounds);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currSpinner.setAdapter(adapter);
    }

    // Sets the ArrayList of items the currSpinner is displaying to be all the available shapes
    private void setSpinnerListToAllShapes (Spinner currSpinner) {
        // makes the first entry in the list "Select One" so that's what initially visible
        ArrayList<String> shapeNames = new ArrayList<>();
        shapeNames.add("Select One");

        HashMap<String, Shape> currShapes = AllShapes.getInstance().getAllShapes();
        for (String key : currShapes.keySet()) {
            shapeNames.add(key);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item,shapeNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currSpinner.setAdapter(adapter);
    }


    // Sets the ArrayList of items the currSpinner is displaying to be all the available pages
    private void setSpinnerListToAllPages (Spinner currSpinner) {
        // makes the first entry in the list "Select One" so that's what initially visible
        ArrayList<String> pageNames = new ArrayList<>();
        pageNames.add("Select One");

        HashMap<String, Page> currPages = AllPages.getInstance().getAllPages();
        for (String key : currPages.keySet()) {
            pageNames.add(key);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item,pageNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currSpinner.setAdapter(adapter);
    }


    /**
     * Sets all the needed parameters needed to create a new shape
     * @param shapeName
     */
    private Shape buildNewShape (String shapeName) {
        //startX
        EditText xLoc = findViewById(R.id.startX);
        String xString = xLoc.getText().toString();
        float x = 0f;
        if (!xString.isEmpty()) x = Float.parseFloat(xString);

        //startY
        EditText yLoc = findViewById(R.id.startY);
        String yString = yLoc.getText().toString();
        float y = 0f;
        if (!yString.isEmpty())  y = Float.parseFloat(yString);

        //Width
        EditText shapeWidth = findViewById(R.id.shapeWidth);
        String widthString = shapeWidth.getText().toString();
        float width = 0f;
        if (!widthString.isEmpty()) width = Float.parseFloat(widthString);

        //Height
        EditText shapeHeight = findViewById(R.id.shapeHeight);
        String heightString = shapeHeight.getText().toString();
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
        int imageID = getResources().getIdentifier(imageName,"drawable", getPackageName());
        BitmapDrawable imageDrawable = (BitmapDrawable) getResources().getDrawable(imageID);

        // text
        EditText textInput = findViewById(R.id.textString);
        String textString = textInput.getText().toString();

        // text Size
        EditText textSizeInput = findViewById(R.id.textSize);
        String textSizeString = textSizeInput.getText().toString();
        int textSize = 0;
        if (!textSizeString.isEmpty()) textSize = Integer.parseInt(textSizeString);

        //Script
        Spinner script1 = findViewById(R.id.scriptFirst);
        Spinner script2 = findViewById(R.id.scriptSecond);
        Spinner script3 = findViewById(R.id.scriptThird);
        Spinner script4 = findViewById(R.id.scriptFourth);
        // TODO: change script back to begin with an empty string once Shape can handle an empty string
        String script = "tests text";

        // Makes sure to only make a script when something is selected
        if (!script1.getSelectedItem().equals("Select One")) {
            script += script1.getSelectedItem().toString();
            if (script2.getSelectedItem() != null && !script2.getSelectedItem().equals("Select One")) {
                script += " " + script2.getSelectedItem().toString();
                if (script3.getVisibility() != View.INVISIBLE && script3.getSelectedItem() != null
                        && !script3.getSelectedItem().equals("Select One")) {
                    script += " " + script3.getSelectedItem().toString();
                    if (script4.getVisibility() != View.INVISIBLE && script4.getSelectedItem() != null
                            && !script4.getSelectedItem().equals("Select One"))
                        script += " " + script4.getSelectedItem().toString();
                }
            }
        }
        Toast.makeText(getApplicationContext(), script, Toast.LENGTH_SHORT).show();
        Shape currShape = new Shape(currPageName, shapeName, x, y, width, height, hidden, moveable, imageName, imageDrawable, textString, script, textSize);
        return currShape;
    }


    /**
     *     Creates a shape assuming the user has given us all the necessary information
     */
    public void createNewShape(View view) {
        EditText shapeName = findViewById(R.id.currentShapeName);
        String shapeNameString = shapeName.getText().toString();

        // If a Shape with that name doesn't already exist
        if (!AllShapes.getInstance().getAllShapes().containsKey(shapeNameString)) {
            Shape newShape = buildNewShape(shapeNameString);
            buildNewShape(shapeNameString);
            AllShapes.getInstance().getAllShapes().put(shapeNameString, newShape);

            // adds one to the total number of shapes ever created so we know what to name future Shapes
            AllShapes.getInstance().updateCurrShapeNumber();
            Toast.makeText(getApplicationContext(), shapeNameString +" CREATED", Toast.LENGTH_SHORT).show();

            // Goes back to the page, does not create new page
            Intent intent = new Intent(this, NewPage.class);
            intent.putExtra("NEW_PAGE", false);
            intent.putExtra("pageName", currPageName);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), shapeNameString +" ALREADY EXISTS", Toast.LENGTH_SHORT).show();
        }
    }
}
