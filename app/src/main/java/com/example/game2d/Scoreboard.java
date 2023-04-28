package com.example.game2d;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Scoreboard implements Serializable {
    public Map<String, Double> scoreboard;

    Scoreboard() {
        scoreboard = new HashMap<String, Double>();
    }

    public void add(String username, double score) {
        if (scoreboard != null) {
            scoreboard.put(username, score);
        }
    }
}
