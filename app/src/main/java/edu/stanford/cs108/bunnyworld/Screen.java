package edu.stanford.cs108.bunnyworld;

import android.content.Context;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Custom View for Bunny World
 */

public class Screen extends View {
    private String currPage;

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
         * dropped shape. TODO check if this is okay with group
         * @param shapeDroppedOnto
         * @param droppedShape
         */
        public void shapeDropped(Shape shapeDroppedOnto, Shape droppedShape) {
            if (droppedShape == null || shapeDroppedOnto == null) return;

            String script = shapeDroppedOnto.getScript();
            String onDrop = ON_DROP + " " + droppedShape.getName() + " ";
            String clause = getClause(onDrop, script);

            runClause(clause);
        }

        /**
         * Switch to show the page of given name
         * @param page_name
         */
        private void goTo(String page_name) {
            currPage = page_name;
            System.out.printf("goto(%s) called\n",page_name);
        }

        /**
         * Play the sound of the given name.
         * Assuming that the sound_name is legal.
         * @param sound_name
         */
        private void play(String sound_name) {
            int sound = 0;
            if (sound_name.equals("carrotcarrotcarrot")) sound = R.raw.carrotcarrotcarrot;
            else if (sound_name.equals("evillaugh")) sound = R.raw.evillaugh;
            else if (sound_name.equals("fire")) sound = R.raw.fire;
            else if (sound_name.equals("hooray")) sound = R.raw.evillaugh;
            else if (sound_name.equals("munch")) sound = R.raw.munch;
            else if (sound_name.equals("munching")) sound = R.raw.munching;
            else if (sound_name.equals("woof")) sound = R.raw.woof;
            MediaPlayer mp = MediaPlayer.create(getContext(),sound);
            mp.start();
            System.out.printf("play(%s) called\n",sound_name);
        }

        /**
         * Make the given shape invisible and un-clickable.
         * @param shape_name
         */
        private void hide(String shape_name) {
            Shape toHide = AllShapes.getInstance().getAllShapes().get(shape_name);
            toHide.setHidden(true);
            toHide.setMovable(false);
            System.out.printf("hide(%s) called\n",shape_name);
        }

        /**
         * Make the given shape visible and active.
         * @param shape_name
         */
        private void show(String shape_name) {
            Shape toShow = AllShapes.getInstance().getAllShapes().get(shape_name);
            toShow.setHidden(false);
            toShow.setMovable(false);
            System.out.printf("show(%s) called\n",shape_name);
        }

    }

    public Screen(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        script = new Script();
        currPage = "page1";
        allShapes = AllShapes.getInstance();
        shapes = allShapes.getAllShapes();
        allPages = AllPages.getInstance();
        testMethod();
    }

    public void drawShapes(Canvas canvas) {
        HashSet<Shape> shapes = new HashSet<>(this.shapes.values());
        for(Shape shape : shapes) {
            if(shape.getAssociatedPage().equals(currPage)) {
                shape.draw(canvas, dragging);
            }
        }
    }

    private void testMethod() {
        BitmapDrawable draw = (BitmapDrawable) getResources().getDrawable(R.drawable.flower);
        shapes.put("shape1", new Shape("page1", "shape1", 30.0f, 30.0f, 600.0f, 492.0f, false, false, "flower", draw, "", "on click hide shape2;", 0));
        shapes.put("shape2", new Shape("page1", "shape2", 30.0f, 600.0f, 600.0f, 220.0f, false, false, "flower", draw, "hi there", "on click goto page2 show shape3;", 48));
        shapes.put("shape3", new Shape("page2", "shape3", 30.0f, 30.0f, 40.0f, 20.0f, true, false, "", null, "", "on click goto page1 play carrotcarrotcarrot;", 48));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawShapes(canvas);
    }

    //returns a shape at a point if it's not hidden
    private Shape getShape() {
        Shape result = null;
        HashSet<Shape> shapes = new HashSet<>(this.shapes.values());
        for(Shape shape : shapes) {
            float leftX = shape.getX();
            float rightX = leftX + shape.getWidth();
            float topY = shape.getY();
            float bottomY = topY + shape.getHeight();
            if(x >= leftX && x <= rightX && y >= topY && y <= bottomY) {
                if(!shape.isHidden() && shape.associatedPage.equals(currPage)) {
                    result = shape;
                }
            }
        }
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dragging = false;
                x = event.getX();
                y = event.getY();
                Shape shape = getShape();
                script.clickHappened(shape);
                invalidate();
        }
        return true;
    }

    // TODO when creating the event handler, remember to invalidate() to update screen
}
