package com.github.invictum.mei.condition.instance;

import com.github.invictum.mei.Utils;
import org.bukkit.Bukkit;

/**
 * Matches on exact online players
 */
public class UserCount extends AbstractCondition {

    @Override
    public boolean check() {
        int userCount = Integer.valueOf(expression());
        return (Bukkit.getOnlinePlayers().size() == userCount);
    }

    @Override
    public boolean isValid() {
        try {
            Long.valueOf(expression());
        } catch (NumberFormatException ex) {
            Utils.getLogger().warning("Expression for 'count_user' is wrong");
            return false;
        }
        return true;
    }
}
