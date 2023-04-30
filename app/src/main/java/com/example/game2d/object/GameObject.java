package com.example.game2d.object;

import android.graphics.Canvas;

// generalized class for all game objects eg player and enemy

/**
 * GameObject is an abstract class and the foundation of all
 * world objects in the game.
 */
// public modifer allows class to be accessed outside of package (default is private)
public abstract class GameObject {
    // protected so that player etc can inherit them
    /*
    protected double positionX;
    protected double positionY;

     */
    protected float positionX;
    protected float positionY;
    protected double velocityX;
    protected double velocityY;

    // constructor
    public GameObject(float positionX, float positionY) {

        this.positionX = positionX;
        this.positionY = positionY;
    }


    // abstract: all derived classes have their own implementation
    public abstract void draw(Canvas canvas);
    public abstract void update();

    protected double getPositionX() {
        return positionX;
    }
    protected double getPositionY() {
        return positionY;
    }
    protected static double getDistanceBetweenObjects(GameObject obj1, GameObject obj2) {
        return Math.sqrt(
                Math.pow(obj2.getPositionX() - obj1.getPositionX(), 2) +
                        Math.pow(obj2.getPositionY() - obj1.getPositionY(), 2)
        );
    }
}
