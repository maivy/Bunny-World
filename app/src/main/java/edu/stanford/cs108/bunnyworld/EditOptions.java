package edu.stanford.cs108.bunnyworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class EditOptions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_options);
        init();
    }

    @Override
    protected void onResume () {
        super.onResume();
        init();
    }
    
    /**
     * Initializes the activity by displaying all the shapes in the current page.
     */
    private void init () {
        ListView currPagesView = findViewById(R.id.editViewPages);
        final HashMap<String, Page> currPages = AllPages.getInstance().getAllPages();

        final ArrayList<String> allPageNames  = new ArrayList<>();
        for (String key : currPages.keySet()) {
            allPageNames.add(key);
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, allPageNames);
        currPagesView.setAdapter(adapter);
        currPagesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editPage(allPageNames.get(i));
            }
        });
    }


    /**
     *  Goes to a new activity to edit the page.
     * @param pageNameString
     */
    private void editPage(String pageNameString) {
            Intent intent = new Intent(this, NewPage.class);
            intent.putExtra("NEW_PAGE", false);
            intent.putExtra("pageName", pageNameString);
            startActivity(intent);
    }
}
