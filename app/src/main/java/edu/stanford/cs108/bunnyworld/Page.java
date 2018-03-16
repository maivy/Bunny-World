package edu.stanford.cs108.bunnyworld;

import android.graphics.drawable.BitmapDrawable;

/**
 * edu.stanford.cs108.bunnyworld.Page Class
 */

public class Page {
    private String pageName;
    private String backgroundImageName;

    // Initializes a page with a given name.
    public Page (String name) {
        pageName = name;
        backgroundImageName = ""; // TODO: are we setting a background image when you create a page?
    }

    // Renames a page.
    public void setPageName (String newName) {
        pageName = newName;
        AllPages.getInstance().getAllPages().remove(this);
        AllPages.getInstance().getAllPages().put(pageName, this);
    }

    // Returns the current page name
    public String getPageName() {
        return pageName;
    }

    public void setBackgroundImageName(String backgroundImageName) {
        this.backgroundImageName = backgroundImageName;
    }

    public String getBackgroundImageName() {
        return backgroundImageName;
    }

    /**
     * Returns image associated with background image name or null.
     * @return backgroundImage
     */
    public BitmapDrawable getBackgroundImage() {
        if (backgroundImageName.equals("")) return null;
        BitmapDrawable backgroundImage = BunnyWorldDB.getBitmapDrawable(backgroundImageName);
        return backgroundImage;
    }
}
