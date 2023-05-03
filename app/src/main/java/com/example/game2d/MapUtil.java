package com.example.game2d;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * MapUtil provides a function to sort a map by its values.
 */
public class MapUtil {
    /**
     * Sorts a map by value.
     * This implementation was adapted from https://stackoverflow.com/questions/109383/sort-a-mapkey-value-by-values
     * @param map the map to be sorted
     * @param <K> the type of the map's keys
     * @param <V> the type of the map's values, must be comparable to objects of same type or super type
     * @return the sorted map, with entries descending by value
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        // Create an array list of the map's entries
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        // Sort the list by comparing by value
        list.sort(Collections.reverseOrder(Map.Entry.comparingByValue()));
        // Create a map to store the result
        Map<K, V> result = new LinkedHashMap<>();
        // Put each item from the sorted list into the result map
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}