package com.example.game2d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * This is the transition page/activity that is run when the user dies in the chalkGame/
 * chalkDodging activity. It allows users to continue back to the main menu when they
 * are ready.
 */

public class ChalkGameRIPActivity extends AppCompatActivity {
    private Button resButton;

    MediaPlayer ripSound;

    /**
     * Initializes the sound effects and buttons as well as their mechanisms.
     * @param savedInstanceState the bundle of the instance of the game in case the
     * activity needs to be restored to this instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ripSound = MediaPlayer.create(getApplicationContext(), R.raw.gta_wasted);
        ripSound.start();
        setContentView(R.layout.activity_chalk_game_ripactivity);
        resButton = findViewById(R.id.ChalkRIPResume);

        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        resButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Creates/executes the intent to go to the main menu when
             * the user clicks on the continue button.
             * @param view
             */
            @Override
            public void onClick(View view) {
                ripSound.stop();
                Intent ChalkRIPtrns = new Intent(ChalkGameRIPActivity.this,MainActivity.class);
                ChalkRIPtrns.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(ChalkRIPtrns);
            }
        });
    }
}