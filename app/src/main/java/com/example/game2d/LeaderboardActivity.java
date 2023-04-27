package com.example.game2d;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Vector;

public class LeaderboardActivity extends AppCompatActivity {

    private LinearLayout scoreListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        // Get reference to the LinearLayout that will hold the scores
        scoreListLayout = findViewById(R.id.score_list_layout);

        // Create vectors to store the usernames and scores
        Vector<String> usernames = new Vector<>();
        usernames.add("Alice");
        usernames.add("Bob");
        usernames.add("Charlie");
        usernames.add("Dave");

        Vector<Integer> scores = new Vector<>();
        scores.add(100);
        scores.add(75);
        scores.add(60);
        scores.add(50);

        // Loop through the scores and add them to the layout
        for (int i = 0; i < usernames.size(); i++) {
            String username = usernames.get(i);
            int score = scores.get(i);

            // Create a new TextView to display the score
            TextView scoreView = new TextView(this);
            scoreView.setText(String.format("%s: %d", username, score));

            // Add the TextView to the LinearLayout
            scoreListLayout.addView(scoreView);
        }
    }
}
