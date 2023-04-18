package com.example.game2d;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ChalkGameClass extends View {
    private Bitmap BackgroundImage;
    private boolean touch = false;
    private Paint redPaint = new Paint();

    public ChalkGameClass(Context context) {
        super(context);
        BackgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //canvas.drawBitmap(BackgroundImage, 0, 0, null);

        if (touch) {
            canvas.drawCircle(30, 30, 100, redPaint);
            Intent mainIntent = new Intent(getContext(), ChalkGameActivity.class);
            getContext().startActivity(mainIntent);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_UP)
        {
            touch = true;
        }
        return true;
    }
}
