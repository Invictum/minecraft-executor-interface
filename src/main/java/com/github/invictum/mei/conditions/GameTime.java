package com.github.invictum.mei.conditions;

import com.github.invictum.mei.MeiPlugin;
import org.bukkit.Bukkit;

public class GameTime implements ConditionInterface {
    @Override
    public boolean checkCondition(String expression) {
        try {
            int requiredTime = Integer.valueOf(expression);
            int interval = MeiPlugin.getPlugin(MeiPlugin.class).getConfig().getInt("interval", 60) * 20 + 5;
            long currentTime = Bukkit.getWorlds().get(0).getTime();
            if (currentTime + interval < requiredTime &&
                    currentTime - interval > requiredTime) {
                return true;
            }
        } catch (NumberFormatException ex) {
            Bukkit.getLogger().warning("Can't parse condition 'game_time': " + ex.getMessage());
        }
        return false;
    }
}
