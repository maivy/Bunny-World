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

    public Shape getCopiedShape() { return copiedShape; }

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

    private String removeObjectFromClause(String clause, String objName) {
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

            for (int currVerb = start; currVerb < words.length; currVerb += 2) {
                String verb = words[currVerb];
                String modifier = words[currVerb + 1];
                if (!modifier.equals(objName)) {
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

    /**
     * Removes given shape or page object from every shape script
     * @param objName
     */
    public void removeObjectFromScripts(String objName) {
        for (Shape shape: currShapes.values()) {
            String script = shape.getScript();
            if (!script.contains(objName)) break;
            String newScript = "";
            while (script.contains(objName)) {
                int semicolon = script.indexOf(";");
                String clause = script.substring(0,semicolon).trim();

                String newClause  = clause + " ; ";
                if (clause.contains(objName)) {
                    newClause = removeObjectFromClause(clause,objName);
                }

                newScript = newScript.concat(newClause);
                script = script.substring(semicolon+1);
            }
            shape.setScript(newScript);
        }
    }

    // TODO: remove page from scripts as well
}
