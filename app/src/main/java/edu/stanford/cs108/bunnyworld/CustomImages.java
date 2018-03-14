package edu.stanford.cs108.bunnyworld;

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

    private HashMap<String, Uri> images;

    private CustomImages() {
        images = new HashMap<>();
    }

    public HashMap<String, Uri> getImages() {
        return images;
    }
}
