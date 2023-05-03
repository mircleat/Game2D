package com.example.game2d;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This is the start page/activity displayed after <code>SplashActivity</code>.
 * It allows users to start the game or look at the credits page
 * and the leaderboard.
 */
public class StartActivity extends AppCompatActivity {

    Button play, credit,leaderboard;

    /**
     * Sets the content view to the related XML file and removes the
     * status bar. Also initializes the buttons and their mechanics.
     * @param savedInstanceState the bundle of the instance of the game in case the
     * activity needs to be restored to this instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        overridePendingTransition(0, 0);


        play = (Button) findViewById(R.id.imageButton);
        play.setOnClickListener(new View.OnClickListener() {
            /**
             * Creates/executes the intent to <code>CharacterActivity</code>
             * when the user clicks on the start button.
             * @param view the button instance/click on the start button
             */
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CharacterActivity.class));
                finish();
            }
        });


        credit = (Button) findViewById(R.id.credit);
        credit.setOnClickListener(new View.OnClickListener() {
            /**
             * Creates/executes the intent to <code>CreditActivity</code>
             * when the user clicks on the credit button.
             * @param view the button instance/click from the user on the credit button.
             */
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CreditActivity.class));
                finish();
            }
        });

        leaderboard = (Button) findViewById(R.id.leaderboard_btn);
        leaderboard.setOnClickListener(new View.OnClickListener() {
            /**
             * creates/executes the intent to <code>FirebaseActivity</code>
             * when the user clicks on the leaderboard button
             * @param view the button instance/click from the user ont he leaderboard button.
             */
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), FirebaseActivity.class));
                finish();
            }
        });
    }
}
