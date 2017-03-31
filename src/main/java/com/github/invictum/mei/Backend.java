package com.github.invictum.mei;

import com.github.invictum.mei.connectors.AbstractBackend;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Map;

public class Backend {

    private static AbstractBackend backend = null;
    private static FileConfiguration config = MeiPlugin.getPlugin(MeiPlugin.class).getConfig();

    private Backend() {
    }

    public static AbstractBackend getInstance() {
        if (backend == null) {
            constructBackend();
        }
        return backend;
    }

    private static void constructBackend() {
        String packageFolder = "com.github.invictum.mei.connectors.";
        String backendType = config.getString("backend", "MySql");
        try {
            backend = (AbstractBackend) Class.forName(packageFolder + backendType).getConstructor(Map.class)
                    .newInstance(config.getConfigurationSection(backendType).getValues(true));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
