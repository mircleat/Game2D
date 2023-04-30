package com.example.game2d.object;

import static com.example.game2d.Game.getScreenHeight;
import static com.example.game2d.Game.getScreenWidth;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.game2d.Game;
import com.example.game2d.GameLoop;
import com.example.game2d.Joystick;
import com.example.game2d.R;

/**
 * Player is an extension of a Circle, which is an extension of a GameObject.
 * It has four bitmaps for left-facing and right-facing idle and walking sprites.
 * The user controls the Player position with a touch joystick.
 */
public class Player extends Circle {
    // User's preference for short-haired or long-haired sprite
    private boolean selectedShortHair;
    // Idle and walking sprites for the right and left
    Bitmap spriteIdleRight;
    Bitmap spriteIdleLeft;
    Bitmap spriteWalkRight;
    Bitmap spriteWalkLeft;
    Bitmap currentSprite;
    Bitmap previousDirection;
    // Room collision boundaries
    private final double relativeMinYpercent = 0.27;
    private final double relativeMaxYpercent = 0.65;
    private final int relativeMinYpixels = (int) (getScreenHeight() * relativeMinYpercent);
    private final int relativeMaxYpixels = (int) (getScreenHeight() * relativeMaxYpercent);
    private final double relativeMinXpercent = 0.04;
    private final double relativeMaxXpercent = 0.82;
    private final int relativeMinXpixels = (int) (getScreenWidth() * relativeMinXpercent);
    private final int relativeMaxXpixels = (int) (getScreenWidth() * relativeMaxXpercent);
    // Maximum speed
    public static final double SPEED_PIXELS_PER_SECOND = 300.0;
    // dimensional analysis gives you pixels per update
    public static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    // Touch joystick which controls the player
    private final Joystick joystick;
    private Context context;

    // player constructor

    /**
     * Constructs a Player object with four sprites that are drawn to the screen and that can be
     * controlled by a touch joystick.
     * @param context the current context
     * @param joystick the joystick object to control the player's velocity
     * @param positionX the player's x-coordinate
     * @param positionY the player's y-coordinate
     * @param radius the radius of the player's collision circle
     */
    public Player(Context context, Joystick joystick, float positionX, float positionY, double radius) {
        // Call parent (Circle) constructor
        super(context, ContextCompat.getColor(context, R.color.player), positionX, positionY, radius);
        // Set joystick and context
        this.joystick = joystick;
        this.context = context;
        // Get sprite preference
        SharedPreferences preferences = context.getSharedPreferences("MY_PREFS", 0);
        selectedShortHair = preferences.getBoolean("shortHairSelection", false);
        // Set correct sprites
        if(selectedShortHair) {
            Bitmap originalIdleRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.boy_idle_right);
            spriteIdleRight = Bitmap.createScaledBitmap(originalIdleRight, 200, 200, false);
            Bitmap originalIdleLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.boy_idle_fliped);
            spriteIdleLeft = Bitmap.createScaledBitmap(originalIdleLeft, 200, 200, false);
            Bitmap originalWalkRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.boy_walk_right);
            spriteWalkRight = Bitmap.createScaledBitmap(originalWalkRight, 200, 200, false);
            Bitmap originalWalkLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.boy_walk_left);
            spriteWalkLeft = Bitmap.createScaledBitmap(originalWalkLeft, 200, 200, false);
        }
        else {
            Bitmap originalIdleRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.girl_idle_right);
            spriteIdleRight = Bitmap.createScaledBitmap(originalIdleRight, 200, 200, false);
            Bitmap originalIdleLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.girl_idle_left);
            spriteIdleLeft = Bitmap.createScaledBitmap(originalIdleLeft, 200, 200, false);
            Bitmap originalWalkRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.girl_walk_right);
            spriteWalkRight = Bitmap.createScaledBitmap(originalWalkRight, 200, 200, false);
            Bitmap originalWalkLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.girl_walk_left);
            spriteWalkLeft = Bitmap.createScaledBitmap(originalWalkLeft, 200, 200, false);
        }
        currentSprite = spriteIdleRight;
        previousDirection = currentSprite;
    }

    /**
     * Updates the player's velocity based on joystick input, checks for collisions with room
     * boundaries, and updates player's position based on velocity.
     */
    public void update() {
        // Update velocity based on joystick actuator
        velocityX = joystick.getActuatorX()*MAX_SPEED;
        velocityY = joystick.getActuatorY()*MAX_SPEED;
        // Check position relative to screen boundaries
        float relativePositionY = positionY / getScreenHeight();
        float relativePositionX = positionX / getScreenWidth();
        // Check if beyond room boundaries in X direction
        if (relativePositionX > relativeMaxXpercent) {
            positionX = relativeMaxXpixels;
        } else if (relativePositionX < relativeMinXpercent) {
            positionX = relativeMinXpixels;
        }
        // Check if beyond room boundaries in Y direction
        if (relativePositionY > relativeMaxYpercent) {
            positionY = relativeMaxYpixels;
        } else if (relativePositionY < relativeMinYpercent) {
            positionY = relativeMinYpixels;
        }
        // Update position
        positionX += velocityX;
        positionY += velocityY;
    }

    /**
     * Manually set the player's x and y position.
     * @param positionX the new x-coordinate
     * @param positionY the new y-coordinate
     */
    public void setPosition(float positionX, float positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    /**
     * Handles sprite animation and draws the player bitmaps to the screen.
     * @param canvas the canvas to which the object will be drawn
     */
    public void draw(Canvas canvas) {
        // Check if moving right or left
        if (velocityX > 0) {
            currentSprite = spriteWalkRight;
        } else if (velocityX < 0) {
            currentSprite = spriteWalkLeft;
        } else {
            // Set idle based on previous direction of movement
            if (previousDirection == spriteWalkRight) {
                currentSprite = spriteIdleRight;
            } else if (previousDirection == spriteWalkLeft) {
                currentSprite = spriteIdleLeft;
            }
        }
        // Draw bitmap
        Paint paint = new Paint();
        canvas.drawBitmap(currentSprite, positionX, positionY, paint);
        // Update previous direction
        previousDirection = currentSprite;
    }
}
