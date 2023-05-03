package com.example.game2d;

import static com.example.game2d.MapUtil.sortByValue;
//import static com.example.game2d.Scoreboard

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * The LeaderboardActivity displays a leaderboard with players' usernames, scores, and ranks in
 * descending order. It receives the scoreboard data from the FirebaseActivity.
 */
public class LeaderboardActivity extends AppCompatActivity {
    // ListLayouts for the three columns of text
    private LinearLayout scoreListLayout,scoreListLayout2,scoreListLayout3;

    /**
     * Sets the content view to the associated XML file and hides the status bar. Gets the
     * scoreboard from the FirebaseActivity and displays sorted lists of the data.
     * @param savedInstanceState the instance state bundle
     */
    @SuppressLint("DefaultLocale")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        // Hide status bar
        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        // Get references to the list layouts that will hold the scores
        scoreListLayout = findViewById(R.id.score_list_layout);
        scoreListLayout2 = findViewById(R.id.score_list_layout2);
        scoreListLayout3 = findViewById(R.id.score_list_layout3);
        // Create a scoreboard object storing the usernames and scores from the FirebaseActivity
        Intent intent = getIntent();
        Scoreboard scoreboardObj = (Scoreboard) intent.getSerializableExtra("scoreboard_object");
        // Sort map by values (descending scores)
        scoreboardObj.scoreboard = sortByValue(scoreboardObj.scoreboard);

        // Initialize ranking at zero
        int ranking = 0;
        for (String username : scoreboardObj.scoreboard.keySet()) {
            // Increment ranking
            ranking++;
            // Create a textview for the ranking
            TextView rankView = new TextView(this);
            rankView.setTextSize(20);
            rankView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            rankView.setFontFeatureSettings("press_start_2p");
            rankView.setText(String.format("%d",ranking));
            // Create a textview for the username
            String sub_name = username.substring(36);
            TextView nameView = new TextView(this);
            nameView.setTextSize(20);
            nameView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            nameView.setFontFeatureSettings("press_start_2p");
            nameView.setText(String.format("%s", sub_name));
            // Create a textview for the score
            TextView scoreView = new TextView(this);
            scoreView.setTextSize(20);
            scoreView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            scoreView.setFontFeatureSettings("press_start_2p");
            scoreView.setText(String.format("%f",  scoreboardObj.scoreboard.get(username)));
            // Set the top three scores' color to red
            if(ranking <=3)
            {
                rankView.setTextColor(Color.RED);
                nameView.setTextColor(Color.RED);
                scoreView.setTextColor(Color.RED);
            }
            // Add the TextViews to their ListLayouts
            scoreListLayout.addView(rankView);
            scoreListLayout2.addView(nameView);
            scoreListLayout3.addView(scoreView);
        }

        // Return button
        Button return_button = (Button) findViewById(R.id.return_btn);
        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Switch back to Firebase activity
                Intent intent = new Intent(LeaderboardActivity.this, FirebaseActivity.class);
                startActivity(intent);
            }
        });
    }
}
