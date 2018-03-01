package edu.stanford.cs108.bunnyworld;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by angel on 2/25/2018.
 */

public class TestView extends View {

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        BitmapDrawable draw = (BitmapDrawable) getResources().getDrawable(R.drawable.flower);
        Shape shape = new Shape("page1", "shape1", 30.0f, 30.0f, 600.0f, 492.0f, false, false, "flower", draw, "", "on click hide carrot", 0);
        shape.draw(canvas, false);
    }

}
