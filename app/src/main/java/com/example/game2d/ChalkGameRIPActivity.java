package com.example.game2d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChalkGameRIPActivity extends AppCompatActivity {
    private Button resButton;

    MediaPlayer ripSound = MediaPlayer.create(getApplicationContext(), R.raw.gta_wasted);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ripSound.start();
        setContentView(R.layout.activity_chalk_game_ripactivity);
        resButton = findViewById(R.id.ChalkRIPResume);
        resButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ripSound.stop();
                Intent ChalkRIPtrns = new Intent(ChalkGameRIPActivity.this,MainActivity.class);
                ChalkRIPtrns.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(ChalkRIPtrns);
            }
        });
    }
}