
package com.example.game2d;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
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

/**
 * This is the Name quiz which uses <code>QuestionAnswer</code> to present a
 * randomized combination to the user and asks if it is a person in our class.
 * It checks if the user has answered correctly and plays sound effects accordingly
 * while updating the score.
 */

public class NameActivity extends AppCompatActivity implements View.OnClickListener {

    TextView totalQuestionsTextView;
    TextView nameTextView;
    Button yes, no, exit;

    MediaPlayer correct, wrong, backgroundMusic;

    ImageButton pauseBtn;

    int score=0;
    int totalQuestion = QuestionAnswer.names.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    //for pop up intro
    RelativeLayout layout;

    /**Sets the content view to the related XML file and removes the
     * status bar. Also initializes the buttons and their mechanics and the sound elements.
     * It also checks if the user answered correctly or not.
     * @param savedInstanceState the bundle of the instance of the game in case the
     * activity needs to be restored to this instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        correct = MediaPlayer.create(this, R.raw.correct);
        wrong = MediaPlayer.create(this, R.raw.gasp);
        backgroundMusic = MediaPlayer.create(this, R.raw.kahoot_music);

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

        backgroundMusic.setLooping(true);

        yes.setOnClickListener(this);
        no.setOnClickListener(this);

        totalQuestionsTextView.setText("Total questions: " + totalQuestion);

        //Pause menu implementation
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Creates/executes intent to pause page for <code>NameActivity<code/>
             * when the user clicks on the pause button. It reorders the activity so
             * that when the user returns to this activity they resume where they left off.
             * @param view
             */
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
            /**
             * Creates a popup window at the start of the activity with
             * an introduction to the activity, allows users to continue to
             * <code>NameActivity</code> at their convenience.
             */
            @Override
            public void run() {
                popupWindow.showAtLocation(layout, Gravity.BOTTOM,0,0);
            }
        });
        TextView Continue;
        Continue = popUpView.findViewById(R.id.Continue);
        Continue.setOnClickListener(new View.OnClickListener() {
            /**
             * Dismisses the popup window when the user clicks on continue,
             * allowing users to continue to <code>NameActivity</code>
             * @param view the buttton instance/click from the user on the continue button
             */
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

    }

    /**
     * Checks if the button the user clicked is associated with the
     * correct answer or not and changing the background of the button and
     * sound effects to indicate that to the users.
     * @param view button instance/click from the user on the button.
     */
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
            correct.start();
            clickedButton.setBackgroundColor(Color.GREEN);
        }else{
            wrong.start();
            clickedButton.setBackgroundColor(Color.RED);
        }

        currentQuestionIndex++; //if this is more than the question we have, app would crash

        new Handler().postDelayed(new Runnable() {
            /**
             * Loads the next question 1500 ms after the choice has been checked and
             * the effects have been played.
             */
            @Override
            public void run() {
                // code to be executed after delay
                loadNewQuestion();
            }
        }, 1500); // 5000 milliseconds = 5 seconds
        //if condition check answer + add score
    }

    /**
     * Loads the next question unless it is the last question in
     * which case it loads the final page. It also sets the background colour
     * for the buttons.
     */
    void loadNewQuestion() {
        if(currentQuestionIndex == totalQuestion){
            finishQuiz();
            return;
        }
        nameTextView.setText(QuestionAnswer.names[currentQuestionIndex]);
        yes.setBackgroundColor(Color.BLACK);
        no.setBackgroundColor(Color.BLACK);


    }

    /**
     * Stores the score in sharedPreferences so that it persists when the app
     * is closed and reopened. It then creates/executes the intent to the
     * score page for <code>NameActivity</code>
     */
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

    /**
     * Resets the variables and the questions.
     */
    void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        QuestionAnswer QA = new QuestionAnswer();
        QA.run(this);
        loadNewQuestion();
    }

    /**
     * Starts the music when the activity starts.
     */
    protected void onStart() {
        super.onStart();
        backgroundMusic.start();
    }

    /**
     * Pauses the music when the activity is paused.
     */
    protected void onPause() {
        super.onPause();
        backgroundMusic.pause();
    }

}