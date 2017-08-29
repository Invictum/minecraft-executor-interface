package com.github.invictum.mei.conditions.instance;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class UserOnline extends AbstractCondition {

    @Override
    public boolean check() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().equalsIgnoreCase(expression())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
