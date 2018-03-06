package edu.stanford.cs108.bunnyworld;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Custom View for Bunny World
 */

public class Screen extends View {
    public static final String INVENTORY = "inventory";

    boolean dragging = false;
    Script script;
    private String currPage;
    private String prevPage;
    private float x;
    private float y;
    private float prevX;
    private float prevY;
    private int viewHeight;
    private int viewWidth;
    private float inventoryHeight;
    private float inventoryMin;
    private Shape dragShape;
    private AllShapes allShapes;
    private HashMap<String, Shape> shapes;
    private AllPages allPages;
    private HashMap<String, Page> pages;
    private Possessions allPossessions;
    private HashSet<Shape> possessions;
    Paint inventoryPaint;
    Paint inventoryTextPaint;

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
            String rest = script.substring(start + event.length() + 1);

            int end = rest.indexOf(";");
            rest = rest.substring(0,end);
            System.out.println(rest);
            return rest;
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

        public void enteredPage(String page) {
            // looping through shapes and checking for page matches
            for (Shape shape: AllShapes.getInstance().getAllShapes().values()) {
                if (shape.getAssociatedPage().equals(page)) {
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
            String onDrop = ON_DROP + " " + droppedShape.getName();
            String clause = getClause(onDrop, script);
            //snaps back if the shape was dropped on something that it doesn't interact with
            if(clause == null) {
                dragShape.setX(prevX);
                dragShape.setY(prevY);
                return;
            }
            runClause(clause);
        }

        /**
         * Switch to show the page of given name
         * @param page_name
         */
        private void goTo(String page_name) {
            prevPage = currPage;
            currPage = page_name;
            if(!prevPage.equals(currPage)){
                enteredPage(currPage);
            }
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
        script.enteredPage(currPage);
        allShapes = AllShapes.getInstance();
        shapes = allShapes.getAllShapes();
        allPages = AllPages.getInstance();
        Possessions allPossessions = Possessions.getInstance();
        possessions = allPossessions.getAllPossessions();
        inventoryPaint = new Paint();
        inventoryPaint.setColor(Color.GRAY);
        inventoryTextPaint = new Paint();
        inventoryTextPaint.setTextSize(60);
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

    //method I have been using to create test objects
    private void testMethod() {
        BitmapDrawable draw = (BitmapDrawable) getResources().getDrawable(R.drawable.carrot);
        shapes.put("shape1", new Shape("page1", "shape1", 30.0f, 30.0f, 600.0f, 492.0f, false, true, "carrot", draw, "", "on enter play munching; on drop shape3 hide shape2", 0));
        shapes.put("shape2", new Shape("page1", "shape2", 30.0f, 600.0f, 600.0f, 220.0f, false, true, "carrot", draw, "hi there", "on drop shape1 play hooray hide shape2;", 48));
        shapes.put("shape3", new Shape("page2", "shape3", 30.0f, 30.0f, 40.0f, 20.0f, true, false, "", null, "", "on click goto page1 play carrotcarrotcarrot; on enter play fire;", 48));

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewHeight = h;
        viewWidth = w;
        inventoryHeight = viewHeight * 0.75f;
        inventoryMin = inventoryHeight + 60;
    }

    private void drawInventory(Canvas canvas) {
        RectF rect = new RectF(0f, inventoryHeight, viewWidth, viewHeight);
        canvas.drawRect(rect, inventoryPaint);
        canvas.drawText("Inventory", 0, inventoryHeight + 45, inventoryTextPaint);
        Iterator<Shape> it = possessions.iterator();
        while(it.hasNext()){
            Shape shape = it.next();
            shape.draw(canvas, false);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawShapes(canvas);
        drawInventory(canvas);
    }

    //returns a shape at a point if it's not hidden
    //if dropEvent is true, it means a shape was dropped, so we want to ignore that shape when iterating
    //through the shapes that lie at a x and y
    public Shape getShape(boolean dropEvent) {
        Shape result = null;
        HashSet<Shape> shapes = new HashSet<>(this.shapes.values());
        float leftX;
        float rightX;
        float topY;
        float bottomY;
        for(Shape shape : shapes) {
                leftX = shape.getX();
                //need to check bounds differently depending on the type of shape
            if(shape.getText().equals("")) {
                topY = shape.getY();
                rightX = leftX + shape.getWidth();
                bottomY = topY + shape.getHeight();
            } else {
                topY = shape.getY() + shape.getTextPaint().ascent();
                rightX = leftX + shape.getTextPaint().measureText(shape.getText());
                bottomY = shape.getY() + shape.getTextPaint().descent();
            }
            if(x >= leftX && x <= rightX && y >= topY && y <= bottomY) {
                if(!shape.isHidden() && shape.associatedPage.equals(currPage) || shape.associatedPage.equals(INVENTORY)) {
                    if(!dropEvent || shape != dragShape) {
                        result = shape;
                    }
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
                Shape shape = getShape(false);
                if(shape != null && shape.isMovable()) {
                    dragShape = shape;
                    //allows us to snap back later
                    prevX = dragShape.getX();
                    prevY = dragShape.getY();
                }
                script.clickHappened(shape);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (dragShape != null) {
                    //makes sure that if I am dragging a shape and it clicks to another page, that shape
                    //isn't considered to be dragging anymore
                    if (dragShape.associatedPage.equals(currPage)) {
                        dragging = true;
                    }
                    x = event.getX();
                    y = event.getY();
                    //drags shape in the middle
                    if (dragShape.getText().equals("")) {
                        dragShape.setX(x - dragShape.getWidth() * .5f);
                        dragShape.setY(y - dragShape.getHeight() * .5f);
                    } else {
                        dragShape.setX(x - dragShape.getTextPaint().measureText(dragShape.getText()) * .5f);
                        dragShape.setY(y);
                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if(dragShape != null) {
                    if(y > inventoryHeight) {
                        dragShape.setAssociatedPage("inventory");
                        if(dragShape.getText().equals("")) {
                            dragShape.setX(x - dragShape.getWidth() * .5f);
                            //lies well within the inventory box
                            dragShape.setY(Math.max(y - dragShape.getHeight() * .5f, inventoryMin));
                        } else {
                            dragShape.setX(x - dragShape.getTextPaint().measureText(dragShape.getText()) * .5f);
                            dragShape.setY(Math.max(y, inventoryMin + 30));
                        }
                        possessions.add(dragShape);
                    } else {
                        dragShape.setAssociatedPage(currPage);
                        Shape receiver = getShape(true);
                        if (receiver != null) {
                            script.shapeDropped(receiver, dragShape);
                        } else {
                            //shape snaps back to where it was before
                            dragShape.setX(prevX);
                            dragShape.setY(prevY);
                        }
                        dragShape = null;
                        dragging = false;
                    }
                    invalidate();

                }
        }
        return true;
    }
}

