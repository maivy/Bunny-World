package edu.stanford.cs108.bunnyworld;
import java.util.HashMap;

class AllPages {
    private String gameName;
    private static final AllPages ourInstance = new AllPages();
    private HashMap<String, Page> allCurrPages;
    private int currPageNumber;

    static AllPages getInstance() {
        return ourInstance;
    }

    private AllPages() {
        allCurrPages = new HashMap<>();
        currPageNumber = 1;
        gameName = "";
    }

    public void nameGame (String newName) {gameName = newName;}
    public String getGameName () {return gameName;}
    public HashMap<String, Page> getAllPages() {
        return allCurrPages;
    }

    public int getCurrPageNumber () {
        return currPageNumber;
    }

    public void updateCurrPageNumber () {
        currPageNumber++;
    }

    public void setCurrPageNumber(int currPageNumber) {
        this.currPageNumber = currPageNumber;
    }
    public void setAllCurrPages(HashMap<String, Page> allCurrPages) {
        this.allCurrPages = allCurrPages;
    }
}
