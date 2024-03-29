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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static edu.stanford.cs108.bunnyworld.EditShape.NO_IMG;

public class NewPage extends AppCompatActivity {
    private static final String MAIN_PAGE = "page1";
    private Page currPage;
    private AllShapes allShapes;
    private HashMap<String, Shape> shapes;
    public CustomImages imageMap;
    public HashMap<String, BitmapDrawable> customBitmapDrawables;
    private ArrayList<String> customImagesNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_page);
        initPage(this.getIntent());
    }

    private void initPage (Intent intent) {
        boolean isNewPage = intent.getBooleanExtra("NEW_PAGE", true);
        String currPageName;

        allShapes = AllShapes.getInstance();
        shapes = allShapes.getAllShapes();

        imageMap = CustomImages.getInstance();
        customBitmapDrawables = imageMap.getBitmapDrawables();
        customImagesNames = new ArrayList<>(customBitmapDrawables.keySet());
        customImagesNames.add(0, NO_IMG);

        Button seeShapes = findViewById(R.id.pagesShapesButton);
        if (isNewPage) {
            seeShapes.setVisibility(View.GONE);
            HashMap<String, Page> currPages = AllPages.getInstance().getAllPages();
            currPageName = "page" + (AllPages.getInstance().getCurrPageNumber());
            currPage = new Page(currPageName);
            // add it to the singleton so all activities have the updated list of all the pages
            currPages.put(currPageName, currPage);
            AllPages.getInstance().updateCurrPageNumber();
            Toast.makeText(getApplicationContext(), "PAGE CREATED", Toast.LENGTH_SHORT).show();
        } else {
            seeShapes.setVisibility(View.VISIBLE);
            currPageName = intent.getStringExtra("pageName");
            currPage = AllPages.getInstance().getAllPages().get(currPageName);
        }

        // Display the Name of the current page
        TextView pageName = findViewById(R.id.nameOfNewPage);
        pageName.setText(currPageName);

        if (currPageName.equals(MAIN_PAGE)) {
            Button renameButton = findViewById(R.id.renameButton);
            renameButton.setVisibility(View.GONE);
            EditText nameEditBox = findViewById(R.id.pageNameByUser);
            nameEditBox.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "THIS IS THE MAIN PAGE", Toast.LENGTH_SHORT).show();
            Button deleteButton = findViewById(R.id.deleteB);
            deleteButton.setVisibility(View.GONE);
            TextView newName = findViewById(R.id.newPageName);
            newName.setVisibility(View.INVISIBLE);
        }
    }


    /**
     * Adds a new shape to the current page.
     * @param view
     */
    public void addNewShape(View view) {
        if (currPage == null) return;
        if (AllPages.getInstance().getAllPages().containsKey(currPage.getPageName())) {
            Intent intent = new Intent(this, NewShape.class);
            intent.putExtra("Page", currPage.getPageName());
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "PAGE NO LONGER EXISTS", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Renames the page, if the name the user gave is not already used by another page.
     * @param view
     */
    public void renamePage(View view) {
        if (currPage == null) return;
        if (AllPages.getInstance().getAllPages().containsKey(currPage.getPageName())) {
            EditText newName = findViewById(R.id.pageNameByUser);
            String newNameString = newName.getText().toString().toLowerCase();
            HashMap<String, Page> pages = AllPages.getInstance().getAllPages();

            if ((newNameString.equals(MAIN_PAGE) || pages.containsKey(newNameString)) && !newNameString.equals(currPage.getPageName())) {
                Toast.makeText(getApplicationContext(), "PAGE NAME ALREADY IN USE", Toast.LENGTH_SHORT).show();
            } else if (newNameString.contains(" ")) {
                Toast.makeText(getApplicationContext(), "MUST NOT CONTAIN ANY SPACES", Toast.LENGTH_SHORT).show();
            } else if (newNameString.isEmpty()) {
                Toast.makeText(getApplicationContext(), "INVALID PAGE NAME", Toast.LENGTH_SHORT).show();
            } else if (shapes.containsKey(newNameString)) {
                Toast.makeText(getApplicationContext(), "PAGE NAME CANNOT BE SAME AS SHAPE NAME", Toast.LENGTH_SHORT).show();
            } else {
                String pageName = currPage.getPageName();

                final HashMap<String, Shape> allShapes = AllShapes.getInstance().getAllShapes();
                Iterator<String> it = allShapes.keySet().iterator();

                while (it.hasNext()) {
                    String shapeName = it.next();
                    Shape currShape = allShapes.get(shapeName);
                    if (currShape.getAssociatedPage().equals(pageName)) {
                        currShape.setAssociatedPage(newNameString);
                    }
                }

                pages.remove(pageName);

                // rename pages in scripts
                AllShapes.getInstance().renameObjectInScripts(pageName,newNameString);

                currPage.setPageName(newNameString);
                pages.put(newNameString, currPage);

                // Clears the Edit Text where user can input new page name
                TextView pageNameText = findViewById(R.id.nameOfNewPage);
                pageNameText.setText(newNameString);
                newName.setText("");
            }
        } else {
            Toast.makeText(getApplicationContext(), "PAGE NO LONGER EXISTS", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Deletes the page and all the shapes associated with it.
     * @param
     */
    private void deleteCurrPage() {
        if (currPage == null) return;
        if (AllPages.getInstance().getAllPages().containsKey(currPage.getPageName())) {
            String pageName = currPage.getPageName();

            //deletes the page from AllPages
            AllPages.getInstance().getAllPages().remove(pageName);

            final HashMap<String, Shape> allShapes = AllShapes.getInstance().getAllShapes();
            Iterator<String> it = allShapes.keySet().iterator();

            while (it.hasNext()) {
                String shapeName = it.next();
                if (allShapes.get(shapeName).getAssociatedPage().equals(pageName)) {
                    AllShapes.getInstance().removeObjectFromScripts(shapeName);
                    it.remove();
                }
            }

            // remove all pages from scripts
            AllShapes.getInstance().removeObjectFromScripts(pageName);

            Intent intent = new Intent(this, NewGame.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "PAGE NO LONGER EXISTS", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Goes to an Activity that displays all the shapes associted with the page, which the user
     * can also edit.
     * @param view
     */
    public void displayPageShapes(View view) {
        if (AllPages.getInstance().getAllPages().containsKey(currPage.getPageName())) {
            Intent intent = new Intent(this, ViewAllShapes.class);
            intent.putExtra("Page", currPage.getPageName());
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "PAGE NO LONGER EXISTS", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Goes back to the NewGame Activity, which contains the menu to edit pages, save the game
     * permanently or stop working on the ga,e.
     * @param view
     */
    public void returnToMenu(View view) {
        Intent intent = new Intent(this, NewGame.class);
        startActivity(intent);
    }

    public void openImageHolder(View view) {
        LinearLayout holder = findViewById(R.id.imageHolder);
        holder.setVisibility(View.VISIBLE);
        setUpImageSpinner();
    }

    private void setUpImageSpinner() {
        final Spinner imageSpinner = findViewById(R.id.imageSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item, customImagesNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        imageSpinner.setAdapter(adapter);
        imageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                return;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });
    }

    public void setImage(View view) {
        Spinner imageSpinner = findViewById(R.id.imageSpinner);
        String imageName = imageSpinner.getSelectedItem().toString();
        if(imageName.equals(NO_IMG)) {
            currPage.setBackgroundImageName("");
        } else {
            currPage.setBackgroundImageName(imageSpinner.getSelectedItem().toString());
        }
        LinearLayout holder = findViewById(R.id.imageHolder);
        holder.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(), "Background Image Set", Toast.LENGTH_SHORT).show();
    }

    private void giveWarning () {
        Button returnButton = findViewById(R.id.returnMenuButton);
        returnButton.setVisibility(View.GONE);

        Button shapesButton = findViewById(R.id.pagesShapesButton);
        shapesButton.setVisibility(View.GONE);

        Button addButton = findViewById(R.id.addShapeButton);
        addButton.setVisibility(View.GONE);

        Button deleteButton = findViewById(R.id.deleteB);
        deleteButton.setVisibility(View.GONE);

        LinearLayout rename = findViewById(R.id.nameLayout);
        rename.setVisibility(View.GONE);

        LinearLayout warning = findViewById(R.id.warningsLayout);
        warning.setVisibility(View.VISIBLE);

        LinearLayout options = findViewById(R.id.buttons);
        options.setVisibility(View.VISIBLE);
    }


    public void checkCurrPage(View view) {
        String pageName = currPage.getPageName();
        if (AllPages.getInstance().getAllPages().containsKey(pageName)) {
            final HashMap<String, Shape> allShapes = AllShapes.getInstance().getAllShapes();
            Iterator<String> it = allShapes.keySet().iterator();

            boolean shapeUsed = false;

            ArrayList<String> shapesInPage = new ArrayList<>();
            while (it.hasNext()) {
                String currShapeName = it.next();
                Shape currShape = allShapes.get(currShapeName);
                String currScript  = currShape.getScript();
                for (int i = 0; i < shapesInPage.size(); i++) {
                    if (currScript.contains(shapesInPage.get(i)) && !currShape.getAssociatedPage().equals(pageName)) {
                        giveWarning();
                        shapeUsed = true;
                        break;
                    }
                }

                if (currScript.contains(pageName)) {
                    giveWarning();
                    shapeUsed = true;
                    break;
                }

                if (currShape.getAssociatedPage().equals(pageName)) shapesInPage.add(currShape.getName());
            }
            if (!shapeUsed) deleteCurrPage();
        } else {
            Toast.makeText(getApplicationContext(), "PAGE NO LONGER EXISTS", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelPageDelete(View view) {
        Button returnButton = findViewById(R.id.returnMenuButton);
        returnButton.setVisibility(View.VISIBLE);

        Button shapesButton = findViewById(R.id.pagesShapesButton);
        shapesButton.setVisibility(View.VISIBLE);

        Button addButton = findViewById(R.id.addShapeButton);
        addButton.setVisibility(View.VISIBLE);

        Button deleteButton = findViewById(R.id.deleteB);
        deleteButton.setVisibility(View.VISIBLE);

        LinearLayout rename = findViewById(R.id.nameLayout);
        rename.setVisibility(View.VISIBLE);

        LinearLayout warning = findViewById(R.id.warningsLayout);
        warning.setVisibility(View.GONE);

        LinearLayout options = findViewById(R.id.buttons);
        options.setVisibility(View.GONE);
    }

    public void deletePage(View view) {
        deleteCurrPage();
    }
}
