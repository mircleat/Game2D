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
 * ArtActivity is a credit page for the game's art.
 * It is triggered when the player approaches the painting.
 */
public class ArtActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art);
        // Hide status bar
        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        // Back button
        Button back_button = (Button) findViewById(R.id.return_btn);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Switch back to main activity
                Intent intent = new Intent(ArtActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}