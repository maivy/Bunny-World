package edu.stanford.cs108.bunnyworld;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;

/**
 * Created by angel on 2/25/2018.
 */

public class Shape {

    String name;
    String associatedPage;
    String script;
    BitmapDrawable image;
    String text;
    int fontSize;
    public static Paint greenBorder;
    public static Paint lightGrey;
    Paint textPaint;
    boolean isHidden;
    boolean isMovable;
    boolean isReceiving;
    float x;
    float y;
    float width;
    float height;

    public Shape(String page, String name,float x, float y, float width, float height, boolean hidden, boolean movable, BitmapDrawable image, String text, String script, int fontSize){
        associatedPage = page;
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.script = script;
        this.isHidden = hidden;
        this.isMovable = movable;
        this.image = image;
        this.text = text;
        this.fontSize = fontSize;
        setReceiving();
        lightGrey = new Paint();
        lightGrey.setColor(Color.LTGRAY);
        greenBorder = new Paint();
        greenBorder.setStyle(Paint.Style.STROKE);
        greenBorder.setStrokeWidth(15.0f);
        greenBorder.setColor(Color.GREEN);
        textPaint = new Paint();
    }

    public void draw(Canvas canvas, boolean withBorder) {
        if(!isHidden) {
            if (!text.equals("")) drawText(canvas, withBorder);
            else if (image != null) drawImage(canvas, withBorder);
            else drawRect(canvas, withBorder);
        }
    }

    private void drawText(Canvas canvas, boolean withBorder) {
        textPaint.setTextSize(fontSize);
        canvas.drawText(text, x, y, textPaint);
    }

    private void drawImage(Canvas canvas, boolean withBorder) {
        RectF rect = new RectF(x, y, x + width, y + height);
        if(withBorder && isReceiving) {
            canvas.drawRect(rect, greenBorder);
        }
        canvas.drawBitmap(image.getBitmap(), null, rect, null);
    }

    private void drawRect(Canvas canvas, boolean withBorder) {
        RectF rect = new RectF(x, y, x + width, y + height);
        if(withBorder) {
            canvas.drawRect(rect, greenBorder);
        }
        canvas.drawRect(rect, lightGrey);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssociatedPage() {
        return associatedPage;
    }

    public void setAssociatedPage(String associatedPage) {
        this.associatedPage = associatedPage;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public boolean isMovable() {
        return isMovable;
    }

    public void setMovable(boolean movable) {
        isMovable = movable;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
        setReceiving();
    }

    public BitmapDrawable getImage() {
        return image;
    }

    public void setImage(BitmapDrawable image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public boolean isReceiving() {
        return isReceiving;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setReceiving() {
        if(script != null && !script.equals("")) {
            if(script.substring(0, 7).indexOf("drop") != -1) {
                isReceiving = true;
            }
        }
        isReceiving = false;
    }
}
