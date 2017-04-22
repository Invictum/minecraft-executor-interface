package com.github.invictum.mei.conditions.instance;

import com.github.invictum.mei.MeiPlugin;
import org.bukkit.Bukkit;

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
            MeiPlugin.log().warning("Expression for 'count_user' is wrong");
            return false;
        }
        return true;
    }
}
