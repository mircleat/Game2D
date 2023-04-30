package com.example.game2d.object;

import android.content.Context;
import android.graphics.Canvas;

import androidx.core.content.ContextCompat;

import com.example.game2d.R;

/**
 * Detector is a circle that is not drawn to the screen. It is used to detect collisions with the
 * player.
 */
public class Detector extends Circle {
    private final Player player;

    /**
     * Constructs a detector with the specified position and radius.
     * @param context the current context
     * @param player the player object to detect collisions with
     * @param positionX the x-coordinate for the circle's center
     * @param positionY the y-coordinate for the circle's center
     * @param radius the circle's radius (in pixels)
     * @return the newly constructed Detector object
     */
    public Detector(Context context, Player player, float positionX, float positionY, double radius) {
        super(context, ContextCompat.getColor(context, R.color.detector), positionX, positionY, radius);
        this.player = player;
    }

    /**
     * Updates the position of the detector based on its velocity.
     * Velocity is always set to zero.
     */
    public void update() {
        velocityX = 0;
        velocityY = 0;
        positionX += velocityX;
        positionY += velocityY;
    }

    /**
     * Can draw the detector circle to the screen.
     * Uncomment to make the detector visible for testing and debugging.
     * @param canvas the canvas to which the object will be drawn
     */
    public void draw(Canvas canvas) {
        // canvas.drawCircle((float) positionX, (float) positionY, (float) radius, paint);
    }
}
