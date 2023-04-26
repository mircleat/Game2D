package com.example.game2d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class ChalkQuizPause extends AppCompatActivity {
    private Button resumeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chalk_quiz_pause);

        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        resumeButton = findViewById(R.id.chalkResumeButton);

        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent resumeIntent = new Intent(ChalkQuizPause.this, ChalkActivity.class);
                resumeIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(resumeIntent);
            }
        });
    }

}