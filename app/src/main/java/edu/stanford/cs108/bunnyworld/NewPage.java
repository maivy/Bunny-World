package edu.stanford.cs108.bunnyworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Predicate;

public class NewPage extends AppCompatActivity {
    private static final String MAIN_PAGE = "page1";
    private Page currPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_page);
        initPage(this.getIntent());
    }

    private void initPage (Intent intent) {
        boolean isNewPage = intent.getBooleanExtra("NEW_PAGE", true);
        String currPageName;

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
            renameButton.setClickable(false);
            EditText nameEditBox = findViewById(R.id.pageNameByUser);
            nameEditBox.setEnabled(false);
            Toast.makeText(getApplicationContext(), "THIS IS THE MAIN PAGE", Toast.LENGTH_SHORT).show();
            Button deleteButton = findViewById(R.id.deleteB);
            deleteButton.setClickable(false);
        }
    }


    /**
     * Adds a new shape to the current page.
     * @param view
     */
    public void addNewShape(View view) {
        Intent intent = new Intent(this, NewShape.class);
        intent.putExtra("Page", currPage.getPageName());
        startActivity(intent);
    }


    /**
     * Renames the page, if the name the user gave is not already used by another page.
     * @param view
     */
    public void renamePage(View view) {
        EditText newName = findViewById(R.id.pageNameByUser);
        String newNameString = newName.getText().toString().toLowerCase();
        HashMap<String, Page> pages = AllPages.getInstance().getAllPages();

        if ((newNameString.equals(MAIN_PAGE) || pages.containsKey(newNameString)) && !newNameString.equals(currPage.getPageName())) {
            Toast.makeText(getApplicationContext(), "INVALID PAGE NAME", Toast.LENGTH_SHORT).show();
        } else {
            String currName = currPage.getPageName();
            pages.remove(currName);

            currPage.setPageName(newNameString);
            pages.put(newNameString, currPage);

            TextView pageName = findViewById(R.id.nameOfNewPage);
            pageName.setText(newNameString);
            newName.setText("");
        }
    }

    /**
     * Deletes the page and all the shapes associated with it.
     * @param view
     */
    public void deleteCurrPage(View view) {
        String pageName = currPage.getPageName();


        //deletes the page from AllPages
        AllPages.getInstance().getAllPages().remove(pageName);

        final HashMap<String, Shape> allShapes = AllShapes.getInstance().getAllShapes();
        Iterator<String> it = allShapes.keySet().iterator();

        while (it.hasNext()) {
            String shapeName = it.next();
            if (allShapes.get(shapeName).getAssociatedPage().equals(pageName)) {
                it.remove();
            }

        }
        Intent intent = new Intent(this, NewGame.class);
        startActivity(intent);
    }

    /**
     * Goes to an Activity that displays all the shapes associted with the page, which the user
     * can also edit.
     * @param view
     */
    public void displayPageShapes(View view) {
        Intent intent = new Intent(this, ViewAllShapes.class);
        intent.putExtra("Page", currPage.getPageName());
        startActivity(intent);
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
}
