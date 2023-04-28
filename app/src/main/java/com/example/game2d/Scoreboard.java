package com.example.game2d;

import java.util.HashMap;
import java.util.Map;

public class Scoreboard {
    public Map<String, Long> scoreboard;

    public void add(String username, long score) {
        if (scoreboard != null) {
            scoreboard.put(username,score);
        }
    }
}
