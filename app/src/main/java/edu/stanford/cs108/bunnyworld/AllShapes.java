package edu.stanford.cs108.bunnyworld;

import java.util.HashMap;

class AllShapes {
    private static final AllShapes ourInstance = new AllShapes();
    private HashMap<String, Shape> currShapes;
    private int currShapeNumber;
    public static AllShapes getInstance() {
        return ourInstance;
    }

    private AllShapes() {
        currShapes = new HashMap<>();
        currShapeNumber = 1;
    }

    public HashMap<String, Shape> getAllShapes () {
        return currShapes;
    }

    public void addShape(Shape shape) {
        currShapes.put(shape.getName(), shape);
    }

    public void updateCurrShapeNumber (){
        currShapeNumber++;
    }

    public int getCurrShapeNumber () {
        return currShapeNumber;
    }
}
