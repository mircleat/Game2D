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

public class LeaderboardActivity extends AppCompatActivity {

    //big_userScoreData
    private LinearLayout scoreListLayout,scoreListLayout2,scoreListLayout3;

    @SuppressLint("DefaultLocale")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        Log.d("LEADERBOARD", "Leaderboard activity");

        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        //Typeface customFont = Typeface.createFromAsset(getAssets(), "custom_font.ttf");

        // Get reference to the LinearLayout that will hold the scores
        scoreListLayout = findViewById(R.id.score_list_layout);
        scoreListLayout2 = findViewById(R.id.score_list_layout2);
        scoreListLayout3 = findViewById(R.id.score_list_layout3);

        // Create map to store usernames and scores

        Intent intent = getIntent();
        Scoreboard scoreboardObj = (Scoreboard) intent.getSerializableExtra("scoreboard_object");

        HashMap<String, Integer> scoreMap = new HashMap<String, Integer>();
        scoreMap.put("Bob", 75);
        scoreMap.put("Dave", 50);
        scoreMap.put("Charlie", 60);
        scoreMap.put("Alice", 100);
        scoreMap.put("1", 75);
        scoreMap.put("2", 50);
        scoreMap.put("3", 60);
        scoreMap.put("4", 100);
        scoreMap.put("5", 75);
        scoreMap.put("6", 50);
        scoreMap.put("7", 60);
        scoreMap.put("8", 100);
        scoreMap.put("ef", 100);
        scoreMap.put("ejjfkwl", 75);
        scoreMap.put("ehs", 50);
        scoreMap.put("dhlow9", 60);
        scoreMap.put("fnjwl", 100);


        // Sort map by values (descending scores)
        HashMap<String, Integer> sortedScoreMap = new HashMap<String, Integer>();
        sortedScoreMap = (HashMap<String, Integer>) sortByValue(scoreMap);
        scoreboardObj.scoreboard = sortByValue(scoreboardObj.scoreboard);

        int ranking = 0;
        for (String username : scoreboardObj.scoreboard.keySet()) {
            ranking++;
            Log.d("SCORETEXT", username + String.valueOf(scoreboardObj.scoreboard.get("scrub daddy")));
            // Create a textview for the username and score
            TextView rankView = new TextView(this);
            rankView.setTextSize(20);
            rankView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            rankView.setFontFeatureSettings("press_start_2p");
            rankView.setText(String.format("%d",ranking));

            String sub_name = username.substring(36);
            TextView nameView = new TextView(this);
            nameView.setTextSize(20);
            nameView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            nameView.setFontFeatureSettings("press_start_2p");
            nameView.setText(String.format("%s", sub_name));

            TextView scoreView = new TextView(this);
            scoreView.setTextSize(20);
            scoreView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            scoreView.setFontFeatureSettings("press_start_2p");
            scoreView.setText(String.format("%f",  scoreboardObj.scoreboard.get(username)));

            if(ranking <=3)
            {
                rankView.setTextColor(Color.RED);
                nameView.setTextColor(Color.RED);
                scoreView.setTextColor(Color.RED);
            }

            // Add the TextView to the LinearLayout
            scoreListLayout.addView(rankView);
            scoreListLayout2.addView(nameView);
            scoreListLayout3.addView(scoreView);
        }

        /*
        // Loop through keys (usernames) and add username and score to the layout
        for (String username : sortedScoreMap.keySet()) {
            // Create a textview for the username and score
            TextView scoreView = new TextView(this);
            scoreView.setTextSize(20);
            scoreView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            scoreView.setFontFeatureSettings("press_start_2p");
            scoreView.setText(String.format("%s: %d", username, sortedScoreMap.get(username)));
            // Add the TextView to the LinearLayout
            scoreListLayout.addView(scoreView);
        }

         */


        // Return button
        Button confirm_button = (Button) findViewById(R.id.return_btn);
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // switch back to main activity
                Intent intent = new Intent(LeaderboardActivity.this, FirebaseActivity.class);
                startActivity(intent);
            }
        });
    }
}
