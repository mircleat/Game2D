package com.example.game2d;

import static com.example.game2d.MapUtil.sortByValue;
//import static com.example.game2d.FirebaseActivity.;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    private LinearLayout scoreListLayout;

    @SuppressLint("DefaultLocale")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        //Typeface customFont = Typeface.createFromAsset(getAssets(), "custom_font.ttf");

        // Get reference to the LinearLayout that will hold the scores
        scoreListLayout = findViewById(R.id.score_list_layout);

        // Create map to store usernames and scores
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
    }
}
