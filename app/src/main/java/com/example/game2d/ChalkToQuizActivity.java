package com.example.game2d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * This is a transition page/activity between the chalkGame and the chalkQuiz
 * that is run when the user successfully dodges all the chalk. It allows
 * users to continue to the chalkQuiz/chalkResult activity when they are ready.
 */
public class ChalkToQuizActivity extends AppCompatActivity {
    private Button resButton;
    private int chalkIndex; //stores the question the user was on before getting sent to the chalk dodging game.
    SharedPreferences preferences;
    MediaPlayer victorySound;

    /**
     * Initializes the sound effects and buttons as well as their mechanisms.
     * @param savedInstanceState the bundle of the instance of the game in case the
     * activity needs to be restored to this instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        victorySound = MediaPlayer.create(getApplicationContext(), R.raw.valorant_ace);
        victorySound.start();
        setContentView(R.layout.activity_chalk_to_quiz);
        resButton = findViewById(R.id.ChalktoQuizResume);

        preferences = getSharedPreferences("MY_PREFS",0);
        chalkIndex = preferences.getInt("ChalkQuestionIndex", 0);

        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        resButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Allows users to continue to the next activity when they click the resume button.
             * If the user was on the last question before getting sent to the chalk dodging,
             * the next activity is the chalk result page. Otherwise it is the chalkQuiz
             * and the next question.
             * @param view the button instance/click from the user on the continue button.
             */
            @Override
            public void onClick(View view) {
                victorySound.stop();
                if(chalkIndex != 4) {
                    Intent Quiztrns = new Intent(ChalkToQuizActivity.this, ChalkActivity.class);
                    Quiztrns.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(Quiztrns);
                }
                else
                {
                    preferences.edit().putInt("ChalkQuestionIndex", 0).apply();
                    Intent endQuiz = new Intent(ChalkToQuizActivity.this, ChalkResultActivity.class);
                    endQuiz.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(endQuiz);
                }
            }
        });
    }
}