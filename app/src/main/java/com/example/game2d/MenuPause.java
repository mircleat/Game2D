package com.example.game2d;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * This is the pause page/activity for <code>MainActivity</code>
 * It is called when the user presses on the pause button in the main menu
 * and allows them to return to the start page if they want.
 */
public class MenuPause extends AppCompatActivity {
    private Button resButton;
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
        setContentView(R.layout.activity_menu_pause);

        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        resButton = findViewById(R.id.menuResumeButton);
        resButton.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent resIntent = new Intent(MenuPause.this, MainActivity.class);
                startActivity(resIntent);
            }
        });

        exitButton = findViewById(R.id.menuExitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent exitIntent = new Intent(MenuPause.this, StartActivity.class);
                startActivity(exitIntent);
            }
        });
    }
}