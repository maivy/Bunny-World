package edu.stanford.cs108.bunnyworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewAllShapes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_shapes);
        init(this.getIntent().getStringExtra("Page"));
    }


    private void init (String currPageName) {
        HashMap<String, Shape> allPageShapes = AllShapes.getInstance().getAllShapes();
        ListView currPagesView = findViewById(R.id.allShapes);

        ArrayList<String> allShapeNames  = new ArrayList<>();
        for (String key : allPageShapes.keySet()) {
            //TODO: get each Shape and check if it's in the current shape
            Shape currShape = allPageShapes.get(key);
            // String currLocation = currShape.getLocation();
            //if (currLocation.equals(currPageName)) {
            //    allShapeNames.add(key);
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, allShapeNames);
        currPagesView.setAdapter(adapter);
        currPagesView.setVisibility(View.VISIBLE);
    }

    public void editShape(View view) {
        // TODO: edit the given shape if it exists
    }
}
