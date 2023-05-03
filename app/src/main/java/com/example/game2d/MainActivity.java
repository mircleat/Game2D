package com.example.game2d;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.Objects;

/**
 * MainActivity is the entry point of the actual game. It creates a new Game to update and draw
 * objects to the screen and manages the background music.
 */
public class MainActivity extends AppCompatActivity {
    // Background music
    MediaPlayer backgroundMusic;

    /**
     * Hides the status bar and starts the background music.
     * @param savedInstanceState the instance state bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        // Set window to fullscreen and hide status bar
        // (landscape orientation handled in manifest file)
        Window window = getWindow();
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        // Start background music
        backgroundMusic = MediaPlayer.create( this, R.raw.moog_city_two );
        backgroundMusic.setLooping(true);
        // Set content view to game so that Game objects can be rendered to the screen
        setContentView(new Game(this));
    }

    /**
     * Starts the background music.
     */
    protected void onStart() {
        super.onStart();
        backgroundMusic.start();
    }

    /**
     * Pauses the background music.
     */
    protected void onPause() {
        super.onPause();
        backgroundMusic.pause();
    }
}