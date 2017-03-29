package com.github.invictum.mei;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class CustomPlugin extends JavaPlugin {
    private Backend backend = null;

    public Backend getBackend() {
        if (backend == null) {
            this.reloadBackend();
        }
        return backend;
    }

    public void reloadBackend() {
        String packageFolder = "com.github.invictum.mei.connectors.";
        String backendType = getConfig().getString("backend", "MySql");

        try {
            this.backend = (Backend) Class.forName(packageFolder + backendType).newInstance();
            this.backend.setConfig(getConfig().getConfigurationSection("backendConfig").getValues(true));
            this.backend.initBackend();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
