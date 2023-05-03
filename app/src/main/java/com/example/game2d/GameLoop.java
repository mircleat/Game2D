package com.example.game2d;

import android.graphics.Canvas;
import android.view.Surface;
import android.view.SurfaceHolder;
import java.util.zip.Adler32;

/**
 * GameLoop manages the timing of updates (UPS) and frames (FPS) to draw the canvas during the
 * MainActivity classroom screen.
 */
public class GameLoop extends Thread {
    // Maximum updates per second (UPS)
    public static final double MAX_UPS = 60.0;
    private static final double UPS_PERIOD = 1E+3/MAX_UPS;
    // Game and SurfaceHolder
    private Game game;
    private SurfaceHolder surfaceHolder;
    // Start not running
    private boolean isRunning = false;
    // Average UPS and FPS
    private double averageUPS;
    private double averageFPS;

    /**
     * Constructs a new GameLoop object and initializes its Game and surfaceHolder
     * @param game the associated Game object
     * @param surfaceHolder the SurfaceHolder associated with the SurfaceView
     */
    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.game = game;
        this.surfaceHolder = surfaceHolder;
    }

    /**
     * Gets the average updates per second (UPS).
     * @return the average number of UPS
     */
    public double getAverageUPS() {
        return averageUPS;
    }

    /**
     * Gets the average frames per second (FPS).
     * @return the average number of FPS
     */
    public double getAverageFPS() {
        return averageFPS;
    }

    /**
     * Starts the GameLoop.
     */
    public void startLoop() {
        isRunning = true;
        start();
    }

    /**
     * Runs the GameLoop. Keeps track of time passed in milliseconds and the number of frames and
     * updates. Also calculates the average number of UPS and FPS.
     */
    @Override
    public void run() {
        super.run();
        // Declare time and cycle count variables
        int updateCount = 0;
        int frameCount = 0;
        long startTime;
        long elapsedTime;
        long sleepTime;
        // Create canvas to draw on (initialize null for final block)
        Canvas canvas = null;
        // Set start time to current time in millis
        startTime = System.currentTimeMillis();

        // Game loop
        while(isRunning) {
            // Try to update and render objects from Game class
            try {
                // Lock canvas
                canvas = surfaceHolder.lockCanvas();
                // (Surround methods with synchronized block to prevent multithreading mix-ups)
                synchronized (surfaceHolder) {
                    // Update game
                    game.update();
                    // Increment update count
                    updateCount++;
                    // Draw to canvas
                    game.draw(canvas);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } finally {
                // Final block is executed whether we catch an exception or not
                if(canvas != null) {
                    try {
                        // Unlock canvas
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        // Increment frame count after canvas is posted
                        frameCount++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // Pause game loop to not exceed target UPS frequency
            // Check elapsed time
            elapsedTime = System.currentTimeMillis() - startTime;
            // Calculate sleep time
            // - UPS_PERIOD is inverse of target update frequency
            // - the product is how much time should have passed at end of current iteration
            // - subtracting elapsed time gives how much time we need to sleep for to match target UPS period
            sleepTime = (long) (updateCount*UPS_PERIOD - elapsedTime);
            if (sleepTime > 0) {
                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // Skip frames to keep up with target UPS
            // - subtract 1 from MAX_UPS to make sure UPS is not above max
            while(sleepTime < 0 && updateCount < MAX_UPS-1) {
                game.update();
                updateCount++;
                // recalculate elapsed time and sleep time
                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = (long) (updateCount*UPS_PERIOD - elapsedTime);
            }
            // Calculate average UPS and FPS
            elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime >= 1000) {
                // Average is number of updates divided by elapsed time divided by 1000
                averageUPS = updateCount / (1E-3 * elapsedTime);
                averageFPS = frameCount / (1E-3 * elapsedTime);
                // Reset update and frame counts
                updateCount = 0;
                frameCount = 0;
                // Reset start time
                startTime = System.currentTimeMillis();
            }
        }
    }
}
