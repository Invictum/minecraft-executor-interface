package com.github.invictum.mei.condition.instance;

import org.bukkit.Bukkit;

/**
 * Check user with given name is online
 */
public class UserOnline extends AbstractCondition {

    @Override
    public boolean check() {
        return Bukkit.getOnlinePlayers().stream().anyMatch(player -> player.getName().equalsIgnoreCase(expression()));
    }
}
