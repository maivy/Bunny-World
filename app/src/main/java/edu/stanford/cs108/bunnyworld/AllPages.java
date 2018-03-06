package edu.stanford.cs108.bunnyworld;
import java.util.HashMap;

class AllPages {
    private static final AllPages ourInstance = new AllPages();
    private HashMap<String, Page> allCurrPages;
    private int currPageNumber;

    static AllPages getInstance() {
        return ourInstance;
    }

    private AllPages() {
        allCurrPages = new HashMap<>();
        currPageNumber = 1;
    }

    public HashMap<String, Page> getAllPages() {
        return allCurrPages;
    }

    public int getCurrPageNumber () {
        return currPageNumber;
    }

    public void updateCurrPageNumber () {
        currPageNumber++;
    }

    public void setAllCurrPages(String game_name) {

    }
}
