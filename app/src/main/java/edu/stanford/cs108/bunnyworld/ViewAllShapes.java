package edu.stanford.cs108.bunnyworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewAllShapes extends AppCompatActivity {

    private String currPageName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_shapes);
        currPageName = this.getIntent().getStringExtra("Page");
        init();
    }

    @Override
    protected void onResume () {
        super.onResume();
        init();
    }

    private void init () {
        TextView currPage = findViewById(R.id.allShapesCurrPage);
        currPage.setText(currPageName);

        HashMap<String, Shape> allPageShapes = AllShapes.getInstance().getAllShapes();
        final ListView currPagesView = findViewById(R.id.allShapesView);

        final ArrayList<String> allShapeNames  = new ArrayList<>();
        for (String key : allPageShapes.keySet()) {
            Shape currShape = allPageShapes.get(key);
            String currLocation = currShape.getAssociatedPage();
            if (currLocation.equals(currPageName)) {
                allShapeNames.add(key);
            }
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, allShapeNames);
        currPagesView.setAdapter(adapter);
        currPagesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * On a click go to the edit shape with the selected shape's name passed through
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = allShapeNames.get(i);
                Intent intent = new Intent(getApplicationContext(), EditShapeOptions.class);
                intent.putExtra("shape", selected);
                startActivity(intent);
            }
        });
    }
}
