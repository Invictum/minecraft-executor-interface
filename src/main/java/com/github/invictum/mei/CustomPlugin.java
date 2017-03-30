package com.github.invictum.mei;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class CustomPlugin extends JavaPlugin {
    private Backend backend = null;

    public Backend getBackend() {
        if (backend == null) {
            this.prepareBackend();
        }
        return backend;
    }

    private void prepareBackend() {
        String packageFolder = "com.github.invictum.mei.connectors.";
        String backendType = getConfig().getString("backend", "MySql");

        try {
            this.backend = (Backend) Class.forName(packageFolder + backendType).newInstance();
            this.backend.setConfig(getConfig().getConfigurationSection("backendConfig").getValues(true));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
