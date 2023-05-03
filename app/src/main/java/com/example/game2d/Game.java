package com.example.game2d;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.game2d.object.Circle;
import com.example.game2d.object.Detector;
import com.example.game2d.object.Player;

 /**
  *Game manages the objects in the MainActivity classroom. It is responsible for player movement and
  *animation as well as switching to different mini-game activities when a collision is detected.
  */
public class Game extends SurfaceView implements SurfaceHolder.Callback {
    // Background image
    Bitmap background;
    // Touch joystick to control player
    private final Joystick joystick;
    // Player object
    private final Player player;
    public static boolean canMove;
    // Collision detectors
    private final Detector detector1;
    private final Detector detector2;
    private final Detector detector3;
    private final Detector detector4;
    // Game loop and context
    private GameLoop gameLoop;
    private Context gameContext;

    /**
     * Constructs a new Game object and initializes its background, player, joystick, and detectors.
     * @param context the current context
     */
    public Game(Context context) {
        super(context);
        gameContext = context;
        // Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        // New gameLoop object
        gameLoop = new GameLoop(this, surfaceHolder);
        // Allow player movement
        canMove = true;
        // Check screen height and width
        int height = getScreenHeight();
        int width = getScreenWidth();
        // Set background
        Bitmap original = BitmapFactory.decodeResource(context.getResources(), R.drawable.new_classroom_spots_scaled);
        background = Bitmap.createScaledBitmap(original, width, height, false);
        // Initialize joystick
        int joystickX = 150;
        int joystickY = height - 150;
        joystick = new Joystick(joystickX, joystickY, 70, 40);
        // Initialize player
        player = new Player(context, joystick, (float) (width / 2 - 100), (float) (height * 0.65), 30);
        // Initialize detectors
        detector1 = new Detector(getContext(), player, (float) (width * 0.25), (float) (height * 0.17), 100);
        detector2 = new Detector(getContext(), player, (float) (width * 0.47), (float) (height * 0.17), 100);
        detector3 = new Detector(getContext(), player, (float) (width * 0.75), (float) (height * 0.17), 100);
        detector4 = new Detector(getContext(), player, (float) (width * 0.1), (float) (height * 0.17), 100);
        // Copy other developers lol
        setFocusable(true);
    }

    /**
     * Overrides onTouchEvent to provide touch control of the player.
     * Handles touch events by setting the joystick actuator when the user presses down or holds and
     * drags, and resetting the actuator when the user releases.
     * @param event the motion event
     * @return true if the event has been handled
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            // When user presses down
            case MotionEvent.ACTION_DOWN:
                if (joystick.isPressed((double) event.getX(), (double) event.getY())) {
                    joystick.setIsPressed(true);
                }
                return true;
            // When user does touch and hold / drag
            case MotionEvent.ACTION_MOVE:
                if(joystick.getIsPressed()) {
                    // Joystick is pressed down and moved
                    joystick.setActuator((double) event.getX(), (double) event.getY());
                }
                return true;
            // When user lets go of joystick
            case MotionEvent.ACTION_UP:
                joystick.setIsPressed(false);
                joystick.resetActuator();
                return true;
        }
        return super.onTouchEvent(event);
    }

    /**
     * Override the surfaceCreated method for when the SurfaceView surface is created.
     * Starts the game loop.
     * @param surfaceHolder the SurfaceHolder associated with the SurfaceView
     */
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        gameLoop.startLoop();
    }

    /**
     * Overrides the surfaceChanged method for when the surface is changed (does nothing).
     * @param surfaceHolder the SurfaceHolder associated with the SurfaceView
     * @param i the new format of the surface
     * @param i1 the new width of the surface
     * @param i2 the new height of the surface
     */
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) { }

    /**
     * Overrides the surfaceDestroyed method for when the surface is destroyed (does nothing).
     * @param surfaceHolder the SurfaceHolder associated with the SurfaceView
     */
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) { }

    /**
     * Overrides the draw method to be called in GameLoop to render objects to the screen.
     * Draws the background, joystick, player, and detectors.
     * Detectors are not visible unless the Detector draw method is uncommented in Detector class.
     * @param canvas the canvas to which the objects are drawn
     */
    @Override
    public void draw(Canvas canvas) {
        if (canvas == null) {
            return;
        }
        super.draw(canvas);
        // Draw UPS and FPS (not visible)
        drawUPS(canvas);
        drawFPS(canvas);
        // Draw background
        Paint paint = new Paint();
        canvas.drawBitmap(background, 0, 0, paint);
        // Draw joystick
        joystick.draw(canvas);
        // Draw player
        player.draw(canvas);
        // Draw detectors (not visible, see above)
        detector1.draw(canvas);
        detector2.draw(canvas);
        detector3.draw(canvas);
        detector4.draw(canvas);
    }

    /**
     * Displays the number of updates per second (UPS).
     * @param canvas the canvas to which the text is drawn
     */
    public void drawUPS(Canvas canvas) {
        // Round to one decimal place
        String averageUPS = String.format("%.3g%n", gameLoop.getAverageUPS());
        // Set text properties
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        // Draw text
        canvas.drawText("UPS: " + averageUPS, 100, 100, paint);
    }

    /**
     * Displays the number of frames per second (FPS).
     * @param canvas the canvas to which the text is drawn
     */
    public void drawFPS(Canvas canvas) {
        // Round to one decimal place
        String averageFPS = String.format("%.3g%n", gameLoop.getAverageFPS());
        // Set text properties
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        // Draw text
        canvas.drawText("FPS: " + averageFPS, 100, 200, paint);
    }

    /**
     * Updates the state of the player, joystick, and detector and switches activities if a
     * collision is detected.
     */
    public void update() {
        // Update objects
        player.update();
        joystick.update();
        detector1.update();
        detector2.update();
        detector3.update();
        detector4.update();
        // Check for collisions with detectors
        if (Circle.isColliding(player, detector1)) {
            if (canMove) {
                // Call method to start name quiz activity
                startQuizActivity();
            }
            canMove = false;
        }
        if (Circle.isColliding(player, detector2)) {
            if (canMove) {
                // Call method to start chalk activity
                startChalkActivity();
            }
            canMove = false;
        }
        if (Circle.isColliding(player, detector3)) {
            if (canMove) {
                // Call method to start leaderboard activity
                startLeaderboardActivity();
            }
            canMove = false;
        }
        if (Circle.isColliding(player, detector4)) {
            if (canMove) {
                // Call method to start art activity
                startArtActivity();
            }
            canMove = false;
        }
    }

    /**
     * Gets the width of the screen.
     * @return the width of the screen (in pixels)
     */
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    /**
     * Gets the height of the scree.
     * @return the height of the screen (in pixels)
     */
    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    /**
     * Switches to the name quiz activity.
     */
    public void startQuizActivity() {
        Intent intent = new Intent(gameContext, NameIntroActivity.class);
        gameContext.startActivity(intent);
    }

    /**
     * Switches to the chalk quiz activity.
     */
    public void startChalkActivity() {
        Intent intent = new Intent(gameContext, ChalkIntroActivity.class);
        gameContext.startActivity(intent);
    }

    /**
     * Switches to the leaderboard activity.
     */
    public void startLeaderboardActivity() {
        Intent intent = new Intent(gameContext, FirebaseActivity.class);
        gameContext.startActivity(intent);
    }

    /**
     * Switches to the art credit activity.
     */
    public void startArtActivity() {
        Intent intent = new Intent(gameContext, ArtActivity.class);
        gameContext.startActivity(intent);
    }
}
