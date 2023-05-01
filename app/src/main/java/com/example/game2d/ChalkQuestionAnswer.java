package com.example.game2d;

public class ChalkQuestionAnswer {

    public static String[] question =  {
        "mov R0, 27\nmov R1, 57\nmov [R0], R1",
        "inc R1 ; mov [R0], R1\ninc R1 ; mov [R0], R1",
        "mov R0, 1 ; mov R1, 1\nmov R2, 48 ; mov [R1], R0",
        "loop: inc R0 ; inc R1\ncmp R1, R2 ; jne -4"
    };

    public static String[][] choices = {
        {"501B50797001", "501C50767001", "501C50757001", "501B50897001"},
        {"C0017001C0017001", "B0017001D0017001", "C0016001C0016001", "B0017001D0018001"},
        {"5000504a50947040", "5000504b50957040", "5000504a50847040", "5000504b50947040"},
        {"c000c0014042d7fc", "c000d0024042d7fc", "c000d0014042d7fb", "c000c0014042c7fc"},
    };

    public static String[] correctAnswers = {
        "501B50797001",
        "C0017001C0017001",
        "5000504a50947040",
        "c000c0014042d7fc"
    };
}
