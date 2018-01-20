package com.github.invictum.mei;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Utils class for single Gson instance access
 */
public class Json {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private Json() {
        // Disable constructor
    }

    public static Gson get() {
        return gson;
    }
}
