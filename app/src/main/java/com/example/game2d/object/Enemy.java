package com.example.game2d.object;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.game2d.GameLoop;
import com.example.game2d.R;

/**
 * Enemy is a circular character that is alays moving in the direction of the player.
 * The Enemy class is an extension of Circle, which is an extension of GameObject.
 */
public class Enemy extends Circle {
    // max speed
    private static final double SPEED_PIXELS_PER_SECOND = Player.SPEED_PIXELS_PER_SECOND * 0.5;
    // dimensional analysis lol gives you pixels per update
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private static final double SPAWNS_PER_MINUTE = 20;
    private static final double SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE / 60.0;
    private static final double UPDATES_PER_SPAWN = GameLoop.MAX_UPS / SPAWNS_PER_SECOND;
    private static double updatesUntilNextSpawn = UPDATES_PER_SPAWN;

    private final Player player;

    // constructor for a single enemy
    public Enemy(Context context, Player player, double positionX, double positionY, double radius) {
        super(context, ContextCompat.getColor(context, R.color.enemy), positionX, positionY, radius);
        this.player = player;
    }

    // constructor for enemies in list
    public Enemy(Context context, Player player) {
        super(
                context,
                ContextCompat.getColor(context, R.color.enemy),
                // random spawn location
                Math.random()*1000,
                Math.random()*1000,
                30
        );
        this.player = player;
    }

    /**
     * readyToSpawn checks if a new enemy should spawn based on
     * the specified number of spawns per minute above
     */
    public static boolean readyToSpawn() {
        // if countdown is over
        if (updatesUntilNextSpawn <= 0) {
            // reset countdown
            updatesUntilNextSpawn += UPDATES_PER_SPAWN;
            return true;
        } else {
            updatesUntilNextSpawn--;
            return false;
        }
    }

    @Override
    public void update() {
        // ==============================================================
        //   Update enemy velocity to be in the direction of the player
        // ==============================================================
        // 1. calculate vector (x, y) from enemy to player
        double distanceToPlayerX = player.getPositionX() - positionX;
        double distanceToPlayerY = player.getPositionY() - positionY;

        // 2. calculate (absolute) distance between enemy and player
        double distanceToPlayer = GameObject.getDistanceBetweenObjects(this, player);

        // 3. calculate direction from enemy to player
        double directionX = distanceToPlayerX / distanceToPlayer;
        double directionY = distanceToPlayerY / distanceToPlayer;

        // 4. set velocity in direction of player
        // guard against division by zero (⊙_☉)! (supposed to be in 3.)
        if (distanceToPlayer > 0) {
            velocityX = directionX * MAX_SPEED;
            velocityY = directionY * MAX_SPEED;
        } else {
            velocityX = 0;
            velocityY = 0;
        }

        // 5. update position of enemy
        positionX += velocityX;
        positionY += velocityY;

    }
}
