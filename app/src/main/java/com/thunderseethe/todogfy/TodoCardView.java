package com.thunderseethe.todogfy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import org.w3c.dom.Attr;

/**
 * Created by dead on 4/9/16.
 */
public class TodoCardView extends CardView {
    private boolean strikethrough;
    private final Paint strikethrough_paint;

    public TodoCardView(Context context) {
        this(context, null, 0);
    }

    public TodoCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TodoCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        strikethrough_paint = new Paint();
        strikethrough_paint.setColor(Color.BLACK);
        strikethrough_paint.setStrokeWidth(3.0f);
        strikethrough_paint.setAntiAlias(true);

        if (attrs == null) return;

    }

    public void strikethrough(boolean _strikethrough){
        strikethrough = _strikethrough;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final int height = getHeight();
        final int width = getWidth();

        if(strikethrough)
            canvas.drawLine(20, height / 2, width - 20, height / 2, strikethrough_paint);

    }
}
