package com.github.invictum.mei.conditions;

public class ServerTime implements ConditionInterface {
    @Override
    public boolean checkCondition(String expression) throws IllegalArgumentException {
        try {
            long requiredTime = Long.valueOf(expression);
            if (System.currentTimeMillis() > requiredTime) {
                return true;
            }
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Can't parse expression for 'server_time': " + ex.getMessage());
        }
        return false;
    }
}
