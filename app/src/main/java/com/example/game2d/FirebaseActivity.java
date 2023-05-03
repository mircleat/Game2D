package com.example.game2d;


import static android.service.controls.ControlsProviderService.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.security.KeyStore;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This activity presents the user's ID and scores for
 * <code>NameActivity</code> and <code>ChalkActivity</code>
 * as well as their average overall score. It also gives them
 * the option to sync to the leaderboard and view it.
 */
public class FirebaseActivity extends AppCompatActivity {
    // Button and TextViews
    private Button SaveButton;
    TextView userName, userID,nameScore, chalkScore,avScore;
    // Scoreboard with map of scores for leaderboard
    Scoreboard big_userScoreMap = new Scoreboard();
    // Best chalk score (0-4)
    int bestChalk;
    // Best chalk score (0-100)
    Float percent;
    // Username and ID
    String CRE_username, CRE_userid;
    // Database
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**Sets the content to the related XML file and removes the status bar.
     * It also initializes the buttons and their intents/mechanics. Also retrieves
     * the scores from shared preferences and displays it on the screen alongside
     * retrieving data form the online leaderboard.
     * @param savedInstanceState the bundle of the instance of the game in case the
     * activity needs to be restored to this instance
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);

        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        //-----------DISPLAY-----------------------------------------------------------------
        // Fetching game data from SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MY_PREFS", 0);
        bestChalk = preferences.getInt("bestChalk",0); //get the chalk score (0-4)
        percent = preferences.getFloat("percent",0);
        Log.d(TAG,"best chalk: " + bestChalk + "  percent: " + percent);
        // Fetching user data from SharedPreferences
        SharedPreferences user_info = getSharedPreferences("USER_CREDENTIALS", 0);
        CRE_username = user_info.getString("username", "unnamed user");
        CRE_userid = user_info.getString("user_ID", "111111111111111111111111111111111111");

        // Display credentials on screen
        userName = (TextView) findViewById(R.id.username);
        userName.setText(CRE_username);
        userID = (TextView) findViewById(R.id.user_id);
        userID.setText(CRE_userid);

        // Display personal scores on screen
        nameScore = (TextView) findViewById(R.id.name_score);
        nameScore.setText( percent+"%");
        chalkScore = (TextView) findViewById(R.id.chalk_score);
        chalkScore.setText(bestChalk/4.0*100+"%");
        avScore = (TextView) findViewById(R.id.av_score);
        Double av = (bestChalk/4.0*100+percent)/2;
        DecimalFormat df = new DecimalFormat("##.##");
        String formattedNum = df.format(av);
        avScore.setText(formattedNum+"%");

        // Upload and retrieve data
        UploadData();
        RetrieveData();

        //-----------BUTTON-----------------------------------------------------------------
        // Button that sets data
        Button set_button = (Button) findViewById(R.id.set_btn);
        set_button.setOnClickListener(new View.OnClickListener() {
            /**
             * Uploads and retrieves data from the online leaderboard when the user
             * clicks on the sync button.
             * @param view the button instance/click from the user on the sync button.
             */
            @Override
            public void onClick(View view) {
                UploadData();
                RetrieveData();
                Toast.makeText(FirebaseActivity.this, "Data synced", Toast.LENGTH_SHORT).show();
            }
        });

        // Button that leads to the leaderboard
        Button lead_button = (Button) findViewById(R.id.leaderboard_btn);
        lead_button.setOnClickListener(new View.OnClickListener() {
            /**
             * Creates/executes the intent to <code>LeaderboardActivity</code> when
             * the user presses on the leaderboard button.
             * @param view the button instance/click from the user on the leaderboard button.
             */
            @Override
            public void onClick(View view) {
                // Switch to leaderboard activity
                Intent intent = new Intent(FirebaseActivity.this, LeaderboardActivity.class);
                intent.putExtra("scoreboard_object", big_userScoreMap);
                startActivity(intent);

            }
        });

        // Button that goes back to start activity
        Button back_button = (Button) findViewById(R.id.return_btn);
        back_button.setOnClickListener(new View.OnClickListener() {
            /**
             * Creates/executes the intent to <code>StartActivity</code> when
             * the user clicks on the return button.
             * @param view the button instance/click from the user on the return button
             */
            @Override
            public void onClick(View view) {
                // Switch back to start activity
                Intent intent = new Intent(FirebaseActivity.this, StartActivity.class);
                intent.putExtra("scoreboard_object", big_userScoreMap);
                startActivity(intent);

            }
        });
        //---------------------------------------------------------------------------------
    }

    /**
     * Converts the user's username and score to a map and uploads the data to the database.
     */
    private void UploadData() {
        // Create map
        Map<String, Object> user2 = new HashMap<>();
        user2.put("username",CRE_username);
        user2.put("score", percent/2 + bestChalk/4.0*50);
        // Upload to database
        DocumentReference userRef = db.collection("leaderboard").document(CRE_userid);
        userRef.set(user2, SetOptions.merge());
    }

    /**
     * Retrieves leaderboard data from the database. Individual user data is stored in a map with
     * the player's username and score, and then each individual map is put into the big map.
     */
    private void RetrieveData() {
        db.collection("leaderboard")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    /**
                     * When the task is completed, gets the data from the database as an individual
                     * map. Extracts the name, score, and ID from the individual map and puts it
                     * in the big map of leaderboard scores.
                     * @param task the task that was completed
                     */
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> userScoreMap = document.getData();
                                Log.d(TAG, "ID: "+document.getId());
                                String name = (String) userScoreMap.get("username");
                                Double scores = (Double) userScoreMap.get("score");
                                String userId = document.getId()+name;
                                big_userScoreMap.scoreboard.put(userId, scores);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}
