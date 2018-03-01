package edu.stanford.cs108.bunnyworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class EditOptions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_options);
    }


    public void editPage(View view) {
        EditText pageName = findViewById(R.id.pageToEdit);
        String pageNameString = pageName.getText().toString();

        HashMap<String, Page> pages = AllPages.getInstance().getAllPages();
        if (pages.containsKey(pageNameString)) {
            Intent intent = new Intent(this, NewPage.class);
            intent.putExtra("NEW_PAGE", false);
            intent.putExtra("pageName", pageNameString);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "DOES NOT EXIST", Toast.LENGTH_SHORT).show();
        }
    }


    //displays all the pages currently available in the game
    public void viewAllPages(View view) {
        ListView currPagesView = findViewById(R.id.editViewPages);
        HashMap<String, Page> currPages = AllPages.getInstance().getAllPages();

        ArrayList<String> allPageNames  = new ArrayList<>();
        for (String key : currPages.keySet()) {
            allPageNames.add(key);
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, allPageNames);
        currPagesView.setAdapter(adapter);
        currPagesView.setVisibility(View.VISIBLE);
        findViewById(R.id.pagesTitleInEdit).setVisibility(View.VISIBLE);
    }

    public void addPage(View view) {
        Intent intent = new Intent(this, NewPage.class);
        intent.putExtra("NEW_PAGE", true);
        startActivity(intent);
    }
}
