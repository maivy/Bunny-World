package edu.stanford.cs108.bunnyworld;

import java.util.HashMap;

class AllShapes {
    private static final AllShapes ourInstance = new AllShapes();
    private HashMap<String, Shape> currShapes;
    private int currShapeNumber;
    static AllShapes getInstance() {
        return ourInstance;
    }
    private Shape copiedShape;

    private AllShapes() {
        currShapes = new HashMap<>();
        currShapeNumber = 1;
        copiedShape = null;
    }


    public void setCopiedShape(Shape newShape) {
        copiedShape = newShape;
    }

    public Shape getCopiedShape() {return copiedShape;}


    public HashMap<String, Shape> getAllShapes () {
        return currShapes;
    }

    public void updateCurrShapeNumber (){
        currShapeNumber++;
    }

    public int getCurrShapeNumber () {
        return currShapeNumber;
    }

    public void setCurrShapeNumber(int currShapeNumber) {
        this.currShapeNumber = currShapeNumber;
    }

    public void setCurrShapes(HashMap<String, Shape> currShapes) {
        this.currShapes = currShapes;
    }

    public void clearAllShapes() {
        currShapes.clear();
        currShapeNumber = 1;
    }
}
