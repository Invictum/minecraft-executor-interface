package com.github.invictum.mei.channel;

import com.github.invictum.mei.Utils;
import org.bukkit.configuration.file.FileConfiguration;
import spark.Spark;

import java.util.List;

/**
 * Provides functionality for both API and SOCKET channels
 */
public class ChannelsFacility {

    /* Define properties mapping */
    private final static String CHANNELS = "Channels";
    private final static String SOCKET = "SOCKET";
    private final static String API = "API";
    private final static String PORT = "port";
    private final static String IP_ADDRESS = "ipAddress";
    private final static String API_PATH = "apiPath";
    private final static String CHECK_SIGH = "checkSigh";
    private final static String SECURITY_TOKEN = "securityToken";
    private final static String WHITELIST_IPS = "whitelistIps";

    private FileConfiguration config;

    public ChannelsFacility(FileConfiguration config) {
        this.config = config;
    }

    public void start() {
        /* Basic server setup */
        List<String> channels = config.getStringList(CHANNELS);
        Spark.ipAddress(config.getString(IP_ADDRESS, "0.0.0.0"));
        Spark.port(config.getInt(PORT, 9090));
        /* SOCKET channel setup section */
        if (channels.contains(SOCKET)) {
            // Init socket config
        }
        /* API channel setup section */
        if (channels.contains(API)) {
            String apiPath = config.getString(API_PATH, "/api");
            if (config.getBoolean(CHECK_SIGH, false)) {
                Spark.before(apiPath, new CheckSighFilter(config.getString(SECURITY_TOKEN), Utils.getLogger()));
            }
            Spark.before(apiPath, new WhiteListFilter(config.getStringList(WHITELIST_IPS)));
            Spark.post(apiPath, "application/json", new RestApiRoute());
        }
    }

    public void shutdown() {
        Spark.stop();
    }
}
