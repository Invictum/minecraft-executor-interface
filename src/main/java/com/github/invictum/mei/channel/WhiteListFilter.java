package com.github.invictum.mei.channel;

import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.List;

/**
 * Checks requester IP to enforce security
 */
public class WhiteListFilter implements Filter {

    private List<String> ips;

    public WhiteListFilter(List<String> ips) {
        this.ips = ips;
    }

    @Override
    public void handle(Request request, Response response) {
        if (!ips.isEmpty() && !ips.contains(request.ip())) {
            throw Spark.halt(403);
        }
    }
}
