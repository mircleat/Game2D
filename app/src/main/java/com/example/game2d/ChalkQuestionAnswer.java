package com.example.game2d;
import java.util.Random;

public class ChalkQuestionAnswer {
    public static void shuffleQuestions() {

        Random rand = new Random();

        for (int i = 0; i < question.length; i++) {
            // Switch i and randomIndexToSwap

            int randomIndexToSwap = rand.nextInt(question.length);

            // temp vars to store element at randomIndex
            String tempQuestion = question[randomIndexToSwap];
            String[] tempChoices = choices[randomIndexToSwap];
            String tempAnswers = correctAnswers[randomIndexToSwap];

            // element at randomIndex is now element at i (2 i duplicates)
            question[randomIndexToSwap] = question[i];
            choices[randomIndexToSwap] = choices[i];
            correctAnswers[randomIndexToSwap] = correctAnswers[i];

            // element at i is now element at randomIndex (via temp vars)
            question[i] = tempQuestion;
            choices[i] = tempChoices;
            correctAnswers[i] = tempAnswers;
        }
    }

    public static String[] question =  {
        "mov R0, 27\nmov R1, 57\nmov [R0], R1",
        "inc R1 ; mov [R0], R1\ninc R1 ; mov [R0], R1",
        "mov R0, 1 ; mov R1, 1\nmov R2, 48 ; mov [R1], R0",
        "loop: inc R0 ; inc R1\ncmp R1, R2 ; jne -4"
    };

    public static String[][] choices = {
        {"501B507970010000", "501C507570010000", "501C50757001", "501B50797001"},
        {"C0017001C00170010000", "B0017001D00170010000", "C0017001C0017001", "B0017001D0018001"},
        {"5000504b509470400000", "5000504a509470400000", "5000504a50947040", "5000504b50947040"},
        {"c000c0014042d7fc0000", "c000d0014042d7fc", "c000d0014042d7fb0000", "c000c0014042d7fc"},
    };

    public static String[] correctAnswers = {
        "501B507970010000",
        "C0017001C00170010000",
        "5000504a509470400000",
        "c000c0014042d7fc0000"
    };

    public static void main(String[] args) {
        shuffleQuestions();
    }

}
