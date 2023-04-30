package com.example.game2d.object;

import android.graphics.Canvas;

/**
 * GameObject is an abstract class and the foundation for the Circle, Player, and Detector classes
 * that are drawn in the Game view for the classroom.
 */
public abstract class GameObject {
    protected float positionX;
    protected float positionY;
    protected double velocityX;
    protected double velocityY;

    /**
     * Constructs a game object at the specified position.
     * @param positionX the x-coordinate of the object
     * @param positionY the y-coordinate of the object
     */
    public GameObject(float positionX, float positionY) {

        this.positionX = positionX;
        this.positionY = positionY;
    }

    /**
     * Draw is an abstract method that allows objects to be drawn to the screen.
     * @param canvas the canvas to which the object is drawn
     */
    public abstract void draw(Canvas canvas);

    /**
     * Update is an abstract method that allows objects to have changing velocity and position.
     */
    public abstract void update();

    /**
     * An accessor for the object's x-position.
     * @return the x-coordinate of the object's position
     */
    protected double getPositionX() {
        return positionX;
    }

    /**
     * An accessor for the object's y-position.
     * @return the y-coordinate of the object's position
     */
    protected double getPositionY() {
        return positionY;
    }

    /**
     * Calculates the distance between two game objects.
     * @param obj1 the first game object
     * @param obj2 the second game object
     * @return the distance (in pixels) between the two objects
     */
    protected static double getDistanceBetweenObjects(GameObject obj1, GameObject obj2) {
        return Math.sqrt(
                Math.pow(obj2.getPositionX() - obj1.getPositionX(), 2) +
                        Math.pow(obj2.getPositionY() - obj1.getPositionY(), 2)
        );
    }
}
