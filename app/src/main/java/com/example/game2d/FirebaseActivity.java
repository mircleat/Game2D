package com.example.game2d;


import static android.service.controls.ControlsProviderService.TAG;

import android.content.Intent;
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

import java.security.KeyStore;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FirebaseActivity extends AppCompatActivity {
    private static final String USERNAME_KEY = "username";
    private static final String SCORE_KEY = "score";

    private Button SaveButton;

    TextView nameScore, chalkScore;

    public Map<String, Object> userScoreMap;
    public Map<String, Object> big_userScoreMap;


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //private DocumentReference mDocRef = Fires.getInstance().document("sampleData");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);

//        EditText quoteView = (EditText) findViewById(R.id.editUsername);
//        SaveButton = findViewById(R.id.save_btn);
//        SaveButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                String quoteText = quoteView.getText().toString();
//            }
//        });


        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("username", "test2");
        user.put("score", 10000);


        // Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

        Map<String, Object> user2 = new HashMap<>();
        user2.put("username", "Elena is here!");
        user2.put("score", 444);
        db.collection("users").document("Elena").set(user2);





        //retrieve data from the database
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> userScoreMap = document.getData();
                                for (Map.Entry<String, Object> entry : userScoreMap.entrySet()) {
                                    String user = entry.getKey();
                                    Object scoreObj = entry.getValue();
                                    if (scoreObj instanceof Long) {
                                        Long score = (Long) scoreObj;
                                        //Log.d(TAG, "User: " + user + ", Score: " + score);
                                    }
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        //button that leads to the leaderbaord
        Button back_button = (Button) findViewById(R.id.leaderboard_btn);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // switch back to main activity
                Intent intent = new Intent(FirebaseActivity.this, LeaderboardActivity.class);
                startActivity(intent);
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
