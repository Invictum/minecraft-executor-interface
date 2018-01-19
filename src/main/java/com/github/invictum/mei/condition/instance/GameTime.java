package com.github.invictum.mei.condition.instance;

import com.github.invictum.mei.MeiPlugin;
import com.github.invictum.mei.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class GameTime extends AbstractCondition {

    private final FileConfiguration config = JavaPlugin.getPlugin(MeiPlugin.class).getConfig();

    @Override
    public boolean check() {
        int requiredTime = Integer.valueOf(expression());
        int interval = config.getInt("interval", 60) * 20 + 5;
        long currentTime = Bukkit.getServer().getWorlds().get(0).getTime();
        return (currentTime + interval < requiredTime) && (currentTime - interval > requiredTime);
    }

    @Override
    public boolean isValid() {
        try {
            Integer.valueOf(expression());
        } catch (NumberFormatException ex) {
            Utils.getLogger().warning("Expression for 'game_time' is wrong");
            return false;
        }
        return true;
    }
}
