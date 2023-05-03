package com.example.game2d;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This is the page/activity that credits our group project.
 * It is called when the user presses on the credit button in
 * <code>StartActitity</code>
 */
public class CreditActivity extends AppCompatActivity { //

    /**
     * Sets the view to the related XML file and removes the action bar. Also
     * initiates all the buttons and their mechanics.
     * @param savedInstanceState the bundle of the instance of the game in case the
     * activity needs to be restored to this instance
     */
    @SuppressLint("SetTextI18n")
    @Override
    //for back button
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        // back button
        Button back_button = (Button) findViewById(R.id.back_btn);
        back_button.setOnClickListener(new View.OnClickListener() {
            /**
             * Creates/executes the intent to <code>StartActivity</code> when
             * the user presses on the return button.
             * @param view the button instance/click from the user on the return button.
             */
            @Override
            public void onClick(View view) {
                // switch back to main activity
                Intent intent = new Intent(CreditActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });
    }
}