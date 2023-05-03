package com.example.game2d;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * The Joystick is a touch joystick that is used to control the Player movement.
 * It consists of an outer circle and a movable inner circle that are drawn to the screen.
 * The actuator fields store the extent to which the inner circle is dragged.
 */
public class Joystick {
    // Circle radii to draw
    private int outerCircleRadius;
    private int innerCircleRadius;
    // Position of outer circle center
    private int outerCircleCenterPositionX;
    private int outerCircleCenterPositionY;
    // Position of inner circle center
    private int innerCircleCenterPositionX;
    private int innerCircleCenterPositionY;
    // Circle paints
    private Paint innerCirclePaint;
    private Paint outerCirclePaint;
    // Distance between touch input and outer circle center
    private double joystickCenterToTouchDistance;
    // Whether the joystick is being pressed
    private boolean isPressed;
    // Actuator values with range 0-1 where 1 is furthest
    private double actuatorX;
    private double actuatorY;

    /**
     * Constructs a new joystick with the specified radii and position.
     * @param centerPositionX the circle center's x-coordinate
     * @param centerPositionY the circle center's y-coordinate
     * @param outerCircleRadius the radius of the outer circle
     * @param innerCircleRadius the radius of the inner circle
     */
    public Joystick(int centerPositionX, int centerPositionY, int outerCircleRadius, int innerCircleRadius) {
        // Set position
        outerCircleCenterPositionX = centerPositionX;
        outerCircleCenterPositionY = centerPositionY;
        innerCircleCenterPositionX = centerPositionX;
        innerCircleCenterPositionY = centerPositionY;
        // Set circle radii
        this.outerCircleRadius = outerCircleRadius;
        this.innerCircleRadius = innerCircleRadius;
        // Set circle paints
        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(Color.GRAY);
        outerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(Color.BLUE);
        innerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    /**
     * Draws the joystick composed of two circles to the screen.
     * @param canvas the canvas to which the circles are drawn
     */
    public void draw(Canvas canvas) {
        // Draw outer circle
        canvas.drawCircle(
                outerCircleCenterPositionX,
                outerCircleCenterPositionY,
                outerCircleRadius,
                outerCirclePaint
        );
        // Draw inner circle
        canvas.drawCircle(
                innerCircleCenterPositionX,
                innerCircleCenterPositionY,
                innerCircleRadius,
                innerCirclePaint
        );
    }

    /**
     * Updates the joystick by updated the position of the movable inner circle.
     */
    public void update() {
        updateInnerCirclePosition();
    }

    /**
     * Updates the position of the inner circle according to the actuator values.
     */
    private void updateInnerCirclePosition() {
        innerCircleCenterPositionX = (int) (outerCircleCenterPositionX + actuatorX*outerCircleRadius);
        innerCircleCenterPositionY = (int) (outerCircleCenterPositionY + actuatorY*outerCircleRadius);
    }

    /**
     * Checks if a touch input is on the joystick.
     * @param touchPositionX the x-coordinate of the touch input
     * @param touchPositionY the y-coordinate of the touch input
     * @return true if the touch input is on the joystick
     */
    public boolean isPressed(double touchPositionX, double touchPositionY) {
        joystickCenterToTouchDistance = Math.sqrt(
                Math.pow(outerCircleCenterPositionX - touchPositionX, 2) +
                Math.pow(outerCircleCenterPositionY - touchPositionY, 2)
        );
        return joystickCenterToTouchDistance < outerCircleRadius;
    }

    /**
     * Sets the joystick to be pressed or not.
     * @param isPressed whether the joystick should be pressed
     */
    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    /**
     * Gets whether the joystick is pressed.
     * @return whether the joystick is currently pressed or not
     */
    public boolean getIsPressed() {
        return isPressed;
    }

    /**
     * Sets the actuator values based on the touch input.
     * @param touchPositionX the x-coordinate of the touch input
     * @param touchPositionY the y-coordinate of the touch input
     */
    public void setActuator(double touchPositionX, double touchPositionY) {
        // Distance from center of joystick to touch position
        double deltaX = touchPositionX - outerCircleCenterPositionX;
        double deltaY = touchPositionY - outerCircleCenterPositionY;
        double deltaDistance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        // Actuator values (percentage in range 0-1)
        if(deltaDistance < outerCircleRadius) {
            actuatorX = deltaX / outerCircleRadius;
            actuatorY = deltaY / outerCircleRadius;
        } else {
            // Vector normalization
            actuatorX = deltaX / deltaDistance;
            actuatorY = deltaY / deltaDistance;
        }
    }

    /**
     * Resets the actuator values to zero.
     */
    public void resetActuator() {
        actuatorX = 0.0;
        actuatorY = 0.0;
    }

    /**
     * Gets the actuator's x-value.
     * @return the actuator x-value
     */
    public double getActuatorX() {
        return actuatorX;
    }

    /**
     * Gets the actuator's y-value.
     * @return the actuator's y-value.
     */
    public double getActuatorY() {
        return actuatorY;
    }
}
