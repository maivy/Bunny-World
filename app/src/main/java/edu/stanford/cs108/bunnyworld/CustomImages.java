package edu.stanford.cs108.bunnyworld;

import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;

import java.util.HashMap;

/**
 * Created by angel on 3/13/2018.
 */

class CustomImages {
    private static final CustomImages ourInstance = new CustomImages();

    public static CustomImages getInstance() {
        return ourInstance;
    }

    private HashMap<String, BitmapDrawable> bitmapDrawables;

    private CustomImages() {
        bitmapDrawables = new HashMap<>();
    }

    public void setBitmapDrawables(HashMap<String, BitmapDrawable> bitmapDrawables) {
        this.bitmapDrawables = bitmapDrawables;
    }

    public HashMap<String, BitmapDrawable> getBitmapDrawables() {
        return bitmapDrawables;
    }

}
