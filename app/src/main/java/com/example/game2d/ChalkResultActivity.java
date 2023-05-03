package com.example.game2d;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * ChalkResultActivity is triggered when the user reaches the end of the chalk quiz.
 * It displays the current and highest score out of four and creates buttons to try again or
 * continue back to the classroom.
 */
public class ChalkResultActivity extends AppCompatActivity {
    // TextViews for current and highest scores
    TextView newscore;
    TextView bestscore;
    // Comment about the score
    TextView comment;
    // Current and highest scores
    int lastChalkScore;
    int bestChalk;
    // Win sound effect
    MediaPlayer winSound;

    /**
     * Sets content view to the associated XML file, hides the status bar, initializes TextViews
     * for the scores and comment, retrieves or saves the highest score, and creates buttons to try
     * again or continue.
     * @param savedInstanceState the instance state bundle
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chalk_result);
        // Win sound
        winSound = MediaPlayer.create(this, R.raw.eerie_win);
        winSound.start();
        // Hide status bar
        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        // Create TextViews
        newscore = (TextView) findViewById(R.id.score);
        bestscore = (TextView) findViewById(R.id.highestscore);
        comment = (TextView) findViewById(R.id.comment);
        // Continue button
        Button continue_button = (Button) findViewById(R.id.back_button);
        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                winSound.stop();
                // switch back to main activity
                Intent intent = new Intent(ChalkResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        // Try again button
        Button again_button = (Button) findViewById(R.id.again_button);
        again_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                winSound.stop();
                // switch back to chalk activity
                Intent intent = new Intent(ChalkResultActivity.this, ChalkActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        // Get scores from SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MY_PREFS", 0);
        lastChalkScore = preferences.getInt("lastChalkScore", 0);
        bestChalk = preferences.getInt("bestChalk", 0);
        // Update highest score if current score is greater
        if (lastChalkScore > bestChalk) {
            bestChalk = lastChalkScore;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("bestChalk", bestChalk);
            editor.apply();
        }
        // Set score text
        newscore.setText(lastChalkScore + " out of 4");
        bestscore.setText(bestChalk + " out of 4");
    }
}