package com.example.game2d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * ChalkQuizPause handles the pause/hint page for the ChalkActivity quiz. It creates buttons to
 * resume the game or exit back to the menu activity.
 */
public class ChalkQuizPause extends AppCompatActivity {
    private Button resumeButton;
    private Button exitButton;

    /**
     * Sets the content view to the associated XML file, hides the status bar, and creates a resume
     * and exit button to switch activities.
     * @param savedInstanceState the instance state bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chalk_quiz_pause);
        // Hide status bar
        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        // Resume button
        resumeButton = findViewById(R.id.chalkResumeButton);
        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                // Resume the ChalkActivity quiz game
                Intent resumeIntent = new Intent(ChalkQuizPause.this, ChalkActivity.class);
                resumeIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(resumeIntent);
            }
        });
        // Exit button
        exitButton = findViewById(R.id.chalkExitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Exit to the MainActivity classroom
                Intent exitIntent = new Intent(ChalkQuizPause.this, MainActivity.class);
                startActivity(exitIntent);

            }
        });
    }

}