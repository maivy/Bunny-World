package edu.stanford.cs108.bunnyworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PlaceShape extends AppCompatActivity {

    public static String page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        page = intent.getStringExtra("pageName");
        setContentView(R.layout.activity_place_shape);
        System.out.println("place shape curr page: " + page);
    }
}
