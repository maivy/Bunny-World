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

    public static final float SHAPE_SHRINK_WIDTH = 120;
    public static final float SHAPE_SHRINK_HEIGHT = 120;
    public static final float FONT_SHRINK = 30;


    String name;
    String associatedPage;
    String script;
    String imageName;
    BitmapDrawable image;
    String text;
    int fontSize;
    public static Paint greenBorder;
    public static Paint lightGrey;
    Paint textPaint;
    boolean isHidden;
    boolean isMovable;
    float x;
    float y;
    float width;
    float height;

    public Shape(String page, String name, float x, float y, float width, float height, boolean hidden, boolean movable, String imageName, BitmapDrawable image, String text, String script, int fontSize){
        associatedPage = page;
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.script = script;
        this.isHidden = hidden;
        this.isMovable = movable;
        this.imageName = imageName;
        this.image = image;
        this.text = text;
        this.fontSize = fontSize;
        lightGrey = new Paint();
        lightGrey.setColor(Color.LTGRAY);
        greenBorder = new Paint();
        greenBorder.setStyle(Paint.Style.STROKE);
        greenBorder.setStrokeWidth(15.0f);
        greenBorder.setColor(Color.GREEN);
        textPaint = new Paint();
    }

    public void addToScript (String newScript) {
        script += newScript;
    }

    public void draw(Canvas canvas, boolean withBorder) {
        if (!text.equals("")) drawText(canvas, withBorder);
        else if (image != null) drawImage(canvas, withBorder);
        else drawRect(canvas, withBorder);
    }

    public void shrinkDraw(Canvas canvas) {
        if (!text.equals("")) shrinkDrawText(canvas);
        else if (image != null) shrinkDrawImage(canvas);
        else shrinkDrawRect(canvas);
    }

    //text shapes ignore the width and height given by the user and is only as wide and high as the text
    private void drawText(Canvas canvas, boolean withBorder) {
        textPaint.setTextSize(fontSize);
        float width = textPaint.measureText(text);
        canvas.drawText(text, x, y, textPaint);
        if(withBorder) {
            RectF border = new RectF(x - 15, y + textPaint.ascent(), x + textPaint.measureText(text) + 15, y + textPaint.descent());
            canvas.drawRect(border, greenBorder);
        }
    }

    private void shrinkDrawText(Canvas canvas) {
        textPaint.setTextSize(FONT_SHRINK);
        float width = textPaint.measureText(text);
        canvas.drawText(text, x, y, textPaint);
    }

    private void drawImage(Canvas canvas, boolean withBorder) {
        RectF rect = new RectF(x, y, x + width, y + height);
        if(withBorder) {
            canvas.drawRect(rect, greenBorder);
        }
        canvas.drawBitmap(image.getBitmap(), null, rect, null);
    }

    private void shrinkDrawImage(Canvas canvas) {
        RectF rect = new RectF(x, y, x + SHAPE_SHRINK_WIDTH, y + SHAPE_SHRINK_HEIGHT);
        canvas.drawBitmap(image.getBitmap(), null, rect, null);
    }

    private void drawRect(Canvas canvas, boolean withBorder) {
        RectF rect = new RectF(x, y, x + width, y + height);
        if(withBorder) {
            canvas.drawRect(rect, greenBorder);
        }
        canvas.drawRect(rect, lightGrey);
    }

    private void shrinkDrawRect(Canvas canvas) {
        RectF rect = new RectF(x, y, x + SHAPE_SHRINK_WIDTH, y + SHAPE_SHRINK_HEIGHT);
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
    }

    public BitmapDrawable getImage() {
        return image;
    }

    public String getImageName() { return imageName; }

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

    public boolean isReceiving(String shapeName) {
        return script.contains("on drop " + shapeName);
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public Paint getTextPaint() {
        return textPaint;
    }
}