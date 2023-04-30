package com.example.game2d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChalkToQuizActivity extends AppCompatActivity {
    private Button resButton;

    MediaPlayer victorySound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        victorySound = MediaPlayer.create(getApplicationContext(), R.raw.valorant_ace);
        victorySound.start();
        setContentView(R.layout.activity_chalk_to_quiz);
        resButton = findViewById(R.id.ChalktoQuizResume);

        resButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                victorySound.stop();
                Intent Quiztrns = new Intent(ChalkToQuizActivity.this,ChalkActivity.class);
                Quiztrns.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(Quiztrns);
            }
        });
    }
}