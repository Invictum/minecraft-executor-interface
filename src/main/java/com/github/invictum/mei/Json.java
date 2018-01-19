package com.github.invictum.mei;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Json {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static Gson get() {
        return gson;
    }
}
