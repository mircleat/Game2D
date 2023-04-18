package com.example.game2d;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NameResultActivity extends AppCompatActivity { //

    TextView newscore;

    TextView bestscore;

    TextView comment;

    int lastScore;
    int best;

    Button submitBtn;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_result); //

        newscore = (TextView) findViewById(R.id.score);
        bestscore = (TextView) findViewById(R.id.highestscore);
        comment = (TextView) findViewById(R.id.comment);
        // Continue button
        Button back_button = (Button) findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // switch back to main activity
                Intent intent = new Intent(NameResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        // Try again button
        Button again_button = (Button) findViewById(R.id.again_button);
        again_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // switch back to main activity
                Intent intent = new Intent(NameResultActivity.this, NameActivity.class);
                startActivity(intent);
            }
        });


        SharedPreferences preferences = getSharedPreferences("MY_PREFS", 0);
        lastScore = preferences.getInt("lastScore", 0);
        best = preferences.getInt("best", 0);

        if (lastScore > best) {
            best = lastScore;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best", best);
            editor.apply();
        }
        newscore.setText(lastScore + " out of 5");
        bestscore.setText(best + " out of 5");

        if (lastScore < 3) {
            comment.setText("You must be a terrible person.");
        } else if (lastScore == 3) {
            comment.setText("Not bad, not good either");
        } else if (lastScore == 4) {
            comment.setText("Okay... don't be cocky.");
        } else if (lastScore == 5) {
            comment.setText("You cheated.");
        }
    }

    //This part does not work it is supposed to go back to the main Activity
    public void onClick(View view) {
        Button clickedButton = (Button) view;
        if (clickedButton.getId() == R.id.back_button) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}