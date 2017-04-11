package com.github.invictum.mei.conditions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class UserOnline implements ConditionInterface {

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
