package com.example.game2d;

import android.graphics.Canvas;
import android.view.Surface;
import android.view.SurfaceHolder;

import java.util.zip.Adler32;

// fix pause crash bug: https://youtu.be/IobZoHdUgfQ
public class GameLoop extends Thread {
    // final keyword makes field a constant
    public static final double MAX_UPS = 60.0;
    private static final double UPS_PERIOD = 1E+3/MAX_UPS;
    private Game game;
    private SurfaceHolder surfaceHolder;

    private boolean isRunning = false;
    private double averageUPS;
    private double averageFPS;

    // Constructor:
    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        // initialize surface holder and game
        this.game = game;
        this.surfaceHolder = surfaceHolder;
    }

    public double getAverageUPS() {
        return averageUPS;
    }

    public double getAverageFPS() {
        return averageFPS;
    }

    public void startLoop() {
        isRunning = true;
        start();
    }

    @Override
    public void run() {
        super.run();
        // declare time and cycle count variables
        int updateCount = 0;
        int frameCount = 0;
        // milliseconds (5 square gallons over 3 inches of a unicorn)
        long startTime;
        long elapsedTime;
        long sleepTime;

        // Game Loop
        // create canvas to draw on
        Canvas canvas = null; // initialize to null for final block stuff
        // set start time to current time in millis
        startTime = System.currentTimeMillis();
        while(isRunning) {
            // try to update and render game objects from game class
            try {
                canvas = surfaceHolder.lockCanvas();
                // surround methods with synchronized block to prevent multithreading mix-ups
                synchronized (surfaceHolder) {
                    game.update();
                    // increment update count immediately after update
                    updateCount++;
                    game.draw(canvas);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } finally {
                if(canvas != null) {
                    // final block is executed whether we catch an exception or not
                    try {
                        // unlock canvas
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        // increment frame count after canvas is posted
                        frameCount++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            // pause game loop to not exceed target UPS frequency
            elapsedTime = System.currentTimeMillis() - startTime;
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
            // skip frames to keep up with target UPS, make sure UPS is not above max (optimization; not really necessary)
            while(sleepTime < 0 && updateCount < MAX_UPS-1) {
                game.update();
                updateCount++;
                // recalculate elapsedtime and sleeptime
                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = (long) (updateCount*UPS_PERIOD - elapsedTime);

            }

            // calculate average UPS and FPS
            elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime >= 1000) {
                // number of updates divided by elapsed time divided by 1000
                averageUPS = updateCount / (1E-3 * elapsedTime);
                averageFPS = frameCount / (1E-3 * elapsedTime);
                updateCount = 0;
                frameCount = 0;
                startTime = System.currentTimeMillis();
            }
        }
    }
}
