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

    private HashMap<String, Uri> images;
    private HashMap<String, BitmapDrawable> bitmapDrawables;

    private CustomImages() {
        images = new HashMap<>();
        bitmapDrawables = new HashMap<>();
    }

    public void setImages(HashMap<String, Uri> images) {
        this.images = images;
    }

    public HashMap<String, Uri> getImages() {
        return images;
    }
    public HashMap<String, BitmapDrawable> getBitmapDrawables() {
        return bitmapDrawables;
    }

    //https://stackoverflow.com/questions/17356312/converting-of-uri-to-string
    //helper to load in to database
    public static String uriToString(Uri uri) {
        return uri.toString();
    }

    public static Uri stringToUri(String str) {
        return Uri.parse(str);
    }
}