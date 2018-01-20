package com.github.invictum.mei.condition.instance;

import com.github.invictum.mei.Utils;

/**
 * Matches if current server time is more than specified
 */
public class ServerTime extends AbstractCondition {

    @Override
    public boolean check() {
        long requiredTime = Long.valueOf(expression());
        return (System.currentTimeMillis() >= requiredTime);
    }

    @Override
    public boolean isValid() {
        try {
            Long.valueOf(expression());
        } catch (NumberFormatException ex) {
            Utils.getLogger().warning("Expression for 'server_time' is wrong");
            return false;
        }
        return true;
    }
}
