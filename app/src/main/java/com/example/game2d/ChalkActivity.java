package com.example.game2d;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChalkActivity extends AppCompatActivity implements View.OnClickListener{

    MediaPlayer backgroundMusic;

    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button ansA, ansB, ansC, ansD;
    Button submitBtn/*, helpBtn*/;
    
    ImageButton pauseBtn;

    int chalkscore = 0;
    int totalWrong = 0;
    int totalQuestion = ChalkQuestionAnswer.question.length;
    int currentQuestionIndex = 0;
    int sendQuestionIndex = 0;
    String selectedAnswer = "";

    //for pop up intro
    RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chalk);

        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        // BGM
        backgroundMusic = MediaPlayer.create(this,R.raw.tanpopo);
        backgroundMusic.setLooping(true);
        backgroundMusic.start();

        //for pop up intro
        layout = findViewById(R.id.chalkActivity); // relative is the id of the layout of the page
        CreatepopUpwindow();

        /*totalQuestionsTextView = findViewById(R.id.total_question);*/
        questionTextView = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        submitBtn = findViewById(R.id.submit_btn);
        
        pauseBtn = (ImageButton) findViewById(R.id.pause_lb);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        /*helpBtn.setOnClickListener(this);*/

        /*totalQuestionsTextView.setText("Total questions : "+totalQuestion);*/

        //Pause menu implementation
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent pauseIntent = new Intent(ChalkActivity.this, ChalkQuizPause.class);
                pauseIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(pauseIntent);
            }
        });

        loadNewQuestion();

    }

    //for pop up intro
    private void CreatepopUpwindow() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popUpView=inflater.inflate(R.layout.popupforchalk,null);

        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height= ViewGroup.LayoutParams.MATCH_PARENT;
        boolean focusable = true;
        PopupWindow popupWindow = new PopupWindow(popUpView, width,height,focusable);
        layout.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(layout, Gravity.BOTTOM,0,0);
            }
        });
        TextView Continue;
        Continue = popUpView.findViewById(R.id.Continue);
        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

    }
    @Override
    public void onClick(View view) {
        //Initializing Access to shared preferences;
        SharedPreferences preferences = getSharedPreferences("MY_PREFS", 0);
        sendQuestionIndex = preferences.getInt("ChalkQuestionIndex", 0);

        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);

        Button clickedButton = (Button) view;
        if(clickedButton.getId()==R.id.submit_btn){
            if(selectedAnswer == "") {
                new AlertDialog.Builder(this)
                        .setMessage("Please select an answer")
                        .show();
            } else if(selectedAnswer.equals(ChalkQuestionAnswer.correctAnswers[currentQuestionIndex])){
                //SFX
                MediaPlayer correctSound = MediaPlayer.create(this, R.raw.correct);
                if (currentQuestionIndex != 3) {
                    correctSound.start();
                }
                selectedAnswer = "";
                chalkscore++;
                currentQuestionIndex++;
                preferences.edit().putInt("ChalkQuestionIndex", currentQuestionIndex).apply();
                loadNewQuestion();
            } else {
                MediaPlayer wrongSound = MediaPlayer.create(this, R.raw.wrong);
                wrongSound.start();
                selectedAnswer = "";
                Log.d("QUESTION", "selectedAnswer clear");
                totalWrong++;
                Log.d("QUESTION", "totalWrong++");
                Intent spawnGame = new Intent(getApplicationContext(), QuizToChalkActivity.class);
                spawnGame.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                Log.d("QUESTION", "Starting chalk game...");
                startActivity(spawnGame);
                currentQuestionIndex++;
                preferences.edit().putInt("ChalkQuestionIndex", currentQuestionIndex).apply();
                if(currentQuestionIndex != 4)
                {
                    loadNewQuestion();
                }
                else
                {
                    preferences.edit().putInt("lastChalkScore", chalkscore).apply();
                }
            }
        } else {
            //choices button clicked
            selectedAnswer  = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.MAGENTA);
        }
    }

    void loadNewQuestion(){
        // log question numbers
        Log.d("QUESTION", "current question index: " + String.valueOf(currentQuestionIndex));
        //Log.d("QUESTION", String.valueOf(currentQuestionIndex));
        if(currentQuestionIndex == totalQuestion ){
            Log.d("QUESTION", "finishing quiz...");
            finishQuiz();
            return;
        }

        questionTextView.setText(ChalkQuestionAnswer.question[currentQuestionIndex]);

        int[] orders = new int[]{0, 1, 2, 3};
        // Randomize orders[]
        Random rand = new Random();

        for (int i = 0; i < orders.length; i++) {
            // Switch i and randomIndexToSwap
            int randomIndexToSwap = rand.nextInt(orders.length);

            // temp vars to store element at randomIndex
            int tempOrder = orders[randomIndexToSwap];

            // element at randomIndex is now element at i (2 i duplicates)
            orders[randomIndexToSwap] = orders[i];

            // element at i is now element at randomIndex (via temp vars)
            orders[i] = tempOrder;
        }

        /*ansA.setText(ChalkQuestionAnswer.choices[currentQuestionIndex][orders[0]]);
        ansB.setText(ChalkQuestionAnswer.choices[currentQuestionIndex][orders[1]]);
        ansC.setText(ChalkQuestionAnswer.choices[currentQuestionIndex][orders[2]]);
        ansD.setText(ChalkQuestionAnswer.choices[currentQuestionIndex][orders[3]]);*/
        ansA.setText(ChalkQuestionAnswer.choices[currentQuestionIndex][0]);
        ansB.setText(ChalkQuestionAnswer.choices[currentQuestionIndex][1]);
        ansC.setText(ChalkQuestionAnswer.choices[currentQuestionIndex][2]);
        ansD.setText(ChalkQuestionAnswer.choices[currentQuestionIndex][3]);
    }

    void finishQuiz(){
//        Intent intent = new Intent(ChalkActivity.this, MainActivity.class);
//        startActivity(intent);

        SharedPreferences preferences = getSharedPreferences("MY_PREFS", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("lastChalkScore",chalkscore).apply();

        editor.putInt("ChalkQuestionIndex",0).apply();

        Intent intent = new Intent(getApplicationContext(),ChalkResultActivity.class);//
        startActivity(intent);
        finish();
    }

    protected void onPause() {
        super.onPause();
        backgroundMusic.stop();
    }

}