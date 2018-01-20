package com.github.invictum.mei.channel;

import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Spark;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.logging.Logger;

/**
 * Checks requests HmacSHA256 sigh
 */
public class CheckSighFilter implements Filter {

    private Logger logger;
    private Mac hmacSHA256;
    private long lastNonce = 0;

    public CheckSighFilter(String token, Logger logger) {
        this.logger = logger;
        try {
            hmacSHA256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec key = new SecretKeySpec(token.getBytes(), "HmacSHA256");
            hmacSHA256.init(key);
        } catch (GeneralSecurityException e) {
            logger.warning("Unable to init security mechanism");
        }
    }

    @Override
    public void handle(Request request, Response response) {
        /* Check nonce */
        if (request.headers("Nonce") == null || Long.valueOf(request.headers("Nonce")) <= lastNonce) {
            logger.warning("Request with bad Nonce detected");
            throw Spark.halt(403);
        }
        /* Check sigh header */
        if (request.headers("Sigh") == null) {
            logger.warning("Request without Sigh header detected");
            throw Spark.halt(403);
        }
        Long nonce = Long.valueOf(request.headers("Nonce"));
        byte[] data = (request.body() + nonce).getBytes();
        String sigh = Base64.getEncoder().encodeToString(hmacSHA256.doFinal(data));
        /* Check sigh value */
        if (!request.headers("Sigh").contentEquals(sigh)) {
            logger.warning("Request Sigh is wrong");
            throw Spark.halt(403);
        }
        lastNonce = nonce;
    }
}
