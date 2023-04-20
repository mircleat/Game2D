package com.example.game2d;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.game2d.object.Circle;
import com.example.game2d.object.Detector;
import com.example.game2d.object.Player;

/*
Game manages all objects in the game and is responsible for updating all states
and rendering all objects to the screen.
 */
public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Joystick joystick;
    private final Player player;
    private final Detector detector1;
    private final Detector detector2;

    private GameLoop gameLoop;
    private Context gameContext;

    public Game(Context context) {
        super(context);
        gameContext = context;

        // Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        // new gameloop object
        gameLoop = new GameLoop(this, surfaceHolder);

        // check screen height and width
        int height = getScreenHeight();
        int width = getScreenWidth();
        Log.d("DIMENSION", String.valueOf(height));
        Log.d("DIMENSION", String.valueOf(width));

        // Initialize game objects
        // initialize joystick
        int joystickX = 150;
        int joystickY = height - 150;
        //joystick = new Joystick(275, 700, 70, 40);
        joystick = new Joystick(joystickX, joystickY, 70, 40);
        // initialize new instance of player class
        player = new Player(context, joystick, 500, 250, 30);
        // initialize new enemy
        //enemy = new Enemy(context, player, 500, 500, 30);
        // initialize new detectors
        detector1 = new Detector(getContext(), player, width - 100, height - 100, 25);
        detector2 = new Detector(getContext(), player, width - 100, 100, 25);

        // copy other developers lol
        setFocusable(true);
    }

    // for player controls, override on touch event
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // handle touch event actions
        switch(event.getAction()) {
            // when user presses down
            case MotionEvent.ACTION_DOWN:
                if (joystick.isPressed((double) event.getX(), (double) event.getY())) {
                    joystick.setIsPressed(true);
                }
                // cast to double
                //player.setPosition((double) event.getX(), (double) event.getY());
                return true; // true indicates event has been handled :)
            // when user does touch and hold / drag
            case MotionEvent.ACTION_MOVE:
                if(joystick.getIsPressed()) {
                    // joystick is BOTH pressed down AND moved
                    joystick.setActuator((double) event.getX(), (double) event.getY());
                }
                //player.setPosition((double) event.getX(), (double) event.getY());
                return true; // true indicates event has been handled :)
            // when user lets go of joystick
            case MotionEvent.ACTION_UP:
                joystick.setIsPressed(false);
                joystick.resetActuator();
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) { }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) { }

    // override draw method to get visual feedback when we render things to screen
    @Override
    public void draw(Canvas canvas) {
        if (canvas == null) {
            return;
        }
        super.draw(canvas);
        drawUPS(canvas);
        drawFPS(canvas);
        // draw joystick
        joystick.draw(canvas);
        // draw player
        player.draw(canvas);
        // draw single detectors
        detector1.draw(canvas);
        detector2.draw(canvas);
    }
    // method to display how many updates per second (UPS)
    public void drawUPS(Canvas canvas) {
        //String averageUPS = Double.toString(gameLoop.getAverageUPS());
        // round to one decimal place
        String averageUPS = String.format("%.3g%n", gameLoop.getAverageUPS());
        Paint paint = new Paint();
        // also need to add magenta in colors.xml file
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS: " + averageUPS, 100, 100, paint);
    }
    // method to display how many frames per second (FPS)
    public void drawFPS(Canvas canvas) {
        //String averageFPS = Double.toString(gameLoop.getAverageFPS());
        // round to one decimal place
        String averageFPS = String.format("%.3g%n", gameLoop.getAverageFPS());
        Paint paint = new Paint();
        // also need to add magenta in colors.xml file
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS: " + averageFPS, 100, 200, paint);
    }

    public void update() {
        // update game state
        joystick.update();
        player.update();
        detector1.update();
        detector2.update();
        // Check for collision with detectors
        if (Circle.isColliding(player, detector1)) {
            // call method to start quiz activity
            startSecondActivity();
            Log.d("COLLISION", "DETECTOR");
        }
        if (Circle.isColliding(player, detector2)) {
            // call method to start chalk activity
            startChalkActivity();
            Log.d("COLLISION", "DETECTOR");
        }
    }

    // check screen width and height
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    // method to start name activity
    public void startSecondActivity() {
        Intent intent = new Intent(gameContext, NameActivity.class);
        gameContext.startActivity(intent);
        Log.d("COLLISION", "Starting name activity");
    }
    // method to start chalk activity
    public void startChalkActivity() {
        Intent intent = new Intent(gameContext, ChalkActivity.class);
        gameContext.startActivity(intent);
        Log.d("COLLISION", "Starting chalk activity");
    }
}
