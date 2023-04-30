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

public class CharacterActivity extends AppCompatActivity {

    public boolean selectedShortHair;

    MediaPlayer openSound;

    String username ; // <--- store the username in this variable

    String ID;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_selection);

        openSound = MediaPlayer.create(this, R.raw.metal_click);

        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        overridePendingTransition(0, 0);

        SharedPreferences preferences = getSharedPreferences("MY_PREFS", 0);
        selectedShortHair = preferences.getBoolean("shortHairSelection", false);//get data

        //SaveName(username);
        SharedPreferences user_info = getSharedPreferences("USER_CREDENTIALS", 0);
        //user_info.edit().putString("username", user_name).apply();
        username = user_info.getString("username", null);
        ID = user_info.getString("user_ID", null);
        Log.d(TAG,"username is now "+username);
        Log.d(TAG,"user id is now "+ ID);

        //username input
        View textInputLayout = findViewById(R.id.textInputLayout);
        TextInputEditText editText = findViewById(R.id.editText);
        View button = findViewById(R.id.button);

        TextView credentialDisplay = findViewById(R.id.credential_display);
        credentialDisplay.setText("Your \nUsername: \n"+username+"\n\nUnique ID:\n"+ID);



        //this button saves name, create ID, and display name/ID on greenscreen
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = editText.getText().toString();
                SaveName(username);
                credentialDisplay.setText("Your \nUsername: \n"+username+"\n\nUnique ID:\n"+ID);
                Toast.makeText(CharacterActivity.this, "Username Saved: " + username, Toast.LENGTH_SHORT).show();
            }
        });

        // select short hair button
        Button shortHairButton = (Button) findViewById(R.id.boy_btn);
        // select long hair button
        Button longHairButton = (Button) findViewById(R.id.girl_btn);

        shortHairButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences.edit().putBoolean("shortHairSelection", true).apply();
                shortHairButton.setBackgroundColor(Color.GREEN);
                longHairButton.setBackgroundColor(Color.BLACK);
            }
        });

        longHairButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences.edit().putBoolean("shortHairSelection", false).apply();
                longHairButton.setBackgroundColor(Color.GREEN);
                shortHairButton.setBackgroundColor(Color.BLACK);
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

    void SaveName(String username) // For saving name to shared preference - this method should be called after name is saved
    {
        //save username to USER_CREDENTIALS
        String user_name = username;
        SharedPreferences user_info = getSharedPreferences("USER_CREDENTIALS", 0);
        user_info.edit().putString("username", user_name).apply();
        ID = user_info.getString("user_ID", null);

        //assign a random ID when log in for the first time.
        if (user_info.getString("user_ID", null) == null){
            ID = UUID.randomUUID().toString();
            Log.d(TAG, "generated ID is: " + ID);
            user_info.edit().putString("user_ID", ID).apply();
        }else {
            Log.d(TAG,"User ID already generated:" +user_info.getString("user_ID", null));
        }

    }

}