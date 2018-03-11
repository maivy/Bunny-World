package edu.stanford.cs108.bunnyworld;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by angel on 3/10/2018.
 */

public class PlaceScreen extends View {

    private final int RADIUS = 30;


    private AllShapes allShapes;
    private HashMap<String, Shape> shapes;
    private HashSet<RectF> circles;
    RectF bottomCircle;
    RectF rightCircle;
    RectF cornerCircle;
    boolean resizingBottom = false;
    boolean resizingSide = false;
    Shape resizeShape;
    private Shape selectedShape;
    private String currPage;
    int viewHeight;
    int viewWidth;
    float inventoryHeight;
    float inventoryMin;
    float x;
    float y;
    float prevX;
    float prevY;
    Paint inventoryPaint;
    Paint inventoryTextPaint;
    Paint circlePaint;

    public PlaceScreen(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        allShapes = AllShapes.getInstance();
        shapes = allShapes.getAllShapes();
        currPage = PlaceShape.page;
        inventoryPaint = new Paint();
        inventoryPaint.setColor(Color.GRAY);
        inventoryTextPaint = new Paint();
        inventoryTextPaint.setTextSize(60);
        circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(Color.rgb(108,207, 255));
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
    }

    public void drawShapes(Canvas canvas) {
        HashSet<Shape> shapes = new HashSet<>(this.shapes.values());
        for(Shape shape : shapes) {
            if(shape.getAssociatedPage().equals(currPage)) {
                shape.draw(canvas, false);
                if(shape == selectedShape) {
                    addCircles(shape, canvas);
                }
            }
        }
    }

    private void addCircles(Shape shape, Canvas canvas) {
        resizeShape = shape;
        float xCenter = shape.getX() + shape.getWidth()/2 - RADIUS;
        float top = shape.getY()  + shape.getHeight() - RADIUS;
        float right = shape.getX() + shape.getWidth() - RADIUS;
        float yMiddle = shape.getY() + shape.getHeight()/2 + - RADIUS;
        float cornerX = shape.getX() + shape.getWidth() - RADIUS;
        float cornerY = shape.getY() + shape.getHeight() - RADIUS;
        bottomCircle = new RectF(xCenter, top, xCenter + 2 * RADIUS, top + 2 * RADIUS);
        rightCircle = new RectF(right, yMiddle, right + 2 * RADIUS, yMiddle + 2 * RADIUS);
        cornerCircle = new RectF(cornerX, cornerY, cornerX + 2 * RADIUS, cornerY + 2 * RADIUS);
        canvas.drawOval(bottomCircle, circlePaint);
        canvas.drawOval(rightCircle, circlePaint);
        canvas.drawOval(cornerCircle, circlePaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawShapes(canvas);
        drawInventory(canvas);
    }

    public Shape getShape() {
        Shape result = null;
        HashSet<Shape> shapes = new HashSet<>(this.shapes.values());
        float leftX;
        float rightX;
        float topY;
        float bottomY;
        if(resizeShape != null) {
            if (x >= bottomCircle.left && x <= bottomCircle.right && y >= bottomCircle.top && y <= bottomCircle.bottom) {
                resizingBottom = true;
                return result;
            }
            if (x >= rightCircle.left && x <= rightCircle.right && y >= rightCircle.top && y <= rightCircle.bottom) {
                resizingSide = true;
                return result;
            }
            if (x >= cornerCircle.left && x <= cornerCircle.right && y >= cornerCircle.top && y <= cornerCircle.bottom) {
                resizingSide = true;
                resizingBottom = true;
                return result;
            }
        }
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
                if(shape.associatedPage.equals(currPage)) {
                        result = shape;
                }
            }
        }
        return result;
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                prevX = x;
                prevY = y;
                Shape shape = getShape();
                selectedShape = shape;
                break;
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                if(selectedShape != null) {
                    if (selectedShape.getText().equals("")) {
                        selectedShape.setX(x - selectedShape.getWidth() * .5f);
                        selectedShape.setY(y - selectedShape.getHeight() * .5f);
                    } else {
                        selectedShape.setX(x - selectedShape.getTextPaint().measureText(selectedShape.getText()) * .5f);
                        selectedShape.setY(y);
                    }
                }
                if(resizingBottom) {
                    if (resizeShape.getText().equals("")) {
                        resizeShape.setHeight(resizeShape.getHeight() + (y - (resizeShape.getY() + resizeShape.getHeight())));
                    }
                }
                if(resizingSide) {
                    if (resizeShape.getText().equals("")) {
                        resizeShape.setWidth(resizeShape.getWidth() + (x - (resizeShape.getWidth() + resizeShape.getX())));
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                resizingBottom = false;
                resizingSide = false;

        }
        invalidate();
        return true;
    }

}
