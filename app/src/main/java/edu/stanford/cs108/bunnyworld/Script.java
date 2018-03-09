package edu.stanford.cs108.bunnyworld;

import java.util.Set;

/**
 * Script Triggers
 * Created by vymai on 2/26/18.
 */

public class Script {
    // ACTION KEYWORDS
    public static final String GO_TO = "goto";
    public static final String PLAY = "play";
    public static final String SHOW = "show";
    public static final String HIDE = "hide";
    // EVENT KEYWORDS
    public static final String ON_CLICK = "on click";
    public static final String ON_DROP = "on drop";
    public static final String ON_ENTER = "on enter";

    /**
     * Finds the trigger clause for specified event in given
     * Script. Returns null if trigger clause does not exist
     * in Script.
     * @param event
     * @param script
     * @return Script
     */
    private String getClause(String event, String script) {
        if (!script.contains(event)) return null;

        int start = script.indexOf(event);
        script = script.substring(start + event.length() + 1);

        int end = script.indexOf(";");
        script = script.substring(0,end);
        System.out.println(script);
        return script;
    }

    /**
     * Assumes that clause has a legal form, meaning a proper
     * modifier always follows a verb. Event actions (on click,etc)
     * have also been truncated. Performs actions found
     * in clause from left to right.
     * @param clause
     */
    private void runClause(String clause) {
        if (clause == null) return;

        String [] words = clause.split(" ");

        // loops through words in clause two at a time
        for (int curr = 0; curr < words.length; curr+=2) {
            String verb = words[curr];
            String modifier = words[curr+1];

            if (verb.equals(GO_TO)) {
                goTo(modifier);
            } else if (verb.equals(PLAY)) {
                play(modifier);
            } else if (verb.equals(HIDE)) {
                hide(modifier);
            } else if (verb.equals(SHOW)) {
                show(modifier);
            }
        }
    }

    /**
     * Defines actions when shape is clicked. Only executes
     * first on-click clause, as mentioned in spec.
     * @param shape
     */
    public void clickHappened(Shape shape) {
        if (shape == null) return;

        String script = shape.getScript();
        String clause = getClause(ON_CLICK,script);

        runClause(clause);
    }

    public void enteredPage(Page page) {
        if (page == null) return;

        String page_name = page.getPageName();

        // looping through shapes and checking for page matches
        for (Shape shape: AllShapes.getInstance().getAllShapes().values()) {
            if (shape.getAssociatedPage() == page_name) {
                String clause = getClause(ON_ENTER, shape.getScript());
                runClause(clause);
            }
        }
    }

    /**
     * Defines actions for shapeDroppedOnto when droppedShape is dropped
     * onto shapeDroppedOnto.
     * Assumes that there is only one on-drop clause for each possible
     * dropped shape.
     * @param shapeDroppedOnto
     * @param droppedShape
     */
    public void shapeDropped(Shape shapeDroppedOnto, Shape droppedShape) {
        if (droppedShape == null || shapeDroppedOnto == null) return;

        String script = shapeDroppedOnto.getScript();
        String onDrop = ON_DROP+" "+droppedShape.getName()+" ";
        String clause = getClause(onDrop,script);

        runClause(clause);
    }

    /**
     *
     * CustomView Functions
     *
     */

    /**
     * Switch to show the page of given name
     * @param page_name
     */
    private void goTo(String page_name) {

        System.out.printf("goto(%s) called\n",page_name);
    }

    /**
     * Play the sound of the given name
     * @param sound_name
     */
    private void play(String sound_name) {
        // TODO IMPLEMENT
        System.out.printf("play(%s) called\n",sound_name);
    }

    /**
     * Make the given shape invisible and un-clickable.
     * @param shape_name
     */
    private void hide(String shape_name) {
        // TODO IMPLEMENT
        System.out.printf("hide(%s) called\n",shape_name);
    }

    /**
     * Make the given shape visible and active.
     * @param shape_name
     */
    private void show(String shape_name) {
        // TODO IMPLEMENT
        System.out.printf("show(%s) called\n",shape_name);
    }

}
