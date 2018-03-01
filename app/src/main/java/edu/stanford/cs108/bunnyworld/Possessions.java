package edu.stanford.cs108.bunnyworld;
import java.util.HashSet;

//
public class Possessions {


    // TODO: REMOVE temp class
    // temp class shape to test it
    class Shape {}

    private HashSet<Shape> allPossessions;

    public Possessions (){
        allPossessions = new HashSet<Shape>();
    }


    // Adds the given Shape to the HashSet of
    // all possessions if it's not null
    public void addShape (Shape newShape) {
        if (newShape != null) {
            allPossessions.add(newShape);
        }
    }


    // Removes a Shape from the HashSet of all
    // possessions if the HashSet contains it
    public void removeShape (Shape shapeToRemove) {
        if (allPossessions.contains(shapeToRemove)) {
            allPossessions.remove(shapeToRemove);
        }
    }
}
