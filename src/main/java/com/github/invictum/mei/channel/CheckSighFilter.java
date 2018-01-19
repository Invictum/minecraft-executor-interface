package com.github.invictum.mei.channel;

import spark.Filter;
import spark.Request;
import spark.Response;

public class CheckSighFilter implements Filter {

    private String token;

    public CheckSighFilter(String token) {
        this.token = token;
    }

    @Override
    public void handle(Request request, Response response) throws Exception {
        // Check sigh here
    }
}
