package edu.stanford.cs108.bunnyworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
            String two = spin2.getSelectedItem().toString();
            String three = spin3.getSelectedItem().toString();
            if(command.getText().equals("Current Command: ")) {
                Spinner spin1 = findViewById(R.id.scriptFirstEdit);
                String one = spin1.getSelectedItem().toString();
                if(one.equals(ON_DROP)) {
                    Spinner dropSpinner = findViewById(R.id.optionalShapeDrop);
                    String shapeDrop = dropSpinner.getSelectedItem().toString();
                    command.setText(command.getText() + " " + one + " " + shapeDrop + " " + two + " " + three);
                } else {
                    command.setText(command.getText() + one + " " + two + " " + three);
                }
            } else {
                command.setText(command.getText() + " " + two + " " + three);
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
                Spinner dropSpinner = findViewById(R.id.optionalShapeDrop);
                Spinner spinnerTwo = findViewById(R.id.scriptSecondEdit);
                Spinner spinnerThree = findViewById(R.id.scriptThirdEdit);
                clearCommand(findViewById(R.id.clear));
                Spinner firstSpin = findViewById(R.id.scriptFirstEdit);
                if(firstSpin.getSelectedItem().toString().equals("Select One")) {
                    Button addClause = (Button) findViewById(R.id.addClause);
                    addClause.setVisibility(View.GONE);
                    TextView clause = (TextView) findViewById(R.id.clause);
                    clause.setText("Clause: ");
                    dropSpinner.setVisibility(View.INVISIBLE);
                    spinnerTwo.setVisibility(View.INVISIBLE);
                    spinnerThree.setVisibility(View.INVISIBLE);

                } else if(firstSpin.getSelectedItem().toString().equals(ON_DROP)){
                    setSpinnerListToAllShapes(dropSpinner);
                    dropSpinner.setVisibility(View.VISIBLE);
                    setUpDropSpinner();
                    spinnerTwo.setVisibility(View.INVISIBLE);
                    spinnerThree.setVisibility(View.INVISIBLE);
                } else {
                    dropSpinner.setVisibility(View.INVISIBLE);
                    setUpSpinnerTwo();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
    }

    private void setUpDropSpinner() {
        final Spinner dropSpinner = findViewById(R.id.optionalShapeDrop);
        dropSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Spinner secondSpin = findViewById(R.id.scriptSecondEdit);
                Spinner thirdSpin = findViewById(R.id.scriptThirdEdit);
                clearCommand(null);
                if(dropSpinner.getSelectedItem().toString().equals("Select One")) {
                    Button addClause = (Button) findViewById(R.id.addClause);
                    addClause.setVisibility(View.GONE);
                    TextView clause = (TextView) findViewById(R.id.clause);
                    clause.setText("Clause: ");
                    secondSpin.setVisibility(View.INVISIBLE);
                    thirdSpin.setVisibility(View.INVISIBLE);
                } else {
                    secondSpin.setVisibility(View.VISIBLE);
                    setUpSpinnerTwo();
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
        Spinner secondSpin = findViewById(R.id.scriptSecondEdit);

        setSpinnerListToAllActions(secondSpin);
        setUpSpinnerTwoListener(secondSpin);
        findViewById(R.id.scriptThirdEdit).setVisibility(View.INVISIBLE);
    }


    // Makes Spinner Two visible and adds an OnItemSelectedListener to it
    private void setUpSpinnerTwoListener (final Spinner secondSpin) {
        secondSpin.setVisibility(View.VISIBLE);

        // sets up the listener for the new spinner
        secondSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setUpSpinnerThree();
                Spinner thirdSpin = findViewById(R.id.scriptThirdEdit);
                if(secondSpin.getSelectedItem().toString().equals("Select One")) {
                    Button addClause = (Button) findViewById(R.id.addClause);
                    addClause.setVisibility(View.GONE);
                    TextView clause = (TextView) findViewById(R.id.clause);
                    clause.setText("Clause: ");
                } else {
                    thirdSpin.setVisibility(View.VISIBLE);
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

        String selectedTwo = secondSpin.getSelectedItem().toString();

        if (selectedTwo.equals(PLAY)) {
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

    private void setUpSpinnerThreeListener(final Spinner fourthSpin) {
        fourthSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Spinner firstSpin = findViewById(R.id.scriptFirstEdit);
                String task1 = firstSpin.getSelectedItem().toString();
                Spinner dropShape = findViewById(R.id.optionalShapeDrop);
                Spinner secondSpin = findViewById(R.id.scriptSecondEdit);
                String task2 = secondSpin.getSelectedItem().toString();
                Spinner thirdSpin = findViewById(R.id.scriptThirdEdit);
                String task3 = thirdSpin.getSelectedItem().toString();
                Button addClause = (Button) findViewById(R.id.addClause);
                if(task1.equals(ON_DROP)){
                    String shapeDrop = dropShape.getSelectedItem().toString();
                    if(!shapeDrop.equals("Select One") && !task2.equals("Select One") && !task3.equals("Select One")){
                        addClause.setVisibility(View.VISIBLE);
                        TextView clause = (TextView) findViewById(R.id.clause);
                        clause.setText("Clause: " + task2 + " " + task3);
                    }
                } else if(!task1.equals("Select One") && !task2.equals("Select One") && !task3.equals("Select One")) {
                    addClause.setVisibility(View.VISIBLE);
                    TextView clause = (TextView) findViewById(R.id.clause);
                    clause.setText("Clause: " + task2 + " " + task3);
                } else {
                    addClause.setVisibility(View.GONE);
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
        LinearLayout holder = (LinearLayout) findViewById(R.id.deleteHolder);
        if(holder.getVisibility() == View.GONE) {
            holder.setVisibility(View.VISIBLE);
            setUpDeleteSpinner();
        } else {
            holder.setVisibility(View.GONE);
        }
    }

    private void setUpDeleteSpinner () {
        Spinner deleteSpinner = findViewById(R.id.deleteTrigger);
        ArrayAdapter<String> firstAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item,initialTriggers);
        firstAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deleteSpinner.setAdapter(firstAdapter);
        deleteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Spinner delete = findViewById(R.id.deleteTrigger);
                Button deleteButton = findViewById(R.id.specialDeleteButton);
                Spinner shape = findViewById(R.id.deleteShape);
                if(delete.getSelectedItem().toString().equals(ON_DROP)) {
                    setUpDeleteShapeSpinner();
                    shape.setVisibility(View.VISIBLE);
                } else if(delete.getSelectedItem().toString().equals("Select One")) {
                    deleteButton.setVisibility(View.GONE);
                    shape.setVisibility(View.GONE);
                } else {
                    deleteButton.setVisibility(View.VISIBLE);
                    shape.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
    }

    private void setUpDeleteShapeSpinner () {
        Spinner deleteShapeSpinner = findViewById(R.id.deleteShape);
        setSpinnerListToAllShapes(deleteShapeSpinner);
        setUpDeleteShapeSpinnerListener(deleteShapeSpinner);
    }

    private void setUpDeleteShapeSpinnerListener(Spinner deleteShapeSpinner) {
        deleteShapeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Spinner deleteShapeSpinner = findViewById(R.id.deleteShape);
                Button deleteButton = findViewById(R.id.specialDeleteButton);
                if(deleteShapeSpinner.getSelectedItem().toString().equals("Select One")) {
                    deleteButton.setVisibility(View.GONE);
                } else {
                    deleteButton.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
    }

    public void deleteCommand(View view) {
        Spinner deleteTrigger = findViewById(R.id.deleteTrigger);
        Spinner deleteShape = findViewById(R.id.deleteShape);
        String trigger = deleteTrigger.getSelectedItem().toString();
        if(deleteShape.getSelectedItem() != null) {
            trigger += " " + deleteShape.getSelectedItem().toString();
        }
        String shapeScript = currShape.getScript();
        int index = shapeScript.indexOf(trigger);
        if(index != -1) {
            int end = shapeScript.indexOf(';', index);
            String newScript = shapeScript.replace(shapeScript.substring(index, end + 1), "");
            currShape.setScript(newScript);
            currScript.setText(newScript);
        }
    }

    /**
     * Returns to the edit shape options.
     * @param view
     */
    public void doneWithScript(View view) {
        if (AllShapes.getInstance().getAllShapes().containsKey(currShape.getName())) {
            if(!getIntent().getBooleanExtra("editing", false)) {
                Intent intent = new Intent(this, PlaceShape.class);
                intent.putExtra("pageName", currShape.getAssociatedPage());
                intent.putExtra("shape", currShape.getName());
                intent.putExtra("editing", false);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, EditShapeOptions.class);
                intent.putExtra("shape", currShape.getName());
                intent.putExtra("pageName", currShape.getAssociatedPage());
                startActivity(intent);
            }
        } else {
            Toast.makeText(getApplicationContext(), "SHAPE NO LONGER EXISTS", Toast.LENGTH_SHORT).show();
        }
    }


    public void addToScript(View view) {
        Spinner script1 = findViewById(R.id.scriptFirstEdit);
        Spinner script2 = findViewById(R.id.scriptSecondEdit);
        Spinner script3 = findViewById(R.id.scriptThirdEdit);
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
    }
}