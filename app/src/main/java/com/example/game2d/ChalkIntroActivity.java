package com.example.game2d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * ChalkIntroActivity is triggered when the player approaches the chalkboard.
 * It provides a transition page to the chalk quiz activity.
 */
public class ChalkIntroActivity extends AppCompatActivity {

    /**
     * Sets the content view to the corresponding XML file, hides the status bar, and creates a
     * button allowing the player to proceed to the chalk quiz activity.
     * @param savedInstanceState the instance state bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chalk_intro);
        // Hide status bar
        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        // Answer button to go to chalk quiz activity
        Button answerButton = findViewById(R.id.answerQuestion);
        answerButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Switches to the ChalkActivity quiz.
             */
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(ChalkIntroActivity.this, ChalkActivity.class);
                startActivity(intent);
            }
        });
    }
}