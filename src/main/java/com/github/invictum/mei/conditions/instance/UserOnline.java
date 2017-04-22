package com.github.invictum.mei.conditions.instance;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class UserOnline implements Condition {

    @Override
    public boolean checkCondition(String expression) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().equalsIgnoreCase(expression)) {
                return true;
            }
        }
        return false;
    }
}
