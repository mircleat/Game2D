package com.example.game2d;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.Objects;

/*
MainActivity is the entry point
 */
public class MainActivity extends AppCompatActivity {

    MediaPlayer backgroundMusic;

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

        backgroundMusic = MediaPlayer.create( this, R.raw.moog_city_two );
        backgroundMusic.setLooping(true);
        backgroundMusic.start();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // Set content view to game so that objects in the Game class can be rendered
        setContentView(new Game(this));
    }

    protected void onPause() {
        super.onPause();
        backgroundMusic.stop();
    }
}