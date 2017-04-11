package com.github.invictum.mei.conditions;

import org.bukkit.Bukkit;

public class ServerTime implements ConditionInterface {
    @Override
    public boolean checkCondition(String expression) {
        try {
            long requiredTime = Long.valueOf(expression);
            if (System.currentTimeMillis() > requiredTime) {
                return true;
            }
        } catch (NumberFormatException ex) {
            Bukkit.getLogger().warning("Can't parse condition 'server_time': " + ex.getMessage());
        }
        return false;
    }
}
