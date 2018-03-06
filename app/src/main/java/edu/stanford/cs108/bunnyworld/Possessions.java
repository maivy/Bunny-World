package edu.stanford.cs108.bunnyworld;
import java.util.HashSet;

//
class Possessions {

    private static final Possessions ourInstance = new Possessions();
    private HashSet<Shape> allPossessions;
    static Possessions getInstance() {
        return ourInstance;
    }

    private Possessions() {
        allPossessions = new HashSet<Shape>();
    }

    public HashSet<Shape> getAllPossessions() {
        return allPossessions;
    }
}
