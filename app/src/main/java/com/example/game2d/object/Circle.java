package com.example.game2d.object;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Circle is an abstract class which implements GameObject's abstract
 * draw method to draw the object as a circle.
 */
public abstract class Circle extends GameObject{
    protected double radius;
    protected Paint paint;

    public Circle(Context context, int color, float positionX, float positionY, double radius) {
        super(positionX, positionY);
        this.radius = radius;

        // Set circle colors
        paint = new Paint();
        paint.setColor(color);
    }

    /**
     * isColliding checks if two circle objects are colliding based on position and radii
     * @param obj1
     * @param obj2
     * @return
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

    private double getRadius() {
        return radius;
    }

    public abstract void draw(Canvas canvas);
    /*
    public void draw(Canvas canvas) {
        // draw player circle (cast doubles to floats as required)
        canvas.drawCircle((float) positionX, (float) positionY, (float) radius, paint);
    }
     */

}
