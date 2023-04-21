package com.example.game2d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class ChalkGameActivity extends AppCompatActivity {
    private ChalkGameClass gameView;
    private Button StartGameAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new ChalkGameClass(this);
        setContentView(R.layout.activity_chalk_game);

        StartGameAgain = (Button) findViewById(R.id.play_again_btn);

        StartGameAgain.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent mainIntent  = new Intent(ChalkGameActivity.this, ChalkActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(mainIntent);
            }

        });
    }

}