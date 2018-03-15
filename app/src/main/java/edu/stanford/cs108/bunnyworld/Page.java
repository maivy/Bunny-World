package edu.stanford.cs108.bunnyworld;

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

}
