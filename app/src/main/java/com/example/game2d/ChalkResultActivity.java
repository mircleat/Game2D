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

public class ChalkResultActivity extends AppCompatActivity { //

    TextView newscore;

    TextView bestscore;

    TextView comment;

    int lastChalkScore;
    int bestChalk;

    MediaPlayer winSound;

    Button submitBtn;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_result);
        winSound = MediaPlayer.create(this, R.raw.eerie_win);
        winSound.start();
        Log.d("CHALKRESULT", "onCreate after sound start");
        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        newscore = (TextView) findViewById(R.id.score);
        bestscore = (TextView) findViewById(R.id.highestscore);
        comment = (TextView) findViewById(R.id.comment);


        // Continue button
        Button back_button = (Button) findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
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
                // switch back to main activity
                Intent intent = new Intent(ChalkResultActivity.this, ChalkActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        SharedPreferences preferences = getSharedPreferences("MY_PREFS", 0);
        lastChalkScore = preferences.getInt("lastChalkScore", 0);
        bestChalk = preferences.getInt("bestChalk", 0);

        if (lastChalkScore > bestChalk) {
            bestChalk = lastChalkScore;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("bestChalk", bestChalk);
            editor.apply();
        }
        newscore.setText(lastChalkScore + " out of 4");
        bestscore.setText(bestChalk + " out of 4");

//        if (lastScore < 3) {
//            comment.setText("You must be a terrible person.");
//        } else if (lastScore == 3) {
//            comment.setText("Not bad, not good either");
//        } else if (lastScore == 4) {
//            comment.setText("Okay... don't be cocky.");
//        } else if (lastScore == 5) {
//            comment.setText("You cheated.");
//        }
    }


    public void onClick(View view) {
        Button clickedButton = (Button) view;
        if (clickedButton.getId() == R.id.back_button) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}