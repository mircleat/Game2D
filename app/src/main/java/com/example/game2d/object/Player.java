package com.example.game2d.object;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.game2d.GameLoop;
import com.example.game2d.Joystick;
import com.example.game2d.R;

/**
 * Player is the circular character that the user can control with a touch joystick.
 * Player is an extension of a Circle, which is an extension of a GameObject
 */
public class Player extends Circle {
    // max speed
    public static final double SPEED_PIXELS_PER_SECOND = 300.0;
    // dimensional analysis lol gives you pixels per update
    public static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private final Joystick joystick;
    // create as doubles for higher precision

    // player constructor
    public Player(Context context, Joystick joystick, double positionX, double positionY, double radius) {
        // specify parent constructor because there is no default no arg one
        // player color defined in colors xml
        super(context, ContextCompat.getColor(context, R.color.player), positionX, positionY, radius);
        this.joystick = joystick;
    }

    public void update() {
        // update velocity based on joystick actuator
        velocityX = joystick.getActuatorX()*MAX_SPEED;
        velocityY = joystick.getActuatorY()*MAX_SPEED;
        // update position
        // later can abstract out position updates
        positionX += velocityX;
        positionY += velocityY;
    }

    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
        // to log stuff, can use Log.d like so
        // Log.d("Player", "moved");
    }
}
