package com.example.game2d.object;

import static com.example.game2d.Game.getScreenHeight;
import static com.example.game2d.Game.getScreenWidth;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.game2d.GameLoop;
import com.example.game2d.Joystick;
import com.example.game2d.R;

/**
 * Player is the circular character that the user can control with a touch joystick.
 * Player is an extension of a Circle, which is an extension of a GameObject
 */
public class Player extends Circle {
    Bitmap sprite;
    // max speed
    public static final double SPEED_PIXELS_PER_SECOND = 300.0;
    // dimensional analysis lol gives you pixels per update
    public static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private final Joystick joystick;
    private Context context;
    // create as doubles for higher precision

    // player constructor
    public Player(Context context, Joystick joystick, float positionX, float positionY, double radius) {
        // specify parent constructor because there is no default no arg one
        // player color defined in colors xml
        super(context, ContextCompat.getColor(context, R.color.player), positionX, positionY, radius);
        this.joystick = joystick;
        Bitmap original = BitmapFactory.decodeResource(context.getResources(), R.drawable.spinable);
        sprite = Bitmap.createScaledBitmap(original, 50, 50, false);
        this.context = context;
    }

    public void update() {
        // update velocity based on joystick actuator
        velocityX = joystick.getActuatorX()*MAX_SPEED;
        velocityY = joystick.getActuatorY()*MAX_SPEED;

        // later can abstract out position updates
        // Don't allow to go off screen
        int height = getScreenHeight();
        int width = getScreenWidth();
        if (positionX >= width) {
            positionX = width;
        }
        if (positionX <= 0) {
            positionX = 0;
        }
        if (positionY >= height) {
            positionY = height;
        }
        if (positionY <= 0) {
            positionY = 0;
        }

        // update position
        positionX += velocityX;
        positionY += velocityY;
    }

    public void setPosition(float positionX, float positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
        // to log stuff, can use Log.d like so
        // Log.d("Player", "moved");
    }

    public void draw(Canvas canvas) {
        /*
        // draw player circle (cast doubles to floats as required)
        canvas.drawCircle((float) positionX, (float) positionY, (float) radius, paint);
         */
        Paint paint = new Paint();
        canvas.drawBitmap(sprite, positionX, positionY, paint);
    }
}
