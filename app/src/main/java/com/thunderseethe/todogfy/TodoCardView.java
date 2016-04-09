package com.thunderseethe.todogfy;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
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
        strikethrough_paint.setAntiAlias(true);
        strikethrough_paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));

        /*if(attrs == null) return;
        strikethrough_paint.setStrokeWidth(attrs.getAttributeFloatValue(R.attr.strikethroughColor, 3.0f));
        strikethrough_paint.setColor(attrs.getAttributeIntValue(R.attr.strikethroughWidth, Color.BLACK));
        */
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TodoCardView);

        strikethrough_paint.setColor(a.getColor(R.styleable.TodoCardView_strikethroughColor, Color.BLACK));
        strikethrough_paint.setStrokeWidth(a.getFloat(R.styleable.TodoCardView_strikethroughWidth, 3.0f));

        a.recycle();
    }

    public void strikethrough(boolean _strikethrough){
        strikethrough = _strikethrough;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        final int height = getHeight();
        final int width = getWidth();

        if(strikethrough)
            canvas.drawLine(20, height / 2, width - 20, height / 2, strikethrough_paint);
    }
}
