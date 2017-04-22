package com.github.invictum.mei.conditions.instance;

import com.github.invictum.mei.MeiPlugin;

public class ServerTime extends AbstractCondition {

    @Override
    public boolean check() {
        long requiredTime = Long.valueOf(expression());
        return (System.currentTimeMillis() > requiredTime);
    }

    @Override
    public boolean isValid() {
        try {
            Long.valueOf(expression());
        } catch (NumberFormatException ex) {
            MeiPlugin.log().warning("Expression for 'server_time' is wrong");
            return false;
        }
        return true;
    }
}
