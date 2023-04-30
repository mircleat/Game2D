package com.example.game2d;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    Bitmap background;
    private final Joystick joystick;
    private final Player player;
    public static boolean canMove;
    private final Detector detector1;
    private final Detector detector2;
    private final Detector detector3;
    private final Detector detector4;

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
        canMove = true;

        // check screen height and width
        int height = getScreenHeight();
        int width = getScreenWidth();
        Log.d("DIMENSION", String.valueOf(height));
        Log.d("DIMENSION", String.valueOf(width));

        // Set background
        Bitmap original = BitmapFactory.decodeResource(context.getResources(), R.drawable.new_classroom_spots_scaled);
        background = Bitmap.createScaledBitmap(original, width, height, false);

        // Initialize game objects
        // initialize joystick
        int joystickX = 150;
        int joystickY = height - 150;
        //joystick = new Joystick(275, 700, 70, 40);
        joystick = new Joystick(joystickX, joystickY, 70, 40);
        // initialize new instance of player class
        player = new Player(context, joystick, (float) (width / 2 - 100), (float) (height * 0.65), 30);
        // initialize new enemy
        //enemy = new Enemy(context, player, 500, 500, 30);
        // initialize new detectors
        detector1 = new Detector(getContext(), player, (float) (width * 0.25), (float) (height * 0.17), 100);
        detector2 = new Detector(getContext(), player, (float) (width * 0.47), (float) (height * 0.17), 100);
        detector3 = new Detector(getContext(), player, (float) (width * 0.75), (float) (height * 0.17), 100);
        detector4 = new Detector(getContext(), player, (float) (width * 0.1), (float) (height * 0.17), 100);

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
        // draw background
        Paint paint = new Paint();
        canvas.drawBitmap(background, 0, 0, paint);
        // draw joystick
        joystick.draw(canvas);
        // draw player
        player.draw(canvas);
        // draw single detectors
        detector1.draw(canvas);
        detector2.draw(canvas);
        detector3.draw(canvas);
        detector4.draw(canvas);
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
        detector3.update();
        detector4.update();
        // Check for collision with detectors
        if (Circle.isColliding(player, detector1)) {
            if (canMove) {
                // call method to start quiz activity
                startSecondActivity();
            }
            canMove = false;
        }
        if (Circle.isColliding(player, detector2)) {
            if (canMove) {
                // call method to start chalk activity
                startChalkActivity();
            }
            canMove = false;
        }
        if (Circle.isColliding(player, detector3)) {
            if (canMove) {
                // call method to start leaderboard activity
                startLeaderboardActivity();
            }
            canMove = false;
        }
        if (Circle.isColliding(player, detector4)) {
            if (canMove) {
                // call method to start art activity
                startArtActivity();
            }
            canMove = false;
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
        Intent intent = new Intent(gameContext, NameIntroActivity.class);
        gameContext.startActivity(intent);
    }
    // method to start chalk activity
    public void startChalkActivity() {
        Intent intent = new Intent(gameContext, ChalkIntroActivity.class);
        gameContext.startActivity(intent);
    }
    public void startLeaderboardActivity() {
        Intent intent = new Intent(gameContext, FirebaseActivity.class);
        gameContext.startActivity(intent);
    }
    public void startArtActivity() {
        Intent intent = new Intent(gameContext, ArtActivity.class);
        gameContext.startActivity(intent);
    }
}
