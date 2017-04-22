package com.github.invictum.mei.conditions.instance;

import com.github.invictum.mei.MeiPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public class GameTime implements Condition {
    private JavaPlugin plugin = MeiPlugin.getPlugin(MeiPlugin.class);

    @Override
    public boolean checkCondition(String expression) throws IllegalArgumentException {
        try {
            int requiredTime = Integer.valueOf(expression);
            int interval = plugin.getConfig().getInt("interval", 60) * 20 + 5;
            long currentTime = plugin.getServer().getWorlds().get(0).getTime();
            if (currentTime + interval < requiredTime &&
                    currentTime - interval > requiredTime) {
                return true;
            }
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Can't parse expression for 'game_time': " + ex.getMessage());
        }
        return false;
    }
}
