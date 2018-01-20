package com.github.invictum.mei;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * Utils class with helper methods
 */
public class Utils {

    /**
     * Stops MEI plugin
     */
    public static void disablePlugin() {
        Bukkit.getPluginManager().disablePlugin(JavaPlugin.getPlugin(MeiPlugin.class));
    }

    /**
     * Returns plugin's {@link Logger}
     *
     * @return logger
     */
    public static Logger getLogger() {
        return JavaPlugin.getPlugin(MeiPlugin.class).getLogger();
    }
}
