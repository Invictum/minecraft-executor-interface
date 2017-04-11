package com.github.invictum.mei.conditions;

import org.bukkit.Bukkit;

public class UserCount implements ConditionInterface {
    @Override
    public boolean checkCondition(String expression) {
        try {
            int userCount = Integer.valueOf(expression);
            if (Bukkit.getOnlinePlayers().size() == userCount) {
                return true;
            }
        } catch (NumberFormatException ex) {
            Bukkit.getLogger().warning("Can't parse condition 'count_user': " + ex.getMessage());
        }
        return false;
    }
}
