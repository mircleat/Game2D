package com.example.game2d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * This is the transition page to <code>NameActivity</code>
 * and briefly introduces the activity by relating it to the class roster.
 * It allows the user to continue to <code>NameActivity</code> when they want.
 */
public class NameIntroActivity extends AppCompatActivity {
    /**
     * Sets the content view to the related XML file and removes the
     * status bar. Also initializes the buttons and their mechanics.
     * @param savedInstanceState the bundle of the instance of the game in case the
     * activity needs to be restored to this instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_intro);

        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        Button readButton = findViewById(R.id.openNameList);
        readButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Creates/executes the intent to <code>NameActivity</code>
             * when the user hits continue.
             * @param view the button instance/click from the user on the continue button.
             */
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(NameIntroActivity.this, NameActivity.class);
                startActivity(intent);
            }
        });

    }
}