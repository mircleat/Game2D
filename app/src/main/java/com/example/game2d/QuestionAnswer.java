package com.example.game2d;


import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class creates a randomized combination of a first and last name
 * from the database in the class.
 */

public class QuestionAnswer {

    private static final String TAG = "QuestionAnswer";
    private final Random random = new Random();
    private List<String> fullName;
    private List<Integer> usedIndex = new ArrayList<>();

    public static String[] names = new String[5];
    public static boolean[] tfArray = new boolean[5];

    /**
     * Retrieves a first name from a given list of full names
     * @param names the list containing strings of full names
     * @param index index of the full name from which the first name is retrieved
     * @return a string containing the first name at the index from the list given
     */
    public String first(List<String> names, int index) {
        //function usage: get firstname from list with index
        String full = names.get(index);
        int i = 0;
        while (i < full.length() && full.charAt(i) != ' ') {
            i++;
        }
        return full.substring(0, i);
    }

    /**
     * Retrieves a last name from a given list of full names
     * @param names the list containing strings of full names
     * @param index index of the full name from which the last name is retrieved
     * @return a string containing the last name at the index from the list given
     */
    public String last(List<String> names, int index) {
        //function usage: get lastname from list with index
        String full = names.get(index);
        int i = 0;
        while (i < full.length() && full.charAt(i) != ' ') {
            i++;
        }
        int j = i + 1, k = 0;
        while (j < full.length() && full.charAt(j) != ' ') {
            j++;
            k++;
        }
        return full.substring(i + 1, i + 1 + k);
    }

    /**
     * Generates a random index in the list of names and makes sure
     * that it is not an index used before.
     * @param names the list containing the full names to index into
     * @return a random integer representing the random index used in the above functions
     */
    public int randomIndex(List<String> names) {
        //produce a random, non-repeating index
        int in;
        do {
            in = random.nextInt(names.size());
        } while (usedIndex.contains(in));
        usedIndex.add(in);
        return in;
    }

    /**
     *This first reads in the txt file with all the names in the class.
     * It then randomly generates a boolean which determines whether a true
     * or false combination is fetched from the file. It does this 5 times.
     * @param context the current context
     */
    public void run(Context context) {
        fullName = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("rawname.txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                fullName.add(line);
            }
        } catch (IOException e) {
            Log.e(TAG, "Unable to open file", e);
            return;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "Unable to close file", e);
                }
            }
        }



        for (int i = 0; i < 5; i++) {
            boolean tf = random.nextBoolean();
            Log.d(TAG, "true false: " + tf);
            tfArray[i] = tf;

            if (tf) {
                int in1 = randomIndex(fullName);
                names[i] = first(fullName, in1) + " " + last(fullName, in1);
                Log.d(TAG, first(fullName, in1) + " " + last(fullName, in1));
            } else {
                int in1 = randomIndex(fullName);
                int in2 = randomIndex(fullName);
                names[i] = first(fullName, in1) + " " + last(fullName, in2);
                Log.d(TAG, first(fullName, in1) + " " + last(fullName, in2));
            }
        }

        Log.d("MyApp", "List contents: " + names.toString());

    }
}