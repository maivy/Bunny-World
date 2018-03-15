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

    public void removeShapeFromScripts(String shapeName) {
        for (Shape shape: currShapes.values()) {
            String script = shape.getScript();
            String newScript = "";
            while (script.contains(shapeName)) {
                int semicolon = script.indexOf(";");
                String clause = script.substring(0,semicolon).trim();

                String newClause  = clause + " ; ";
                if (clause.contains(shapeName)) {
                    newClause = removeShapeFromClause(clause,shapeName);
                }

                newScript = newScript.concat(newClause);
                script = script.substring(semicolon+1);
            }
            shape.setScript(newScript);
        }
    }

    private String removeShapeFromClause(String clause, String shapeName) {
        String [] words = clause.split(" ");

        String newClause = "";

        // check if other actions exist
        if (words.length > 5) {
            int start = 2;
            String event = words[0] + " " + words[1];
            if (event.equals("on drop")) {
                event = event.concat(" " + words[2]);
                start = 3;
            }
            newClause = event + " ";
            int retainedActions = 0;


            for (int currAction = start; currAction < words.length; currAction += 2) {
                String verb = words[currAction];
                String modifier = words[currAction + 1];
                if (!modifier.equals(shapeName)) {
                    String action = verb + " " + modifier + " ";
                    newClause = newClause.concat(action);
                    retainedActions++;
                }
            }
            newClause = newClause.concat("; ");

            if (retainedActions == 0) newClause = ""; // reset if all actions omitted
        }
        return newClause;
    }

    // TODO: remove page from scripts as well
}
