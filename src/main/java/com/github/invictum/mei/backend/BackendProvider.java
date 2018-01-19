package com.github.invictum.mei.backend;

import com.github.invictum.mei.MeiPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Provides backend object based on plugin configuration
 */
public class BackendProvider {

    private static Backend backend = null;

    public static Backend get() {
        if (backend == null) {
            synchronized (BackendProvider.class) {
                if (backend == null) {
                    prepareBackend();
                }
            }
        }
        return backend;
    }

    private static void prepareBackend() {
        FileConfiguration config = JavaPlugin.getPlugin(MeiPlugin.class).getConfig();
        String backendType = config.getString("backend", "Memory");
        switch (backendType) {
            case "Sql":
                backend = new Sql(config.getConfigurationSection(backendType).getValues(true));
                break;
            case "Filesystem":
                backend = new Filesystem(config.getConfigurationSection(backendType).getValues(true));
                break;
            default:
                /* Use Memory backend by default */
                backend = new Memory();
                break;
        }
    }
}
