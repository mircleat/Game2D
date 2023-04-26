package com.example.game2d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class QuizToChalkActivity extends AppCompatActivity {
    private Button resButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_to_chalk);
        resButton = findViewById(R.id.QuiztoChalkResume);

        resButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Chalktrns = new Intent(QuizToChalkActivity.this,ChalkGameActivity.class);
                startActivity(Chalktrns);
            }
        });
        {

        }

    }
}