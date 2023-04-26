package com.example.game2d;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CreditActivity extends AppCompatActivity { //

    @SuppressLint("SetTextI18n")
    @Override
    //for back button
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit); //

        // back button
        Button back_button = (Button) findViewById(R.id.back_btn);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // switch back to main activity
                Intent intent = new Intent(CreditActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });
    }
}