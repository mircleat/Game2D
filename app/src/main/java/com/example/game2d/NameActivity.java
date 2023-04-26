
package com.example.game2d;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import android.os.Handler;



public class NameActivity extends AppCompatActivity implements View.OnClickListener {

    TextView totalQuestionsTextView;
    TextView nameTextView;
    Button yes, no, exit;

    ImageButton pauseBtn;

    int score=0;
    int totalQuestion = QuestionAnswer.names.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    //for pop up intro
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        //for pop up intro
        layout = findViewById(R.id.nameActivity); // relative is the id of the layout of the page
        CreatepopUpwindow();

        totalQuestionsTextView = findViewById(R.id.total_question);
        nameTextView = findViewById(R.id.names);
        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);

        pauseBtn = (ImageButton) findViewById(R.id.pause_lb);


        //Context context = getContext(); // Get a Context object from somewhere
        QuestionAnswer QA = new QuestionAnswer(); // Create an instance of MyClass
        QA.run(this); // Call the run method on myObject, passing in the context object



        yes.setOnClickListener(this);
        no.setOnClickListener(this);

        totalQuestionsTextView.setText("Total questions: " + totalQuestion);

        //Pause menu implementation
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent pauseIntent = new Intent(NameActivity.this, NamePause.class);
                pauseIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(pauseIntent);
            }
        });

        loadNewQuestion();

    }

    //for pop up window
    private void CreatepopUpwindow() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popUpView=inflater.inflate(R.layout.mainpopup,null);

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