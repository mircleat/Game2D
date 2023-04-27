package com.example.game2d;


import static android.service.controls.ControlsProviderService.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class FirebaseActivity extends AppCompatActivity {
//    private static final String USERNAME_KEY = "username";
//    private static final String SCORE_KEY = "score";

    private Button SaveButton;
    TextView nameScore, chalkScore;

    //public Map<String, Object> userScoreMap;
    public Map<String, Long> big_userScoreMap = new HashMap<>();

    int bestChalk;
    float percent;

    String username = "temp userame";


    //access the average accuracy data from nameResultActivity (does not work)
    //    NameResultActivity Name_Result_Class = new NameResultActivity();
    //    float name_average_percent = Name_Result_Class.percent;


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //private DocumentReference mDocRef = Fires.getInstance().document("sampleData");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);

        //-----------DISPLAY-----------------------------------------------------------------
        //fetching game data from shared Preference
        SharedPreferences preferences = getSharedPreferences("MY_PREFS", 0);
        bestChalk = preferences.getInt("bestChalk",0); //get the chalk score (0-4)
        percent = preferences.getFloat("percent",0);
        Log.d(TAG,"best chalk: " + bestChalk + "  percent: " + percent);


        //display personal score on screen
        nameScore = (TextView) findViewById(R.id.name_score);
        nameScore.setText( percent+"%");

        chalkScore = (TextView) findViewById(R.id.chalk_score);
        chalkScore.setText(bestChalk+" out of 4");

        //-----------BUTTON-----------------------------------------------------------------
        //setting data button
        Button set_button = (Button) findViewById(R.id.set_btn);
        set_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadData();
            }
        });

        //fetching data button
        Button fetch_button = (Button) findViewById(R.id.fetch_btn);
        fetch_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrieveData();
            }
        });

        //button that leads to the leaderboard
        Button lead_button = (Button) findViewById(R.id.leaderboard_btn);
        lead_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // switch back to main activity
                Intent intent = new Intent(FirebaseActivity.this, LeaderboardActivity.class);
                startActivity(intent);
            }
        });
        //---------------------------------------------------------------------------------
    }






    private void UploadData() {
        Map<String, Object> user2 = new HashMap<>();
        user2.put("username",username);
        user2.put("score", percent/2 + bestChalk/4.0*50);//right now it's just the chalk average accuracy
        //db.collection("users").document("new33").set(user2);


        String userId = "e9kSCVavuhpXiIGNMkAh";
        DocumentReference userRef = db.collection("leaderboard").document(userId);
        userRef.set(user2, SetOptions.merge());


    }




    private void RetrieveData() {
        //retrieve data from the database
        db.collection("leaderboard")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> userScoreMap = document.getData();
                                Log.d(TAG, "ID: "+document.getId());
                                for (Map.Entry<String, Object> entry : userScoreMap.entrySet()) {
                                    //String user = entry.getKey();
                                    Object scoreObj = entry.getValue(); //this is the score value
                                    Long score = null;
                                    if (scoreObj instanceof Long) { //if it's the score_value
                                        score = (Long) scoreObj;
                                        //big_userScoreMap.put(document.getId(),score);
                                        Log.d(TAG,"BIG size : "+ big_userScoreMap.size());
                                        //Log.d(TAG, "User: " + user + ", Score: " + score);
                                    }
                                    else { //else it's the username
                                        Log.d(TAG,"the username: "+scoreObj.toString());
                                    }
                                    big_userScoreMap.put(scoreObj.toString(),score); // add <username,score> into the big
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }


}
//    public void saveQuote(View view) {
//        EditText quoteView = (EditText) findViewById(R.id.editUsername);
//        //EditText authorView = (EditText) findViewById(R.id.editTextAuthor);
//        String quoteText = quoteView.getText().toString();
//        //String authorText = authorView.getText().toString();
//
//        if (quoteText.isEmpty()) {return;}
//
////        Map<String, Object> dataToSave = new HashMap<String, Object>();
////        dataToSave.put(USERNAME_KEY,quoteText);
////        //dataToSave.put(SCORE_KEY, authorText);
////        mDocRef.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
////            @Override
////            public void onSuccess(Void aVoid) {
////                Log.d("Leaderboard", "Score has been saved!");
////            }
////        }).addOnFailureListener(new OnFailureListener() {
////            @Override
////            public void onFailure(@NonNull Exception e) {
////                Log.d("Leaderboard","Score is not saved!", e);
////            }
////        });
//
//        Map<String, Object> user = new HashMap<String, Object>();
//        user.put(USERNAME_KEY, "Test");
//        user.put(SCORE_KEY, 100);
//        db.collection("leaderboard")
//                .add(user)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d("Leaderboard", "document snapshot added with ID: " +documentReference);
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("Leaderboard","Error adding Document", e);
//                    }
//                });
//    }
//
//    //button that goes to leaderboard
//
//}


//        EditText quoteView = (EditText) findViewById(R.id.editUsername);
//        SaveButton = findViewById(R.id.save_btn);
//        SaveButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                String quoteText = quoteView.getText().toString();
//            }
//        });


// Create a new user with a first and last name
//        Map<String, Object> user = new HashMap<>();
//        user.put("username", "test2");
//        user.put("score", 10000);
//
//
//        // Add a new document with a generated ID
//        db.collection("users")
//                .add(user)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error adding document", e);
//                    }
//                });