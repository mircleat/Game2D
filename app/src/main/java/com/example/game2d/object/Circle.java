package com.example.game2d.object;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Circle is an abstract class that extends GameObject. It provides functionality to
 * check the radius of the circle, check for collisions with other Circle objects, and
 * draw the circle to the screen. The Player and Detector classes both extend Circle.
 */
public abstract class Circle extends GameObject{
    protected double radius;
    protected Paint paint;

    /** Constructs a circle that can be drawn to the screen.
     * @param context the current context
     * @param color the color of the drawn circle, defined in the res/values/colors.xml file
     * @param positionX an x-coordinate for the center of the circle
     * @param positionY a y-coordinate for the center of the circle
     * @param radius the radius (in pixels) of the circle
     * @return the newly constructed Circle object
     */
    public Circle(Context context, int color, float positionX, float positionY, double radius) {
        super(positionX, positionY);
        this.radius = radius;

        // Set circle colors
        paint = new Paint();
        paint.setColor(color);
    }

    /**
     * Checks if two Circle objects are colliding based on position and radii.
     * @param obj1 the first Circle object
     * @param obj2 the second Circle object
     * @return true if the objects are colliding and false if not
     */
    public static boolean isColliding(Circle obj1, Circle obj2) {
        double distance = getDistanceBetweenObjects(obj1, obj2);
        double distanceToCollision = obj1.getRadius() + obj2.getRadius();
        if (distance < distanceToCollision) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the radius of the circle.
     * @return the radius of the circle
     */
    private double getRadius() {
        return radius;
    }

    /**
     * Abstract function to draw Circle objects to the screen.
     * See Player and Detecter classes for implementations.
     * @param canvas the canvas to which the object will be drawn
     */
    public abstract void draw(Canvas canvas);
}
