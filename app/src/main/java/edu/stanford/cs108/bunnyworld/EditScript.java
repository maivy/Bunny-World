package edu.stanford.cs108.bunnyworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class EditScript extends AppCompatActivity {
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

    private TextView currScript;
    private Shape currShape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_script);
        Intent intent = this.getIntent();
        String shapeName = intent.getStringExtra("shape");
        currShape = AllShapes.getInstance().getAllShapes().get(shapeName);
        init();
    }



    private void init () {
        //displays the name of the current page
        TextView currentPage = findViewById(R.id.currPageEditScript);
        currentPage.setText(currShape.getAssociatedPage());

        // displays the name of the current shape
        TextView currShapeName = findViewById(R.id.currShapeEditScript);
        currShapeName.setText(currShape.getName());
        setUpFirstSpinner();

        currScript = findViewById(R.id.currentScripts);
        currScript.setText(currShape.getScript());
    }

    public void addClause(View view) {
        TextView clause = (TextView) findViewById(R.id.clause);
        if(!clause.getText().toString().equals("Clause: ")) {
            TextView command = (TextView) findViewById(R.id.command);
            Spinner spin2 = findViewById(R.id.scriptSecondEdit);
            Spinner spin3 = findViewById(R.id.scriptThirdEdit);
            Spinner spin4 = findViewById(R.id.scriptFourthEdit);
            String two = spin2.getSelectedItem().toString();
            String three = spin3.getSelectedItem().toString();
            if(command.getText().equals("Current Command: ")) {
                Spinner spin1 = findViewById(R.id.scriptFirstEdit);
                String one = spin1.getSelectedItem().toString();
                command.setText(command.getText() + one + " " + two + " " + three);
                if (spin4.getSelectedItem() != null) {
                    String four = spin4.getSelectedItem().toString();
                    command.setText(command.getText() + " " + four);
                }
            } else {
                command.setText(command.getText() + " " + two + " " + three);
                if (spin4.getSelectedItem() != null) {
                    String four = spin4.getSelectedItem().toString();
                    command.setText(command.getText() + " " + four);
                }
            }
        }
    }

    public void clearCommand(View view) {
        TextView command = (TextView) findViewById(R.id.command);
        command.setText("Current Command: ");
    }

    // Sets up the first spinner and its listener
    private void setUpFirstSpinner () {
        Spinner scriptPartOne = findViewById(R.id.scriptFirstEdit);
        ArrayAdapter<String> firstAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item,initialTriggers);
        firstAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        scriptPartOne.setAdapter(firstAdapter);
        scriptPartOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                clearCommand(findViewById(R.id.clear));
                setUpSpinnerTwo();
                Spinner firstSpin = findViewById(R.id.scriptFirstEdit);
                if(firstSpin.getSelectedItem().toString().equals("Select One")) {
                    Button addClause = (Button) findViewById(R.id.addClause);
                    addClause.setVisibility(View.INVISIBLE);
                    TextView clause = (TextView) findViewById(R.id.clause);
                    clause.setText("Clause: ");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
    }


    // Based on what was selected in spinner one it sets up spinner two
    private void setUpSpinnerTwo () {
        Spinner firstSpin = findViewById(R.id.scriptFirstEdit);
        Spinner secondSpin = findViewById(R.id.scriptSecondEdit);
        String selected = firstSpin.getSelectedItem().toString();

        if (selected.equals(ON_DROP)) {
            setSpinnerListToAllShapes(secondSpin);
            setUpSpinnerTwoListener(secondSpin);
        } else if (selected.equals(ON_ENTER) || selected.equals(ON_CLICK)){
            setSpinnerListToAllActions(secondSpin);
            setUpSpinnerTwoListener(secondSpin);
            findViewById(R.id.scriptFourthEdit).setVisibility(View.INVISIBLE);
            findViewById(R.id.scriptThirdEdit).setVisibility(View.INVISIBLE);
        }
    }


    // Makes Spinner Two visible and adds an OnItemSelectedListener to it
    private void setUpSpinnerTwoListener (final Spinner secondSpin) {
        secondSpin.setVisibility(View.VISIBLE);

        // sets up the listener for the new spinner
        secondSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setUpSpinnerThree();
                if(secondSpin.getSelectedItem().toString().equals("Select One")) {
                    Button addClause = (Button) findViewById(R.id.addClause);
                    addClause.setVisibility(View.INVISIBLE);
                    TextView clause = (TextView) findViewById(R.id.clause);
                    clause.setText("Clause: ");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
    }


    // Based on what was selected in spinner two, sets up spinner three if necessary
    private void setUpSpinnerThree() {
        Spinner firstSpin = findViewById(R.id.scriptFirstEdit);
        Spinner secondSpin = findViewById(R.id.scriptSecondEdit);
        Spinner thirdSpin = findViewById(R.id.scriptThirdEdit);

        String selectedOne = firstSpin.getSelectedItem().toString();
        String selectedTwo = secondSpin.getSelectedItem().toString();

        if (selectedOne.equals(ON_DROP)) {
            setSpinnerListToAllActions(thirdSpin);
            setUpSpinnerThreeListener(thirdSpin);
        } else if (selectedTwo.equals(PLAY)) {
            setSpinnerListToAllSounds(thirdSpin);
            setUpSpinnerThreeListener(thirdSpin);
        } else if (selectedTwo.equals(GOTO)) {
            setSpinnerListToAllPages(thirdSpin);
            setUpSpinnerThreeListener(thirdSpin);
        } else if (selectedTwo.equals(HIDE) || selectedTwo.equals(SHOW)) {
            setSpinnerListToAllShapes(thirdSpin);
            setUpSpinnerThreeListener(thirdSpin);
        }
    }


    // Makes Spinner Three visible and adds an OnItemSelectedListener to it
    private void setUpSpinnerThreeListener(final Spinner thirdSpin) {
        thirdSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Spinner firstSpin = findViewById(R.id.scriptFirstEdit);
                Spinner secondSpin = findViewById(R.id.scriptSecondEdit);
                String task2 = secondSpin.getSelectedItem().toString();
                String selectedOne = firstSpin.getSelectedItem().toString();
                thirdSpin.setVisibility(View.VISIBLE);
                Button addClause = (Button) findViewById(R.id.addClause);
                if(selectedOne.equals(ON_DROP)) {
                    setUpSpinnerFour();
                    if(thirdSpin.getSelectedItem().toString().equals("Select One")) {
                        addClause.setVisibility(View.INVISIBLE);
                        TextView clause = (TextView) findViewById(R.id.clause);
                        clause.setText("Clause: ");
                    }
                } else if (!thirdSpin.getSelectedItem().toString().equals("Select One") && !selectedOne.equals("Select One") && !task2.equals("Select One")){
                    addClause.setVisibility(View.VISIBLE);
                    TextView clause = (TextView) findViewById(R.id.clause);
                    clause.setText("Clause: " + task2 + " " + thirdSpin.getSelectedItem().toString());
                } else {
                    addClause.setVisibility(View.INVISIBLE);
                    TextView clause = (TextView) findViewById(R.id.clause);
                    clause.setText("Clause: ");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
    }


    // Based on what was selected in spinner three it sets up spinner four if necessary
    private void setUpSpinnerFour () {
        Spinner thirdSpin = findViewById(R.id.scriptThirdEdit);
        Spinner fourthSpin = findViewById(R.id.scriptFourthEdit);
        String selected = thirdSpin.getSelectedItem().toString();

        if (selected.equals(PLAY)) {
            setSpinnerListToAllSounds(fourthSpin);
            fourthSpin.setVisibility(View.VISIBLE);
            setUpSpinnerFourListener(fourthSpin);
        } else if (selected.equals(GOTO)) {
            setSpinnerListToAllPages(fourthSpin);
            fourthSpin.setVisibility(View.VISIBLE);
            setUpSpinnerFourListener(fourthSpin);
        } else if (selected.equals(HIDE) || selected.equals(SHOW)) {
            setSpinnerListToAllShapes(fourthSpin);
            fourthSpin.setVisibility(View.VISIBLE);
            setUpSpinnerFourListener(fourthSpin);
        }
    }

    private void setUpSpinnerFourListener(final Spinner fourthSpin) {
        fourthSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Spinner firstSpin = findViewById(R.id.scriptFirstEdit);
                String task1 = firstSpin.getSelectedItem().toString();
                Spinner secondSpin = findViewById(R.id.scriptSecondEdit);
                String task2 = secondSpin.getSelectedItem().toString();
                Spinner thirdSpin = findViewById(R.id.scriptThirdEdit);
                String task3 = thirdSpin.getSelectedItem().toString();
                Button addClause = (Button) findViewById(R.id.addClause);
                if(!task1.equals("Select One") && !task2.equals("Select One") && !task3.equals("Select One") && !fourthSpin.getSelectedItem().toString().equals("Select One")) {
                    addClause.setVisibility(View.VISIBLE);
                    TextView clause = (TextView) findViewById(R.id.clause);
                    clause.setText("Clause: " + task2 + " " + task3 + " " + fourthSpin.getSelectedItem().toString());
                } else {
                    addClause.setVisibility(View.INVISIBLE);
                    TextView clause = (TextView) findViewById(R.id.clause);
                    clause.setText("Clause: ");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
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
                android.R.layout.simple_spinner_item, allSounds);
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
     * Deletes the current script.
     * @param view
     */
    public void deleteScript(View view) {
        currScript.setText("");
        currShape.setScript("");
    }

    /**
     * Returns to the edit shape options.
     * @param view
     */
    public void doneWithScript(View view) {
        Intent intent = new Intent(this, EditShapeOptions.class);
        intent.putExtra("shape", currShape.getName());
        startActivity(intent);
    }


    public void addToScript(View view) {
        Spinner script1 = findViewById(R.id.scriptFirstEdit);
        Spinner script2 = findViewById(R.id.scriptSecondEdit);
        Spinner script3 = findViewById(R.id.scriptThirdEdit);
        Spinner script4 = findViewById(R.id.scriptFourthEdit);
        TextView command = (TextView) findViewById(R.id.command);
        String script = command.getText().toString();

        if(!script.equals("Current Command: ")){
            int index = script.indexOf(':');
            String result = script.substring(index + 2);
            result += ";";
            currShape.addToScript(result);
            currScript.setText(currShape.getScript());
        }

        script1.setSelection(0);
        script2.setVisibility(View.INVISIBLE);
        script3.setVisibility(View.INVISIBLE);
        script4.setVisibility(View.INVISIBLE);
    }
}