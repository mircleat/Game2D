package com.example.game2d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * This is the pause window/activity for <code>ChalkGameActivity</code>
 * It is called when the user presses the pause button and allows the user
 * to continue when they want or exit to the main menu.
 */
public class PauseWindow extends AppCompatActivity {
    private Button resumeButton;
    private Button exitButton;

    /**
     * Sets the content to the related XML file and removes the status bar.
     * It also initializes the buttons and their intents/mechanics.
     * @param savedInstanceState the bundle of the instance of the game in case the
     * activity needs to be restored to this instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause_window);

        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        resumeButton = findViewById(R.id.chalkGameResumeButton);
        exitButton = findViewById(R.id.chalkGameExitButton);

        resumeButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Creates/executes the intent back to the <code>ChalkActivity</code> when
             * the user clicks on the resume/continue button. It reorders the activity
             * so that it isn't restarted and continues where they left off.
             * @param view the button instance/click from the user when they click on the resume button.
             */
            @Override
            public void onClick(View view)
            {
                Intent resumeIntent = new Intent(PauseWindow.this, ChalkGameActivity.class);
                resumeIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(resumeIntent);
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Creates/executes the intent back to <code>MainActivity</code> when
             * the user clicks ont he return to main menu button.
             * @param view the button instance/click from the user when they click on the
             * return to main menu button.
             */
            @Override
            public void onClick(View view) {
                Intent exitIntent = new Intent(PauseWindow.this, MainActivity.class);
                exitIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(exitIntent);
            }
        });
    }

}