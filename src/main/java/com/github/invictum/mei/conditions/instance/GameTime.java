package com.github.invictum.mei.conditions.instance;

import com.github.invictum.mei.MeiPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public class GameTime extends AbstractCondition {

    private JavaPlugin plugin = MeiPlugin.getPlugin(MeiPlugin.class);

    @Override
    public boolean check() {
        int requiredTime = Integer.valueOf(expression());
        int interval = plugin.getConfig().getInt("interval", 60) * 20 + 5;
        long currentTime = plugin.getServer().getWorlds().get(0).getTime();
        return (currentTime + interval < requiredTime) && (currentTime - interval > requiredTime);
    }

    @Override
    public boolean isValid() {
        try {
            Integer.valueOf(expression());
        } catch (NumberFormatException ex) {
            MeiPlugin.log().warning("Expression for 'game_time' is wrong");
            return false;
        }
        return true;
    }
}
