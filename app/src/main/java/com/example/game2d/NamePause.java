package com.example.game2d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
/**
 * This is the pause window/activity for <code>NameActivity</code>
 * It is called when the user presses the pause button and allows the user
 * to continue when they want or exit to the main menu.
 */
public class NamePause extends AppCompatActivity {
    private Button resumeButton;
    private Button exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
         * Sets the content to the related XML file and removes the status bar.
         * It also initializes the buttons and their intents/mechanics.
         * @param savedInstanceState the bundle of the instance of the game in case the
         * activity needs to be restored to this instance
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_pause);

        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        resumeButton = findViewById(R.id.nameResumeButton);
        exitButton = findViewById(R.id.nameGameExitButton);

        resumeButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Creates/executes the intent back to the <code>NameActivity</code> when
             * the user clicks on the resume/continue button. It reorders the activity
             * so that it isn't restarted and continues where they left off.
             * @param view the button instance/click from the user when they click on the resume button.
             */
            @Override
            public void onClick(View view)
            {
                Intent resumeIntent = new Intent(NamePause.this, NameActivity.class);
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
                Intent exitIntent = new Intent(NamePause.this, MainActivity.class);
                startActivity(exitIntent);

            }
        });
    }

}
