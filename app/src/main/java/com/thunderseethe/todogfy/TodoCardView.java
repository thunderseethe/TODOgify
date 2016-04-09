package com.thunderseethe.todogfy;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import org.w3c.dom.Attr;

/**
 * Created by dead on 4/9/16.
 */
public class TodoCardView extends CardView {
    private boolean strikethrough;

    public TodoCardView(Context context) {
        this(context, null, 0);
    }

    public TodoCardView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public TodoCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*final int height = getHeight();
        if(strikethrough)
            canvas.drawLine();*/

    }
}
