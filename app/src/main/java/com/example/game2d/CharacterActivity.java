package com.example.game2d;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CharacterActivity extends AppCompatActivity {

    public boolean selectedShortHair;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_selection);

        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        overridePendingTransition(0, 0);

        // select short hair button
        Button shortHairButton = (Button) findViewById(R.id.boy_btn);
        shortHairButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedShortHair = true;
            }
        });

        // select long hair button
        Button longHairButton = (Button) findViewById(R.id.girl_btn);
        longHairButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedShortHair = false;
            }
        });

        // Confirm button
        Button confirm_button = (Button) findViewById(R.id.confirm_btn);
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // switch back to main activity
                Intent intent = new Intent(CharacterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}