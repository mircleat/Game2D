package com.example.game2d;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The Scoreboard class stores a map of usernames and scores for the game's leaderboard.
 */
public class Scoreboard implements Serializable {
    // The map in which usernames and scores are stored
    public Map<String, Double> scoreboard;

    /**
     * Constructs an empty scoreboard.
     */
    Scoreboard() {
        scoreboard = new HashMap<String, Double>();
    }

    /**
     * Adds an entry to the scoreboard map.
     * @param username a string for the player's username
     * @param score a double for the player's score
     */
    public void add(String username, double score) {
        if (scoreboard != null) {
            scoreboard.put(username, score);
        }
    }
}
