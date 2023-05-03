package com.example.game2d;
import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.UUID;

/**
 * CharacterActivity manages the character selection screen. This is where the user can enter their
 * username and select their preferred character before starting the actual game. It also generates
 * a random ID if the user doesn't have one yet for the online leaderboard.
 */
public class CharacterActivity extends AppCompatActivity {
    // Character selection
    public boolean selectedShortHair;
    // Opening sound effect
    MediaPlayer openSound;
    // User's username and generated ID
    String username;
    String ID;

    /**
     * Sets up the character selection screen by hiding the status bar and reading from and writing
     * to the user's credentials in SharedPreferences. Allows the user to input and save their
     * username and select between two characters.
     * @param savedInstanceState the instance state bundle
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_selection);
        // Opening sound
        openSound = MediaPlayer.create(this, R.raw.metal_click);
        // Hide status bar
        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        // Override transition animation
        overridePendingTransition(0, 0);

        // Get character selection preference from SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MY_PREFS", 0);
        selectedShortHair = preferences.getBoolean("shortHairSelection", false);
        // Get username and ID from SharedPreferences
        SharedPreferences user_info = getSharedPreferences("USER_CREDENTIALS", 0);
        username = user_info.getString("username", null);
        ID = user_info.getString("user_ID", null);
        // Username input
        View textInputLayout = findViewById(R.id.textInputLayout);
        TextInputEditText editText = findViewById(R.id.editText);
        View save_button = findViewById(R.id.button);
        // Display username and ID
        TextView credentialDisplay = findViewById(R.id.credential_display);
        credentialDisplay.setText("Your \nUsername: \n"+username+"\n\nUnique ID:\n"+ID);

        // Save button
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save username to shared preferences
                username = editText.getText().toString();
                SaveName(username);
                // Update display of username and ID
                credentialDisplay.setText("Your \nUsername: \n"+username+"\n\nUnique ID:\n"+ID);
                // Username toast
                Toast.makeText(CharacterActivity.this, "Username Saved: " + username, Toast.LENGTH_SHORT).show();
            }
        });

        // Create short hair button
        Button shortHairButton = (Button) findViewById(R.id.boy_btn);
        // Create long hair button
        Button longHairButton = (Button) findViewById(R.id.girl_btn);

        // Short hair button
        shortHairButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save short hair as true
                preferences.edit().putBoolean("shortHairSelection", true).apply();
                // Update button colors
                shortHairButton.setBackgroundColor(Color.GREEN);
                longHairButton.setBackgroundColor(Color.BLACK);
            }
        });

        // Long hair button
        longHairButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save short hair as false
                preferences.edit().putBoolean("shortHairSelection", false).apply();
                // Update button colors
                longHairButton.setBackgroundColor(Color.GREEN);
                shortHairButton.setBackgroundColor(Color.BLACK);
            }
        });

        // Confirm button
        Button confirm_button = (Button) findViewById(R.id.confirm_btn);
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start main activity
                Intent intent = new Intent(CharacterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Saves the username input to USER_CREDENTIALS in SharedPreferences.
     * Also generates a random ID if the user has not had one yet.
     * @param username a string storing the new username
     */
    void SaveName(String username)
    {
        // Save username to USER_CREDENTIALS
        SharedPreferences user_info = getSharedPreferences("USER_CREDENTIALS", 0);
        user_info.edit().putString("username", username).apply();
        // Get user ID
        ID = user_info.getString("user_ID", null);
        // Assign a random ID if the user doesn't already have one
        if (user_info.getString("user_ID", null) == null){
            ID = UUID.randomUUID().toString();
            user_info.edit().putString("user_ID", ID).apply();
        }
    }
}