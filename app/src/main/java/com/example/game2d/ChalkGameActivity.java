package com.example.game2d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Implementation of the custom view for the Chalk Dodging Game.
 * It invalidates the view every 50 ms so that the elements can be
 * redrawn consistently and display the animation.
 */

public class ChalkGameActivity extends AppCompatActivity {
    private ChalkGameClass gameView;
    private Handler handler = new Handler();
    private final static long Interval = 50;


    /**
     * Creates the activity and sets the view to the custom view ChalkGameClass
     * @param savedInstanceState the bundle of the instance of the game in case the
     *  activity needs to be restored to this instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        gameView = new ChalkGameClass(this);
        setContentView(gameView);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    /**
                     * invalidates the gameview so that canvas is redrawn in regular intervals
                     * and the "animation" is visible through the updates positions of the
                     * elements.
                     */
                    @Override
                    public void run() {
                        gameView.invalidate();
                    }
                });
            }
        }, 0, Interval);

    }


}