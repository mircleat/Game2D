package com.example.game2d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class QuizToChalkActivity extends AppCompatActivity {
    private Button resButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_to_chalk);
        resButton = findViewById(R.id.QuiztoChalkResume);

        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        resButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Chalktrns = new Intent(QuizToChalkActivity.this, ChalkGameActivity.class);
                Chalktrns.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(Chalktrns);
            }
        });

    }
}