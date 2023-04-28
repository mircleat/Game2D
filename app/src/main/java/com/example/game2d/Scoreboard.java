package com.example.game2d;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Scoreboard implements Serializable {
    public Map<String, Long> scoreboard;

    Scoreboard() {
        scoreboard = new HashMap<String, Long>();
    }

    public void add(String username, long score) {
        if (scoreboard != null) {
            scoreboard.put(username,score);
        }
    }
}
