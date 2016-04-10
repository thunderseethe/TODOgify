package com.thunderseethe.todogfy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Mati on 4/9/2016.
 */
public class Percentage extends View {
    public Percentage(Context context) {
        super(context);
    }

    public Percentage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Percentage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        Paint myPaint=new Paint();
        myPaint.setColor(Color.rgb(255, 255, 255));
        myPaint.setStrokeWidth(10);

        canvas.drawRect(30,66,80,80,myPaint);
    }

}
