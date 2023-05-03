package com.example.game2d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * This is the transition page/activity between <code>ChalkActivity</code>  (chalk Quiz)
 * and <code>ChalkGameActivity</code> (chalk dodging activity)
 * It is called when a user gets the question wrong in <code>ChalkActivity</code>
 * It allows users to continue to <code>ChalkGameActivity</code> when they
 * are ready.
 */
public class QuizToChalkActivity extends AppCompatActivity {
    private Button resButton;

    /**
     * Initializes the sound effects and buttons as well as their mechanisms.
     * @param savedInstanceState the bundle of the instance of the game in case the
     * activity needs to be restored to this instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_to_chalk);
        resButton = findViewById(R.id.QuiztoChalkResume);

        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        resButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Creates/executes the intent to <code>ChalkGameActivity</code>
             * when the user hits continue.
             * @param view the button instance/click from the user on the continue button.
             */
            @Override
            public void onClick(View view) {
                Intent Chalktrns = new Intent(QuizToChalkActivity.this, ChalkGameActivity.class);
                Chalktrns.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(Chalktrns);
            }
        });

    }
}