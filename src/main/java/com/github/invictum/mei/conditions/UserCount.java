package com.github.invictum.mei.conditions;

import org.bukkit.Bukkit;

public class UserCount implements ConditionInterface {
    @Override
    public boolean checkCondition(String expression) throws IllegalArgumentException {
        try {
            int userCount = Integer.valueOf(expression);
            if (Bukkit.getOnlinePlayers().size() == userCount) {
                return true;
            }
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Can't parse expression for 'count_user': " + ex.getMessage());
        }
        return false;
    }
}
