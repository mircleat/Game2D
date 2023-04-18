
package com.example.game2d;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;



public class NameActivity extends AppCompatActivity implements View.OnClickListener {

    TextView totalQuestionsTextView;
    TextView nameTextView;
    Button yes, no;

    int score=0;
    int totalQuestion = QuestionAnswer.names.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        totalQuestionsTextView = findViewById(R.id.total_question);
        nameTextView = findViewById(R.id.names);
        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);


        //Context context = getContext(); // Get a Context object from somewhere
        QuestionAnswer QA = new QuestionAnswer(); // Create an instance of MyClass
        QA.run(this); // Call the run method on myObject, passing in the context object



        yes.setOnClickListener(this);
        no.setOnClickListener(this);

        totalQuestionsTextView.setText("Total questions: " + totalQuestion);

        loadNewQuestion();

    }
    @Override
    public void onClick(View view) {
        //initial color white


        Button clickedButton = (Button) view;
        boolean choice;

        if(clickedButton.getId() == R.id.yes)
            choice = true;
        else
            choice = false;

        if(choice == QuestionAnswer.tfArray[currentQuestionIndex]) {
            score++;
            clickedButton.setBackgroundColor(Color.GREEN);
        }else{
            clickedButton.setBackgroundColor(Color.RED);
        }

        currentQuestionIndex++; //if this is more than the question we have, app would crash

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // code to be executed after delay
                loadNewQuestion();
            }
        }, 1500); // 5000 milliseconds = 5 seconds
        //if condition check answer + add score
    }

    void loadNewQuestion() {
        if(currentQuestionIndex == totalQuestion){
            finishQuiz();
            return;
        }
        nameTextView.setText(QuestionAnswer.names[currentQuestionIndex]);
        yes.setBackgroundColor(Color.BLACK);
        no.setBackgroundColor(Color.BLACK);

    }

    void finishQuiz(){
        String passStatus = "";
        if(score >totalQuestion*0.6){
            passStatus = "Passed";
        }else{
            passStatus = "Failed";
        }

        SharedPreferences preferences = getSharedPreferences("MY_PREFS", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("lastScore",score);
        editor.apply();

        Intent intent = new Intent(getApplicationContext(),NameResultActivity.class);//
        startActivity(intent);
        finish();

//        new AlertDialog.Builder(this)
//                .setTitle(passStatus)
//                .setMessage("Score is " + score + " out of "+ totalQuestion)
//                .setPositiveButton("Restart",(dialogInterface,i)-> restartQuiz())
//                .setCancelable(false)
//                .show();
    }

    void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        QuestionAnswer QA = new QuestionAnswer();
        QA.run(this);
        loadNewQuestion();
    }

}